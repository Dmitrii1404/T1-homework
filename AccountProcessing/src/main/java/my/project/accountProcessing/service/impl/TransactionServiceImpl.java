package my.project.accountProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.StatusEnum;
import my.lib.core.TransactionStatus;
import my.lib.core.TransactionType;
import my.project.accountProcessing.dto.CardProcessDto;
import my.project.accountProcessing.dto.PaymentCreateDto;
import my.project.accountProcessing.dto.TransactionDto;
import my.project.accountProcessing.entity.account.Account;
import my.project.accountProcessing.entity.card.Card;
import my.project.accountProcessing.entity.transaction.Transaction;
import my.project.accountProcessing.exception.NonRetryable.InsufficientFundsException;
import my.project.accountProcessing.exception.NonRetryable.SourceNotActiveException;
import my.project.accountProcessing.mapper.TransactionMapper;
import my.project.accountProcessing.repository.AccountRepository;
import my.project.accountProcessing.repository.CardRepository;
import my.project.accountProcessing.repository.TransactionRepository;
import my.project.accountProcessing.service.CardService;
import my.project.accountProcessing.service.PaymentService;
import my.project.accountProcessing.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final PaymentService paymentService;
    private final CardService cardService;
    private final CardRepository cardRepository;

    @Value("${properties.transaction.limit-time-minutes}")
    private long limitTimeMinutes;

    @Value("${properties.transaction.limit-quantity-transaction}")
    private int limitQuantityTransaction;

    @Override
    @Transactional
    public void processTransaction(TransactionDto transactionDto) {

        // получаю карту, по которой проводить транзакцию
        Card card = cardService.getCardById(transactionDto.cardId());

        Transaction transaction = Transaction.builder()
                .card(card)
                .account(card.getAccount())
                .type(transactionDto.type())
                .amount(transactionDto.amount())
                .timestamp(LocalDateTime.now())
                .build();

        // проверка карты, блокируем если надо
        if (!checkCard(card)) {
            transaction.setStatus(TransactionStatus.BLOCKED);
            transactionRepository.save(transaction);

            card.setStatus(StatusEnum.BLOCKED);
            cardRepository.save(card);
            return;
        }

        // выполняю саму транзакцию
        completeTransaction(card.getAccount(), transactionDto.type(), transactionDto.amount());

        // сохраняю транзакцию
        transaction.setStatus(TransactionStatus.COMPLETE);
        transactionRepository.save(transaction);
    }

    // проверка карты
    private boolean checkCard(Card card) {

        // активна ли карта или заблокирована
        if (card.getStatus() != StatusEnum.ACTIVE) {
            throw new SourceNotActiveException("Указанная карта не активна.");
        }

        // количество транзакций за время N
        LocalDateTime after = LocalDateTime.now().minusMinutes(limitTimeMinutes);
        long recent = transactionRepository.countByCardAndTimestampAfter(card, after);

        // проверка, что транзакций меньше чем T
        return recent < limitQuantityTransaction;
    }

    private void completeTransaction(Account account, TransactionType type, BigDecimal amount) {
        // это для списания
        if (type == TransactionType.WITHDRAW) {
            BigDecimal balance = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
            balance = balance.setScale(2, RoundingMode.HALF_UP);
            amount = amount.setScale(2, RoundingMode.HALF_UP);

            // проверка хватит ли денег
            if (balance.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Недостаточно средств для списания");
            }

            // уменьшаем баланс
            BigDecimal newBalance = balance.subtract(amount);
            account.setBalance(newBalance);

            // если счет кредитный, создадим график платежей
            // раз это обычная кредитка, то срок будет просто 1 месяц
            if (account.getIsRecalc()) {
                PaymentCreateDto paymentCreateDto = new PaymentCreateDto(amount);
                paymentService.createPayment(account, paymentCreateDto);
            }
        } else {
            // это для пополнения

            BigDecimal balance = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
            balance = balance.setScale(2, RoundingMode.HALF_UP);
            amount = amount.setScale(2, RoundingMode.HALF_UP);

            BigDecimal newBalance = balance.add(amount);
            account.setBalance(newBalance);

            // если аккаунт кредитный - поробуем оплатить задолженности
            if (account.getIsRecalc()) {
                account = paymentService.payedPaymentLastDay(account);
            }
        }

        accountRepository.save(account);
    }

}

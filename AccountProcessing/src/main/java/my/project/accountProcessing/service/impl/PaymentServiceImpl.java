package my.project.accountProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.lib.core.TransactionType;
import my.project.accountProcessing.dto.PaymentCreateDto;
import my.project.accountProcessing.dto.PaymentDto;
import my.project.accountProcessing.entity.account.Account;
import my.project.accountProcessing.entity.card.Card;
import my.project.accountProcessing.entity.payment.Payment;
import my.project.accountProcessing.exception.NonRetryable.NotFountException;
import my.project.accountProcessing.repository.AccountRepository;
import my.project.accountProcessing.repository.PaymentRepository;
import my.project.accountProcessing.service.CardService;
import my.project.accountProcessing.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final CardService cardService;

    // если сняли деньги с кредитки - надло их вернуть. Создаю задолжность
    @Override
    @Transactional
    public void createPayment(Account account, PaymentCreateDto paymentCreateDto) {

        BigDecimal totalAmount = generatePaymentAmount(account.getInterestRate(), paymentCreateDto.amount());

        Payment payment = Payment.builder()
                .account(account)
                .paymentDate(LocalDate.now().plusMonths(1))
                .amount(totalAmount)
                .isCredit(true)
                .type(TransactionType.WITHDRAW)
                .build();

        paymentRepository.save(payment);
    }

    // принудительная оплата, если было пополнение на счет + сегодня срок каких-то платежей выходит
    @Override
    @Transactional
    public Account payedPaymentLastDay(Account account) {

        int MONEY_SCALE = 2;
        RoundingMode ROUNDING = RoundingMode.HALF_UP;

        List<Payment> PaymentsToday = paymentRepository.findByAccountIdAndPaymentDateOrderByAmountAsc(account.getId(), LocalDate.now());

        List<Payment> toSave = new ArrayList<>();

        BigDecimal available = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
        available = available.setScale(MONEY_SCALE, ROUNDING);

        // проходимся по всем платежам, которые заканчиваются сегодня
        for (Payment p : PaymentsToday) {
            BigDecimal amount = p.getAmount() == null ? BigDecimal.ZERO : p.getAmount().setScale(MONEY_SCALE, ROUNDING);

            if (available.compareTo(amount) >= 0) {
                // можем оплатить
                available = available.subtract(amount).setScale(MONEY_SCALE, ROUNDING);
                p.setPayedAt(LocalDate.now());
                p.setExpired(false);
            } else {
                // денег не хватает - помечаю просроченным
                p.setExpired(true);
            }
            toSave.add(p);
        }

        // сохраняю новый баланс
        account.setBalance(available);

        if (!toSave.isEmpty()) {
            paymentRepository.saveAll(toSave);
        }

        return account;
    }

    @Override
    @Transactional
    public void processPayment(PaymentDto paymentDto) {
        Card card = cardService.getCardById(paymentDto.cardId());

        // считаю общую задолжность
        // выбираю платежи по accountId, неоплаченные и типа ЗАДОЛЖНОСТЬ
        List<Payment> payments = paymentRepository.findByAccountIdAndTypeAndPayedAtIsNull(
                card.getAccount().getId(),
                TransactionType.WITHDRAW
        );

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Payment payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());
        }

        // если мы пополнили на сумму >= общей задолжности, то закрываем все долги
        if (paymentDto.amount().compareTo(totalAmount) >= 0) {
            for (Payment payment : payments) {
                payment.setPayedAt(LocalDate.now());
            }
            paymentRepository.saveAll(payments);
        }

        // создаю платеж о том, что мы пополнили денег
        Payment payment = Payment.builder()
                .account(card.getAccount())
                .paymentDate(LocalDate.now())
                .amount(paymentDto.amount())
                .isCredit(false)
                .payedAt(LocalDate.now())
                .type(TransactionType.DEPOSIT)
                .expired(false)
                .build();
        paymentRepository.save(payment);

        // пополняю эти деньги на счет
        Account account = accountRepository.findById(card.getAccount().getId()).orElseThrow(
                () -> new NotFountException("Аккаунт не найден")
        );

        account.setBalance(account.getBalance().add(payment.getAmount()));
        accountRepository.save(account);
    }

    // расчет платежа на месяц
    private BigDecimal generatePaymentAmount(BigDecimal interestRate, BigDecimal amount) {
        final int MONEY_SCALE = 2;
        final int CALC_SCALE  = 16;

        // месячная ставка
        BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(12), CALC_SCALE, RoundingMode.HALF_UP);

        // процент за месяц
        BigDecimal interest = amount.multiply(monthlyRate)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

        // платеж = сумма + проценты
        return amount.add(interest)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }


}

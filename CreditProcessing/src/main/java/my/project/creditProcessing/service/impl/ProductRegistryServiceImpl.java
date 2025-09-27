package my.project.creditProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.creditProcessing.dto.ClientPaymentCheckDto;
import my.project.creditProcessing.dto.ClientResponseDto;
import my.project.creditProcessing.dto.CreditCreateDto;
import my.project.creditProcessing.entity.productRegistry.ProductRegistry;
import my.project.creditProcessing.exception.NonRetryable.CreditLimitExceedException;
import my.project.creditProcessing.exception.NonRetryable.PaymentExpiredException;
import my.project.creditProcessing.exception.Retryable.NotFoundUserHttpException;
import my.project.creditProcessing.repository.PaymentRegistryRepository;
import my.project.creditProcessing.repository.ProductRegistryRepository;
import my.project.creditProcessing.service.PaymentRegistryService;
import my.project.creditProcessing.service.ProductRegistryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductRegistryServiceImpl implements ProductRegistryService {

    @Value("${properties.url.client-processing}")
    private String urlClientProcessing;

    @Value("${properties.credit-limit}")
    private BigDecimal creditLimit;

    @Value("${properties.interest-rate}")
    private BigDecimal interestRate;

    private final RestTemplate restTemplate;
    private final PaymentRegistryRepository paymentRegistryRepository;
    private final ProductRegistryRepository productRegistryRepository;
    private final PaymentRegistryService paymentRegistryService;

    // открытие кредита
    @Override
    @Transactional
    public void createCredit(CreditCreateDto creditCreateDto) {
        // ToDo(на будущее) что-то делать с полученным пр=аспортом и ФИО
        ClientResponseDto client = fetchClient(creditCreateDto.clientId());

        ClientPaymentCheckDto clientPaymentCheckDto = getInfoClientCredits(creditCreateDto.clientId());

        checkRulesOpenCredit(creditCreateDto, clientPaymentCheckDto);

        ProductRegistry productRegistry = createProductRegistry(creditCreateDto);

        paymentRegistryService.createPayments(productRegistry, creditCreateDto);
    }

    // api запрос за паспортом и ФИО
    private ClientResponseDto fetchClient(Long clientId) {
        String url = urlClientProcessing + clientId;
        ClientResponseDto client = restTemplate.getForObject(url, ClientResponseDto.class);
        if (client == null) throw new NotFoundUserHttpException("Пользователь не найден: " + clientId);
        return client;
    }

    // тут получим сумму всех кредитов/есть ли просрочки/все существующие кредиты клиента
    private ClientPaymentCheckDto getInfoClientCredits(Long clientId) {
        BigDecimal totalSumCredits = paymentRegistryRepository.sumDebtByClientId(clientId);
        if (totalSumCredits == null) totalSumCredits = BigDecimal.ZERO;

        boolean hasExpired = paymentRegistryRepository.existsExpiredPaymentByClientId(clientId);

        List<ProductRegistry> productRegistries = productRegistryRepository.findByClientId(clientId);

        return new ClientPaymentCheckDto(totalSumCredits, hasExpired, productRegistries);
    }

    // проверим правила открытия кредита
    // - сумма всех кредитов клиента
    // - есть ли просрочки
    // - если запрашиваемый кредит больше лимита в целом
    private void checkRulesOpenCredit(CreditCreateDto creditCreateDto, ClientPaymentCheckDto clientPaymentCheckDto) {
        boolean hasCredits = !clientPaymentCheckDto.productRegistries().isEmpty();
        BigDecimal totalSumCredits = clientPaymentCheckDto.totalSumCredits().add(creditCreateDto.creditAmount());

        if (hasCredits) {
            if (totalSumCredits.compareTo(creditLimit) > 0) {
                throw new CreditLimitExceedException("Превышен лимит суммы кредитов");
            }
            if (clientPaymentCheckDto.hasExpired()) {
                throw new PaymentExpiredException("У клиента есть просроченные платежи");
            }
        } else {
            if (creditCreateDto.creditAmount().compareTo(creditLimit) > 0) {
                throw new CreditLimitExceedException("Превышен лимит суммы кредита");
            }
        }
    }

    private ProductRegistry createProductRegistry(CreditCreateDto creditCreateDto) {
        ProductRegistry productRegistry = ProductRegistry.builder()
                .clientId(creditCreateDto.clientId())
                .accountId(0L)                  // ToDo(на будущее) что-то придумать с AccountId. для чего он тут?
                .productId(creditCreateDto.productId())
                .interestRate(interestRate)
                .openDate(LocalDate.now())
                .monthCount(creditCreateDto.monthCount())
                .build();
        return productRegistryRepository.save(productRegistry);
    }

}

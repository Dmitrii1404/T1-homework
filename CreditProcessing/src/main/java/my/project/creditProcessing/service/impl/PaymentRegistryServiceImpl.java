package my.project.creditProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.creditProcessing.dto.CreditCreateDto;
import my.project.creditProcessing.entity.paymentRegistry.PaymentRegistry;
import my.project.creditProcessing.entity.productRegistry.ProductRegistry;
import my.project.creditProcessing.repository.PaymentRegistryRepository;
import my.project.creditProcessing.service.PaymentRegistryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentRegistryServiceImpl implements PaymentRegistryService {
    
    private final PaymentRegistryRepository paymentRegistryRepository;

    // создаем и сохраняем график платежей
    @Override
    @Transactional
    public void createPayments(ProductRegistry productRegistry, CreditCreateDto creditCreateDto) {
        // создание
        List<PaymentRegistry> payments = generatePaymentPlan(
                productRegistry,
                creditCreateDto.creditAmount(),
                creditCreateDto.monthCount()
        );

        // сохранение
        payments.forEach(p -> p.setProductRegistry(productRegistry));
        paymentRegistryRepository.saveAll(payments);
    }
    
    // По формулам считаем пллатежи
    private List<PaymentRegistry> generatePaymentPlan(
            ProductRegistry productRegistry,
            BigDecimal principal,
            int months
    ) {
        final int MONEY_SCALE = 2;
        final int CALC_SCALE = 16;

        BigDecimal annualRate = productRegistry.getInterestRate();
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), CALC_SCALE, RoundingMode.HALF_UP);

        BigDecimal annuity;
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            annuity = principal.divide(BigDecimal.valueOf(months), MONEY_SCALE, RoundingMode.HALF_UP);
        } else {
            BigDecimal onePlusI = BigDecimal.ONE.add(monthlyRate);
            BigDecimal pow = onePlusI.pow(months);
            BigDecimal numerator = principal.multiply(monthlyRate).multiply(pow);
            BigDecimal denominator = pow.subtract(BigDecimal.ONE);
            annuity = numerator.divide(denominator, MONEY_SCALE, RoundingMode.HALF_UP);
        }

        List<PaymentRegistry> payments = new ArrayList<>(months);
        LocalDate payDate = productRegistry.getOpenDate();
        BigDecimal remaining = principal.setScale(MONEY_SCALE, RoundingMode.HALF_UP);

        for (int month = 1; month <= months; month++) {
            payDate = payDate.plusMonths(1);

            BigDecimal interest = remaining.multiply(monthlyRate).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
            BigDecimal principalPart = annuity.subtract(interest).setScale(MONEY_SCALE, RoundingMode.HALF_UP);

            if (month == months) {
                principalPart = remaining;
                annuity = principalPart.add(interest).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
            }

            remaining = remaining.subtract(principalPart).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
            if (remaining.compareTo(BigDecimal.ZERO) < 0) remaining = BigDecimal.ZERO;

            PaymentRegistry p = PaymentRegistry.builder()
                    .date(payDate)
                    .amount(annuity)
                    .interestRateAmount(interest)
                    .debtAmount(principalPart)
                    .expired(false)
                    .paymentExpirationDate(payDate)
                    .build();

            payments.add(p);
        }

        return payments;
    }

}

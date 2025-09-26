package my.project.creditProcessing.util;

import lombok.RequiredArgsConstructor;
import my.lib.core.ClientProductCreditEvent;
import my.project.creditProcessing.dto.CreditCreateDto;
import my.project.creditProcessing.mapper.ProductRegistryMapper;
import my.project.creditProcessing.service.ProductRegistryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumers {

    private final ProductRegistryMapper productRegistryMapper;
    private final ProductRegistryService productRegistryService;

    @KafkaListener(topics = "client_credit_products")
    public void handle(ClientProductCreditEvent clientProductCreditEvent) {
        CreditCreateDto creditCreateDto = productRegistryMapper.toDtoFromEvent(clientProductCreditEvent);
        productRegistryService.createCredit(creditCreateDto);
    }

}

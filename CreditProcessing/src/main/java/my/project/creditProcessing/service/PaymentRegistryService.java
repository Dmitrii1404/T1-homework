package my.project.creditProcessing.service;

import my.project.creditProcessing.dto.CreditCreateDto;
import my.project.creditProcessing.entity.productRegistry.ProductRegistry;


public interface PaymentRegistryService {

    void createPayments(ProductRegistry productRegistry, CreditCreateDto creditCreateDto);

}

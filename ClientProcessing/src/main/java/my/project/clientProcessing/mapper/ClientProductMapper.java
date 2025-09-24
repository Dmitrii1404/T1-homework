package my.project.clientProcessing.mapper;

import my.lib.core.ClientProductAccountEvent;
import my.lib.core.ClientProductCreditEvent;
import my.project.clientProcessing.dto.ClientProductCreditCreateDto;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.entity.clientProduct.ClientProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientProductMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "product.id", target = "productId")
    ClientProductResponseDto toDto(ClientProduct clientProduct);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "product.id", target = "productId")
    ClientProductAccountEvent toAccountEventDto(ClientProduct clientProduct);

    ClientProductCreditEvent toCreditEventDto(ClientProductCreditCreateDto clientProductCreditCreateDto);

}

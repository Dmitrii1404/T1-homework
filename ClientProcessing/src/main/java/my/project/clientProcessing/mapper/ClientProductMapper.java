package my.project.clientProcessing.mapper;

import my.lib.core.ClientProductEvent;
import my.project.clientProcessing.dto.ClientProductResponseDto;
import my.project.clientProcessing.dto.ClientProductUpdateDto;
import my.project.clientProcessing.entity.clientProduct.ClientProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientProductMapper {

    void updateFromDto(ClientProductUpdateDto clientProductUpdateDto, @MappingTarget ClientProduct clientProduct);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "product.id", target = "productId")
    ClientProductResponseDto toDto(ClientProduct clientProduct);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "product.id", target = "productId")
    ClientProductEvent toEventDto(ClientProduct clientProduct);

}

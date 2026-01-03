package my.project.creditProcessing.mapper;

import my.lib.core.ClientProductCreditEvent;
import my.project.creditProcessing.dto.CreditCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRegistryMapper {

    CreditCreateDto toDtoFromEvent(ClientProductCreditEvent clientProductCreditEvent);

}

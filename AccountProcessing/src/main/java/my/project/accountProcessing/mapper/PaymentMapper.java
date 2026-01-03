package my.project.accountProcessing.mapper;

import my.lib.core.PaymentEvent;
import my.project.accountProcessing.dto.PaymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto toDtoFromEvent(PaymentEvent paymentEvent);

}

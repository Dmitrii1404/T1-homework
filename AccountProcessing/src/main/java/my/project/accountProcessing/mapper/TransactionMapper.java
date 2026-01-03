package my.project.accountProcessing.mapper;

import my.lib.core.TransactionEvent;
import my.project.accountProcessing.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto toDtoFromEvent(TransactionEvent transactionEvent);

}

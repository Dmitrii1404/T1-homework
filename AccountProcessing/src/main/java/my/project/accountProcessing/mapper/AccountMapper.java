package my.project.accountProcessing.mapper;

import my.lib.core.ClientProductAccountEvent;
import my.project.accountProcessing.dto.AccountCreateDto;
import my.project.accountProcessing.dto.AccountUpdateDto;
import my.project.accountProcessing.entity.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    Account toEntity(AccountCreateDto accountCreateDto);

    void updateFromDto(AccountUpdateDto accountUpdateDto, @MappingTarget Account account);

    AccountCreateDto toDtoFromEvent(ClientProductAccountEvent clientProductAccountEvent);

}

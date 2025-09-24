package my.project.accountProcessing.mapper;

import my.lib.core.CardCreateEvent;
import my.project.accountProcessing.dto.CardCreateDto;
import my.project.accountProcessing.entity.card.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "account", ignore = true)
    Card toEntity(CardCreateDto cardCreateDto);

    CardCreateDto toDtoFromEvent(CardCreateEvent cardCreateEvent);

}

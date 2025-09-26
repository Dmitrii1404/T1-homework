package my.project.clientProcessing.mapper;

import my.lib.core.CardCreateEvent;
import my.project.clientProcessing.dto.CardCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardCreateEvent toEventDto(CardCreateDto cardCreateDto);

}

package my.project.clientProcessing.mapper;

import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.entity.client.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Client toEntity(ClientCreateDto clientCreateDto);

}

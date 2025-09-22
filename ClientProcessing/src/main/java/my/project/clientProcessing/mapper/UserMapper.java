package my.project.clientProcessing.mapper;

import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.dto.UserResponseDto;
import my.project.clientProcessing.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(ClientCreateDto clientCreateDto);

    UserResponseDto toUserResponseDto(User user);

}

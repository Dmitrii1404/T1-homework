package my.project.clientProcessing.service;

import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.entity.user.User;

public interface UserService {

    User createUser(ClientCreateDto clientCreateDto);
    User getUserByEmail(String email);

}

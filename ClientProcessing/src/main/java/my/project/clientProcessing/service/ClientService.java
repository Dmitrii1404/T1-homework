package my.project.clientProcessing.service;


import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.dto.UserResponseDto;
import my.project.clientProcessing.entity.client.Client;
import my.project.clientProcessing.entity.user.User;
import my.project.clientProcessing.mapper.ClientMapper;
import my.project.clientProcessing.mapper.UserMapper;
import my.project.clientProcessing.repository.ClientRepository;
import my.project.clientProcessing.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;

    // ToDo обработка ошибок
    @Transactional
    public UserResponseDto userRegistry(ClientCreateDto clientCreateDto) {

        User user = userMapper.toEntity(clientCreateDto);
        user.setPassword("hash");            // ToDo добавить хеширование
        User savedUser = userRepository.save(user);

        Client client = clientMapper.toEntity(clientCreateDto);
        client.setUser(savedUser);
        client.setClientId("1245984678");     // ToDo добавить генерацию clientId
        clientRepository.save(client);

        return userMapper.toUserResponseDto(savedUser);
    }

}

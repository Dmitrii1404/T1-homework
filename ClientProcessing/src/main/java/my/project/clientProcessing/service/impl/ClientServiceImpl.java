package my.project.clientProcessing.service.impl;


import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.dto.ClientResponseDto;
import my.project.clientProcessing.dto.DocumentCheckBlacklistDto;
import my.project.clientProcessing.dto.UserResponseDto;
import my.project.clientProcessing.entity.client.Client;
import my.project.clientProcessing.entity.user.User;
import my.project.clientProcessing.exception.AlreadyExistException;
import my.project.clientProcessing.exception.DocumentIsBannedException;
import my.project.clientProcessing.exception.NotFoundException;
import my.project.clientProcessing.mapper.ClientMapper;
import my.project.clientProcessing.mapper.UserMapper;
import my.project.clientProcessing.repository.ClientRepository;
import my.project.clientProcessing.service.BlacklistService;
import my.project.clientProcessing.service.ClientService;
import my.project.clientProcessing.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final BlacklistService blacklistService;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    // регистрация клиента - создание Client+User
    @Override
    @Transactional
    public UserResponseDto clientRegistry(ClientCreateDto clientCreateDto) {

        // проверка на черные списки
        if (blacklistService.documentIsBanned(new DocumentCheckBlacklistDto(clientCreateDto.documentId()))) {
            throw new DocumentIsBannedException("Указанный документ находится в черном списке");
        }

        // проверка на существование уже клиента с таким паспортом
        if (getClientByDocumentId(clientCreateDto.documentId()) != null) {
            throw new AlreadyExistException("Клиент с указанным документом уже существует");
        }

        // создание User
        User user = userService.createUser(clientCreateDto);

        // создание Client
        Client client = clientMapper.toEntity(clientCreateDto);
        client.setUser(user);
        client.setClientId(clientCreateDto.region() + clientCreateDto.bank() + String.format("%08d", user.getId()));
        clientRepository.save(client);

        return userMapper.toDto(user);
    }

    // получение клиента по номеру паспорта
    @Override
    @Transactional(readOnly = true)
    public Client getClientByDocumentId(String documentId) {
        return clientRepository.findByDocumentId(documentId)
                .orElse(null);
    }

    // получение клиента по id
    @Override
    @Transactional(readOnly = true)
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с указанным Id не найден")
        );

        return clientMapper.toDto(client);
    }

}

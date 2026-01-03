package my.project.clientProcessing.service.impl;

import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ClientCreateDto;
import my.project.clientProcessing.entity.user.User;
import my.project.clientProcessing.exception.AlreadyExistException;
import my.project.clientProcessing.mapper.UserMapper;
import my.project.clientProcessing.repository.UserRepository;
import my.project.clientProcessing.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // создание User
    @Override
    @Transactional
    public User createUser(ClientCreateDto clientCreateDto) {

        // проверка на существование User с таким email
        if (userRepository.existsByEmail(clientCreateDto.email())) {
            throw new AlreadyExistException("Пользователь с такием email уже существует");
        }

        // создание User + хеширование пароля
        User user = userMapper.toEntity(clientCreateDto);
        user.setPassword(passwordEncoder.encode(clientCreateDto.password()));

        return userRepository.save(user);
    }

}

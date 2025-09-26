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

    @Override
    @Transactional
    public User createUser(ClientCreateDto clientCreateDto) {
        if (getUserByEmail(clientCreateDto.email()) != null) {
            throw new AlreadyExistException("Пользователь с такием email уже существует");
        }

        User user = userMapper.toEntity(clientCreateDto);
        user.setPassword(passwordEncoder.encode(clientCreateDto.password()));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

}

package com.example.services;

import com.example.dto.UsersDto;
import com.example.exception.AuthException;
import com.example.mapper.UserMapper;
import com.example.models.Roles;
import com.example.models.Users;
import com.example.repositories.UserRepository;
import com.example.security.config.EncoderConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UsersDto> findByName(String name){
        return UserMapper.optionalEntityToDto(userRepository.findUsersByName(name));
    }

    @Transactional
    public UsersDto save(Users user){
        Optional<UsersDto> userDataBase = findByName(user.getName());
        if (userDataBase.isEmpty()){
            user.setPassword(EncoderConfig.getPasswordEncoder().encode(user.getPassword()));
            return UserMapper.entityToDto(userRepository.save(user));
        }
        throw new AuthException("The user is already registered");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findUsersByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return User
                .withUsername(username)
                .password(user.get().getPassword())
                .roles(userRepository.getRolesByUserName(username)
                        .stream()
                        .map(Roles::getName)
                        .collect(Collectors.joining(","))
                )
                .build();
    }
}

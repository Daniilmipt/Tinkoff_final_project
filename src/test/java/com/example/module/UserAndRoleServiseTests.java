package com.example.module;

import com.example.dto.UsersDto;
import com.example.mapper.UserMapper;
import com.example.models.Users;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAndRoleServiseTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void save(){
        Users user = new Users();
        user.setPassword("q");

        when(userRepository.save(ArgumentMatchers.any(Users.class))).thenReturn(user);
        UsersDto usersDto = userService.save(user);

        assertNotNull(usersDto);
        assertEquals(usersDto, UserMapper.entityToDto(user));
        verify(userRepository).save(user);
    }

    @Test
    public void findByName(){
        Users user = new Users();
        user.setPassword("q");
        user.setName("test");

        when(userRepository.findUsersByName(anyString())).thenReturn(Optional.of(user));
        Optional<UsersDto> usersDto = userService.findByName("test");

        assertNotNull(usersDto);
        assertEquals(usersDto, UserMapper.optionalEntityToDto(Optional.of(user)));
        verify(userRepository).findUsersByName("test");
    }
}

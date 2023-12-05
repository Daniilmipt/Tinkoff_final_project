package com.example.mapper;

import com.example.dto.UsersDto;
import com.example.models.Users;

import java.util.Optional;

public class UserMapper {
    public static UsersDto entityToDto(Users user){
        UsersDto usersDto = new UsersDto();
        usersDto.setId(user.getId());
        usersDto.setPassword(user.getPassword());
        usersDto.setName(usersDto.getName());

        return usersDto;
    }

    public static Optional<UsersDto> optionalEntityToDto(Optional<Users> entity){
        if (entity.isEmpty()){
            return Optional.empty();
        }
        UsersDto usersDto = new UsersDto();
        usersDto.setId(entity.get().getId());
        usersDto.setPassword(entity.get().getPassword());
        usersDto.setName(entity.get().getName());

        return Optional.of(usersDto);
    }
}

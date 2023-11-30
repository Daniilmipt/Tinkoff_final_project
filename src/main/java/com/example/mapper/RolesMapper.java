package com.example.mapper;

import com.example.dto.RolesDto;
import com.example.models.Roles;

import java.util.Optional;


public class RolesMapper {
    public static RolesDto entityToDto(Roles entity){
        RolesDto rolesDto = new RolesDto();
        rolesDto.setId(entity.getId());
        rolesDto.setName(entity.getName());

        return rolesDto;
    }

    public static Optional<RolesDto> optionalEntityToDto(Optional<Roles> entity){
        if (entity.isEmpty()){
            return Optional.empty();
        }
        RolesDto rolesDto = new RolesDto();
        rolesDto.setId(entity.get().getId());
        rolesDto.setName(entity.get().getName());

        return Optional.of(rolesDto);
    }
}

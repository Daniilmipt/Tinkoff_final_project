package com.example.mapper;

import com.example.dto.RolesDto;
import com.example.models.Roles;


public class RolesMapper {
    public static RolesDto entityToDto(Roles entity){
        RolesDto rolesDto = new RolesDto();
        rolesDto.setId(entity.getId());
        rolesDto.setName(entity.getName());

        return rolesDto;
    }

}

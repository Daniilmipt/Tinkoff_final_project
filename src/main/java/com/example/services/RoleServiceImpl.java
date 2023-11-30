package com.example.services;

import com.example.dto.RolesDto;
import com.example.mapper.RolesMapper;
import com.example.models.Roles;
import com.example.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl {
    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<RolesDto> findRolesByName(String name){
        return RolesMapper.optionalEntityToDto(roleRepository.findRolesByName(name));
    }

    public RolesDto save(Roles role){
        Optional<Roles> rolesDataBase = roleRepository.findRolesByName(role.getName());
        return RolesMapper.entityToDto(rolesDataBase.orElseGet(() -> roleRepository.save(role)));
    }
}

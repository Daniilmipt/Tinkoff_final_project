package com.example.module;

import com.example.dto.RolesDto;
import com.example.mapper.RolesMapper;
import com.example.models.Roles;
import com.example.repositories.RoleRepository;
import com.example.services.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void save_test() {
        Roles role = new Roles();
        role.setName("test");


        when(roleRepository.save(ArgumentMatchers.any(Roles.class))).thenReturn(role);
        RolesDto roleSaved = roleService.save(role);

        assertNotNull(roleSaved);
        assertEquals(roleSaved, RolesMapper.entityToDto(role));
        verify(roleRepository).save(role);
        verify(roleRepository).findRolesByName("test");
    }

}

package com.example.module;

import com.example.dto.SubjectTypeDto;
import com.example.mapper.SubjectTypeMapper;
import com.example.models.SubjectType;
import com.example.repositories.SubjectTypeRepository;
import com.example.services.SubjectTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectTypeServiceTests {
    @InjectMocks
    private SubjectTypeService subjectTypeService;

    @Mock
    private SubjectTypeRepository subjectTypeRepository;

    @Test
    public void save_test() {
        SubjectType subjectType = new SubjectType();
        subjectType.setName("test");


        when(subjectTypeRepository.save(ArgumentMatchers.any(SubjectType.class))).thenReturn(subjectType);
        SubjectTypeDto subjectTypeSaved = subjectTypeService.save(subjectType);

        assertNotNull(subjectTypeSaved);
        assertEquals(subjectTypeSaved, SubjectTypeMapper.entityToDto(subjectType));
        verify(subjectTypeRepository).save(subjectType);
        verify(subjectTypeRepository).findByName("test");
    }
}

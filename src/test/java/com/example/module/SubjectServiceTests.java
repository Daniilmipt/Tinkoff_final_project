package com.example.module;

import com.example.dto.SubjectDto;
import com.example.mapper.SubjectMapper;
import com.example.models.Subject;
import com.example.repositories.SubjectRepository;
import com.example.services.SubjectService;
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
public class SubjectServiceTests {
    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Test
    public void save_test() {
        Subject subject = new Subject();
        subject.setName("test");


        when(subjectRepository.save(ArgumentMatchers.any(Subject.class))).thenReturn(subject);
        SubjectDto subjectSaved = subjectService.save(subject);

        assertNotNull(subjectSaved);
        assertEquals(subjectSaved, SubjectMapper.entityToDto(subject));
        verify(subjectRepository).save(subject);
        verify(subjectRepository).findByName("test");
    }
}

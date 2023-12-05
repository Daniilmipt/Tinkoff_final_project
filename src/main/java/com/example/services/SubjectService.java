package com.example.services;

import com.example.dto.SubjectDto;
import com.example.mapper.SubjectMapper;
import com.example.models.Subject;
import com.example.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectDto save(Subject subject) {
        Optional<Subject> subjectDtoDataBase = subjectRepository.findByName(subject.getName());
        return SubjectMapper.entityToDto(subjectDtoDataBase.orElseGet(() -> subjectRepository.save(subject)));
    }
}

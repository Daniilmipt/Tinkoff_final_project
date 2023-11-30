package com.example.services;

import com.example.dto.SubjectDto;
import com.example.mapper.SubjectMapper;
import com.example.models.Subject;
import com.example.models.SubjectType;
import com.example.repositories.SubjectRepository;
import com.example.repositories.SubjectTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Optional<SubjectDto> findByName(String subjectName) {
        return SubjectMapper.optionalEntityToDto(subjectRepository.findByName(subjectName));
    }

    public SubjectDto save(Subject subject) {
        Optional<SubjectDto> subjectDtoDataBase = findByName(subject.getName());
        return subjectDtoDataBase.orElseGet(() -> SubjectMapper.entityToDto(subjectRepository.save(subject)));
    }
}

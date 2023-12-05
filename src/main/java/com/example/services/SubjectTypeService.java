package com.example.services;

import com.example.dto.SubjectTypeDto;
import com.example.mapper.SubjectTypeMapper;
import com.example.models.SubjectType;
import com.example.repositories.SubjectTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;

    public SubjectTypeService(SubjectTypeRepository subjectTypeRepository) {
        this.subjectTypeRepository = subjectTypeRepository;
    }

    public SubjectTypeDto save(SubjectType subjectType){
        Optional<SubjectType> subjectTypeDtoDataBase = subjectTypeRepository.findByName(subjectType.getName());
        return SubjectTypeMapper.entityToDto(subjectTypeDtoDataBase.orElseGet(() -> subjectTypeRepository.save(subjectType)));
    }
}

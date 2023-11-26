package com.example.services;

import com.example.dto.SubjectTypeDto;
import com.example.mapper.SubjectTypeMapper;
import com.example.models.SubjectType;
import com.example.repositories.SubjectTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectTypeServiceImpl {
    private final SubjectTypeRepository subjectTypeRepository;

    public SubjectTypeServiceImpl(SubjectTypeRepository subjectTypeRepository) {
        this.subjectTypeRepository = subjectTypeRepository;
    }

    public SubjectTypeDto save(SubjectType subjectType){
        Optional<SubjectTypeDto> subjectTypeDtoDataBase = findByName(subjectType.getName());
        return subjectTypeDtoDataBase.orElseGet(() -> SubjectTypeMapper.entityToDto(
                subjectTypeRepository.save(subjectType)
        ));
    }

    public Optional<SubjectTypeDto> findByName(String subjectTypeName){
        return SubjectTypeMapper.optionalEntityToDto(subjectTypeRepository.findByName(subjectTypeName));
    }
}

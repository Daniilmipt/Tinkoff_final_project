package com.example.mapper;

import com.example.dto.RolesDto;
import com.example.dto.SubjectDto;
import com.example.dto.SubjectTypeDto;
import com.example.models.Roles;
import com.example.models.Subject;
import com.example.models.SubjectType;

import java.util.Optional;

public class SubjectMapper {
    public static SubjectDto entityToDto(Subject entity){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(entity.getId());
        subjectDto.setName(entity.getName());
        subjectDto.setSubjectTypeId(entity.getSubjectTypeId());

        return subjectDto;
    }

    public static Optional<SubjectDto> optionalEntityToDto(Optional<Subject> entity){
        if (entity.isEmpty()){
            return Optional.empty();
        }
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(entity.get().getId());
        subjectDto.setName(entity.get().getName());
        subjectDto.setSubjectTypeId(entity.get().getSubjectTypeId());

        return Optional.of(subjectDto);
    }

    public static Subject dtoToEntity(SubjectDto dto){
        Subject subject = new Subject();
        subject.setId(dto.getId());
        subject.setName(dto.getName());
        subject.setSubjectTypeId(dto.getSubjectTypeId());

        return subject;
    }
}

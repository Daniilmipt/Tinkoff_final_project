package com.example.mapper;

import com.example.dto.SubjectDto;
import com.example.models.Subject;


public class SubjectMapper {
    public static SubjectDto entityToDto(Subject entity){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(entity.getId());
        subjectDto.setName(entity.getName());
        subjectDto.setSubjectTypeId(entity.getSubjectTypeId());

        return subjectDto;
    }
}

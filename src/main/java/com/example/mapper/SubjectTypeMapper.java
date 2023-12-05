package com.example.mapper;

import com.example.dto.SubjectTypeDto;
import com.example.models.SubjectType;

public class SubjectTypeMapper {
    public static SubjectTypeDto entityToDto(SubjectType entity){
        SubjectTypeDto subjectTypeDto = new SubjectTypeDto();
        subjectTypeDto.setId(entity.getId());
        subjectTypeDto.setName(entity.getName());

        return subjectTypeDto;
    }
}

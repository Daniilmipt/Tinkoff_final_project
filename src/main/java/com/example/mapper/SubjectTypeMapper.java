package com.example.mapper;

import com.example.dto.SubjectTypeDto;
import com.example.models.SubjectType;

import java.util.Optional;

public class SubjectTypeMapper {
    public static SubjectTypeDto entityToDto(SubjectType entity){
        SubjectTypeDto subjectTypeDto = new SubjectTypeDto();
        subjectTypeDto.setId(entity.getId());
        subjectTypeDto.setName(entity.getName());

        return subjectTypeDto;
    }

    public static SubjectType dtoToEntity(SubjectTypeDto dto){
        SubjectType subjectType = new SubjectType();
        subjectType.setId(dto.getId());
        subjectType.setName(dto.getName());

        return subjectType;
    }

    public static Optional<SubjectTypeDto> optionalEntityToDto(Optional<SubjectType> entity){
        if (entity.isEmpty()){
            return Optional.empty();
        }
        SubjectTypeDto subjectTypeDto = new SubjectTypeDto();
        subjectTypeDto.setId(entity.get().getId());
        subjectTypeDto.setName(entity.get().getName());

        return Optional.of(subjectTypeDto);
    }
}

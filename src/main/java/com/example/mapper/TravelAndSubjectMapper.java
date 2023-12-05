package com.example.mapper;

import com.example.dto.TravelAndSubjectDto;
import com.example.models.TravelAndSubject;

public class TravelAndSubjectMapper {
    public static TravelAndSubjectDto entityToDto(TravelAndSubject entity){
        TravelAndSubjectDto travelAndSubjectDto = new TravelAndSubjectDto();
        travelAndSubjectDto.setFeatures(entity.getTravelAndSubjectId().getFeatures());
        travelAndSubjectDto.setEndDateTime(entity.getEndDateTime());
        travelAndSubjectDto.setStartDateTime(entity.getStartDateTime());
        travelAndSubjectDto.setTotalAmount(entity.getTotalAmount());

        return travelAndSubjectDto;
    }
}

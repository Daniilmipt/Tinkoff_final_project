package com.example.mapper;

import com.example.dto.TravelAndSubjectDto;
import com.example.models.TravelAndSubject;

import java.util.Optional;

public class TravelAndSubjectMapper {
    public static TravelAndSubjectDto entityToDto(TravelAndSubject entity){
        TravelAndSubjectDto travelAndSubjectDto = new TravelAndSubjectDto();
        travelAndSubjectDto.setFeatures(entity.getTravelAndSubjectId().getFeatures());
        travelAndSubjectDto.setEndDateTime(entity.getEndDateTime());
        travelAndSubjectDto.setStartDateTime(entity.getStartDateTime());
        travelAndSubjectDto.setTotalAmount(entity.getTotalAmount());

        return travelAndSubjectDto;
    }

    public static Optional<TravelAndSubjectDto> optionalEntityToDto(Optional<TravelAndSubject> entity){
        if (entity.isEmpty()){
            return Optional.empty();
        }
        TravelAndSubjectDto travelAndSubjectDto = new TravelAndSubjectDto();
        travelAndSubjectDto.setFeatures(entity.get().getTravelAndSubjectId().getFeatures());
        travelAndSubjectDto.setEndDateTime(entity.get().getEndDateTime());
        travelAndSubjectDto.setStartDateTime(entity.get().getStartDateTime());
        travelAndSubjectDto.setTotalAmount(entity.get().getTotalAmount());

        return Optional.of(travelAndSubjectDto);
    }
}

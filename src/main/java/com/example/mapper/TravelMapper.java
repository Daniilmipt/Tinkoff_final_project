package com.example.mapper;

import com.example.dto.TravelDto;
import com.example.models.Travel;

public class TravelMapper {
    public static TravelDto entityToDto(Travel entity){
        TravelDto travelDto = new TravelDto();
        travelDto.setTravelId(entity.getTravelId());
        travelDto.setUserId(entity.getUserId());
        travelDto.setDeleted(entity.getDeleted());
        travelDto.setTotalAmount(entity.getTotalAmount());
        travelDto.setStartDateTime(entity.getStartDateTime());
        travelDto.setEndDateTime(entity.getEndDateTime());

        return travelDto;
    }
}

package com.example.mapper;

import com.example.dto.TravelDto;
import com.example.models.Travel;

import java.util.Optional;

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

    public static Optional<TravelDto> optionalEntityToDto(Optional<Travel> entity){
        if (entity.isEmpty()){
            return Optional.empty();
        }
        TravelDto travelDto = new TravelDto();
        travelDto.setTravelId(entity.get().getTravelId());
        travelDto.setUserId(entity.get().getUserId());
        travelDto.setDeleted(entity.get().getDeleted());
        travelDto.setTotalAmount(entity.get().getTotalAmount());
        travelDto.setStartDateTime(entity.get().getStartDateTime());
        travelDto.setEndDateTime(entity.get().getEndDateTime());

        return Optional.of(travelDto);
    }
}

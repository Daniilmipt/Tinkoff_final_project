package com.example.dto;

import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PathDto {
    public PathDto(List<AviaDto> aviaDto, List<HotelDto> hotelDto){
        this.hotelDto = hotelDto;
        this.aviaDto = aviaDto;
    }

    @JsonProperty("hotel")
    private List<HotelDto> hotelDto;

    @JsonProperty("avia")
    private List<AviaDto> aviaDto;
}

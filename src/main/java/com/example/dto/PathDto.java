package com.example.dto;

import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PathDto {
    @JsonProperty("hotel")
    private List<HotelDto> hotelDto;

    @JsonProperty("avia")
    private List<AviaDto> aviaDto;
}

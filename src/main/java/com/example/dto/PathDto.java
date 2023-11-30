package com.example.dto;

import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/*
Итоговая сущность со всеми данными по путешествию.
hotelDto - список отелей с order (порядок пребывания в них)
aviaDto - список авиарейсов с их порядком(order)
 */
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

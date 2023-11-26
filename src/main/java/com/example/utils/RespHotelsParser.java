package com.example.utils;

import com.example.dto.hotel.HotelDto;
import com.example.request.models.hotels.HotelRequest;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RespHotelsParser {
    @Getter
    private final JsonNode response;

    private final static Map<String, String> mapCityTranslit = TransliterationCity.mapCityTranslit;

    public RespHotelsParser(JsonNode response){
        this.response = response;
    }

    public List<HotelDto> getInfo(HotelRequest request){
        List<HotelDto> hotelList = new ArrayList<>();

        for (JsonNode response : response){
            if (flagsCheck(request, response)){
                HotelDto hotel = new HotelDto();
                hotel.setStartDateTime(LocalDateTime.of(request.getStartDateTime(), LocalTime.MIDNIGHT));
                hotel.setEndDateTime(LocalDateTime.of(request.getEndDateTime(), LocalTime.NOON));
                hotel.setHotelName(response.get("hotelName").asText());
                hotel.setHotelId(response.get("hotelId").asText());
                hotel.setStars((short) response.get("stars").asInt());
                hotel.setPriceAvg(response.get("priceAvg").asDouble());
                hotel.setCity(mapCityTranslit.get(request.getCity()));
                hotel.setOrder(request.getOrder());

                hotelList.add(hotel);
            }
        }
        return hotelList;
    }

    private boolean flagsCheck(HotelRequest request, JsonNode response){
        return request.getStars().contains((short) response.get("stars").asInt())
                && response.get("priceAvg").asDouble() <= request.getMaxPrice();
    }
}

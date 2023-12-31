package com.example.utils;

import com.example.SubjectTypeEnum;
import com.example.dto.hotel.HotelDto;
import com.example.request.models.hotels.HotelRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
public class RespHotelsParser {
    private final JsonNode response;
    private final JsonMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private final static Map<String, String> mapCityTranslit = TransliterationCity.mapCityTranslit;

    public RespHotelsParser(String response) throws JsonProcessingException {
        this.response = mapper.readTree(response);
    }

    public List<HotelDto> getInfo(HotelRequest request){
        List<HotelDto> hotelList = new ArrayList<>();

        for (JsonNode response : response){
            if (flagsCheck(request, response)){
                HotelDto hotel = new HotelDto();
                hotel.setSubjectTypeEnum(SubjectTypeEnum.HOTEL);
                hotel.setStartDateTime(LocalDateTime.of(request.getStartDateTime(), LocalTime.MIDNIGHT));
                hotel.setEndDateTime(LocalDateTime.of(request.getEndDateTime(), LocalTime.NOON));
                hotel.setHotelName(response.get("hotelName").asText());
                hotel.setHotelId(response.get("hotelId").asText());
                hotel.setStars((short) response.get("stars").asInt());
                hotel.setPriceAvg(new BigDecimal(response.get("priceAvg").asText()));
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

package com.example.dto.hotel;

import com.example.SubjectTypeEnum;
import com.example.dto.TravelSubjectDto;
import com.example.models.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.time.LocalDateTime;

/*
Итоговая сущность со всеми данными по отелю
 */
@Data
public class HotelDto implements TravelSubjectDto {

    @JsonProperty("hotel_id")
    private String hotelId;

    @JsonProperty("hotel_name")
    private String hotelName;

    @JsonProperty("start_dt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonProperty("end_dt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    @JsonProperty("city")
    private String city;

    @JsonProperty("stars")
    private Short stars;

    @JsonProperty("price_average")
    private double priceAvg;

    @JsonProperty("order")
    private Short order;

    @Override
    public LocalDateTime getDepartureDateTime() {
        return startDateTime;
    }

    @Override
    public LocalDateTime getArrivalDateTime() {
        return endDateTime;
    }

    @Override
    public double getPrice() {
        return priceAvg;
    }

    public String createJson() throws JsonProcessingException {
        JsonMapper mapper = new JsonMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("hotel_name", hotelName);
        json.put("city", city);
        json.put("stars", stars);
        json.put("order", order);
        return mapper.writeValueAsString(json);
    }

    @Override
    public com.example.models.SubjectType convertToSubjectType(){
        com.example.models.SubjectType subjectType = new com.example.models.SubjectType();
        subjectType.setName(SubjectTypeEnum.HOTEL.get());
        return subjectType;
    }

    @Override
    public Subject convertToSubject(){
        Subject subject = new Subject();
        subject.setName(hotelName);
        return subject;
    }
}

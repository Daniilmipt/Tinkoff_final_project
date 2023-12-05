package com.example.dto.hotel;

import com.example.SubjectTypeEnum;
import com.example.dto.TravelSubjectDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotelDto extends TravelSubjectDto {

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
    private BigDecimal priceAvg;

    @JsonProperty("order")
    private Short order;

    @JsonProperty("content")
    private SubjectTypeEnum subjectTypeEnum;

    @Override
    public LocalDateTime getDepartureDateTime() {
        return startDateTime;
    }

    @Override
    public LocalDateTime getArrivalDateTime() {
        return endDateTime;
    }

    @Override
    public String getName(){
        return hotelName;
    }

    @Override
    public SubjectTypeEnum getSubjectType(){
        return SubjectTypeEnum.HOTEL;
    }

    @Override
    public BigDecimal getPrice(){
        return priceAvg;
    }
}

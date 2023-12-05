package com.example.dto.avia;

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
public class AviaDto extends TravelSubjectDto {

    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("isCharter")
    private boolean isCharter;

    @JsonProperty("validatingCarrier")
    private String carrier;

    @JsonProperty("withBaggage")
    private boolean withBaggage;

    @JsonProperty("withSkiEquipment")
    private boolean withSkiEquipment;

    @JsonProperty("departure_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureDateTime;

    @JsonProperty("departure_airport")
    private String departureAirport;

    @JsonProperty("departure_terminal")
    private String departureTerminal;

    @JsonProperty("departure_city")
    private String departureCity;

    @JsonProperty("arrival_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalDateTime;

    @JsonProperty("arrival_airport")
    private String arrivalAirport;

    @JsonProperty("arrival_terminal")
    private String arrivalTerminal;

    @JsonProperty("arrival_city")
    private String arrivalCity;

    @JsonProperty("order")
    private Short order;

    @JsonProperty("content")
    private SubjectTypeEnum subjectTypeEnum;
}

package com.example.dto.avia;

import com.example.SubjectTypeEnum;
import com.example.dto.TravelSubjectDto;
import com.example.models.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AviaDto implements TravelSubjectDto {

    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("price")
    private double price;

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

    @Override
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    @Override
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String createJson() throws JsonProcessingException {
        JsonMapper mapper = new JsonMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("isCharter", isCharter);
        json.put("validatingCarrier", carrier);
        json.put("withBaggage", withBaggage);
        json.put("withSkiEquipment", withSkiEquipment);
        json.put("departure_airport", departureAirport);
        json.put("departure_terminal", departureTerminal);
        json.put("departure_city", departureCity);
        json.put("arrival_airport", arrivalAirport);
        json.put("arrival_terminal", arrivalTerminal);
        json.put("arrival_city", arrivalCity);
        json.put("order", order);
        return mapper.writeValueAsString(json);
    }

    @Override
    public com.example.models.SubjectType convertToSubjectType(){
        com.example.models.SubjectType subjectType = new com.example.models.SubjectType();
        subjectType.setName(SubjectTypeEnum.AVIA.get());
        subjectType.setId(UUID.randomUUID());
        return subjectType;
    }

    @Override
    public Subject convertToSubject(){
        Subject subject = new Subject();
        subject.setName(carrier);
        subject.setId(UUID.randomUUID());
        return subject;
    }
}

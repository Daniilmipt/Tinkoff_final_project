package com.example.request.models;

import com.example.request.models.aviasales.AviaRequest;
import com.example.request.models.hotels.HotelRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TravelRequest {
    @NotNull
    @Valid
    @JsonProperty("hotel")
    private List<HotelRequest> hotelRequest;

    @NotNull
    @Valid
    @JsonProperty("avia")
    private List<AviaRequest> aviaRequest;
}

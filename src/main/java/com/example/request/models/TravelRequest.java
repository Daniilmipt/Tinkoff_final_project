package com.example.request.models;

import com.example.request.models.aviasales.AviaRequest;
import com.example.request.models.hotels.HotelRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TravelRequest {
    @NotNull
    @JsonProperty("hotel")
    private List<HotelRequest> hotelRequest;

    @NotNull
    @JsonProperty("avia")
    private List<AviaRequest> aviaRequest;
}

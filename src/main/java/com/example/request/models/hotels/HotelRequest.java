package com.example.request.models.hotels;

import com.example.request.models.aviasales.Passangers;
import com.example.request.models.aviasales.Segments;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class HotelRequest {
    @NotNull
    @JsonProperty("city")
    private String city;

    @NotNull
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateTime;

    @NotNull
    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateTime;

    @NotNull
    @JsonProperty("max_price")
    private Double maxPrice;

    @NotNull
    @JsonProperty("stars")
    private Set<Short> stars;

    @NotNull
    @JsonProperty("order")
    private Short order;
}

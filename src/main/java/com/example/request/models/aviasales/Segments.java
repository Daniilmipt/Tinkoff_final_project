package com.example.request.models.aviasales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class Segments {
    @NotNull
    @JsonProperty("from")
    private String destinationCity;

    @NotNull
    @JsonProperty("to")
    private String arrivalCity;

    @NotNull
    @JsonProperty("date")
    private String date;
}

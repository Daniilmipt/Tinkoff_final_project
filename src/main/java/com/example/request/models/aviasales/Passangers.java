package com.example.request.models.aviasales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class Passangers {
    @NotNull
    @JsonProperty("adults")
    private Integer adultsCount;

    @NotNull
    @JsonProperty("children")
    private Integer childrenCount;

    @NotNull
    @JsonProperty("infants")
    private Integer infantsCount;
}

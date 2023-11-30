package com.example.request.models.aviasales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/*
Класс передачи тела запроса в апи авиабилетов.
Представляет информацию о пассажирах
 */
@Data
public class Passangers {
    @NotNull
    @JsonProperty("adults")
    private Integer adultsCount;

    @NotNull
    @JsonProperty("children")
    private Integer childrenCount;

    // это пожилые, нейминг плохой, пока не исправил
    @NotNull
    @JsonProperty("infants")
    private Integer infantsCount;
}

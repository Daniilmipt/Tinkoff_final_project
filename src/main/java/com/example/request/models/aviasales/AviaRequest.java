package com.example.request.models.aviasales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

/*
Класс передачи тела запроса в апи авиабилетов. Это данные, которые вводит пользователь.
Далее он преобразуется в AviaRequestApi
Используется только для этого в makeApiRequest класса AviaServiceImpl
 */
@Data
public class AviaRequest {
    @NotNull
    @JsonProperty("cabin")
    private String cabin;

    @NotNull
    @JsonProperty("passengers")
    private Passangers passangers;

    @NotNull
    @JsonProperty("segments")
    private List<Segments> segmentsList;

    @NotNull
    @JsonProperty("isCharter")
    private Set<Boolean> isCharter;

    @NotNull
    @JsonProperty("isBaggage")
    private Set<Boolean> isBaggage;

    @NotNull
    @JsonProperty("isHandBaggage")
    private Set<Boolean> isHandBaggage;

    @NotNull
    @JsonProperty("isSkiEquipment")
    private Set<Boolean> isSkiEquipment;

    @NotNull
    @JsonProperty("maxPrice")
    private Double maxPrice;

    @NotNull
    @JsonProperty("order")
    private Short order;
}

package com.example.request.models.api;

import com.example.request.models.aviasales.AviaRequest;
import com.example.request.models.aviasales.Passangers;
import com.example.request.models.aviasales.Segments;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

import java.util.List;

/*
Класс передачи тела запроса в апи авиабилетов. Он уже передается во внешнее апи.
Конструктор принимает AviaRequest
 */
@Data
public class AviaRequestApi {
    @NotNull
    @JsonProperty("cabin")
    private String cabin;

    @NotNull
    @JsonProperty("composite")
    private Integer composite;

    @NotNull
    @JsonProperty("aviasales")
    private boolean aviasales;

    @NotNull
    @JsonProperty("passengers")
    private Passangers passangers;

    @NotNull
    @JsonProperty("segments")
    private List<Segments> segmentsList;

    public AviaRequestApi(AviaRequest aviaRequest){
        this.cabin = aviaRequest.getCabin();
        this.composite = 0;
        this.aviasales = false;
        this.passangers = aviaRequest.getPassangers();
        this.segmentsList = aviaRequest.getSegmentsList();
    }
}

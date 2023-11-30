package com.example.dto;

import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.example.models.Subject;
import com.example.models.Travel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Data
public class TravelDto {
    @JsonProperty("travel_id")
    private UUID travelId;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("total_amt")
    private Double totalAmount;

    @JsonProperty("start_dttm")
    private LocalDateTime startDateTime;

    @JsonProperty("end_dttm")
    private LocalDateTime endDateTime;

    @JsonProperty("deleted_flg")
    private Integer deleted;

    public static Travel convertToTravel(List<AviaDto> aviaList, List<HotelDto> hotelList, UUID userId){
        Travel travel = new Travel();
        travel.setTravelId(UUID.randomUUID());
        travel.setUserId(userId);
        travel.setDeleted(0);

        Double amt = aviaList.stream().mapToDouble(AviaDto::getPrice).sum()
                + hotelList.stream().mapToDouble(HotelDto::getPrice).sum();

        travel.setTotalAmount(amt);
        travel.setStartDateTime(aviaList.get(0).getArrivalDateTime());
        travel.setEndDateTime(aviaList.get(aviaList.size()-1).getDepartureDateTime());
        return travel;
    }

}

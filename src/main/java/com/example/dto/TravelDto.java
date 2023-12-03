package com.example.dto;

import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.example.models.Travel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TravelDto {
    @JsonProperty("travel_id")
    private UUID travelId;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("total_amt")
    private BigDecimal totalAmount;

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

        BigDecimal amt =
                aviaList
                    .stream()
                    .map(AviaDto::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .add(
                                hotelList
                                        .stream()
                                        .map(HotelDto::getPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        );

        travel.setTotalAmount(amt);
        travel.setStartDateTime(aviaList.get(0).getArrivalDateTime());
        travel.setEndDateTime(aviaList.get(aviaList.size()-1).getDepartureDateTime());
        return travel;
    }

}

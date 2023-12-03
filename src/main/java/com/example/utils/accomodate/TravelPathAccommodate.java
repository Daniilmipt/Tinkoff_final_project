package com.example.utils.accomodate;

import com.example.dto.PathDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.example.exception.InterruptThreadException;
import com.example.request.models.TravelRequest;
import com.example.services.api.AviaService;
import com.example.services.api.HotelsService;
import com.example.utils.RespTravel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Configuration
public class TravelPathAccommodate {
    private final HotelsService hotelsService;

    private final AviaService aviaService;

    public List<PathDto> getTravelPairs(TravelRequest travelRequest) {
        CompletableFuture<List<List<HotelDto>>> hotelsFuture = CompletableFuture.supplyAsync(() ->
        {
            try {
                return hotelsService.handleRequest(travelRequest.getHotelRequest());
            } catch (InterruptedException e) {
                throw new InterruptThreadException(e.getMessage());
            }
        });

        CompletableFuture<List<List<AviaDto>>> aviaFuture = CompletableFuture.supplyAsync(() ->
        {
            try {
                return aviaService.handleRequest(travelRequest.getAviaRequest());
            } catch (InterruptedException e) {
                throw new InterruptThreadException(e.getMessage());
            }
        });

        CompletableFuture.allOf(hotelsFuture, aviaFuture).join();

        List<List<HotelDto>> hotelsResponseList = hotelsFuture.join();
        List<List<AviaDto>> aviaResponseList = aviaFuture.join();

        RespTravel responseTravel = new RespTravel(aviaResponseList, hotelsResponseList);
        return responseTravel.getSubjectPairs();
    }
}

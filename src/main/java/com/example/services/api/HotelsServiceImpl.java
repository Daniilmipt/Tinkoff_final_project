package com.example.services.api;

import com.example.dto.hotel.HotelDto;
import com.example.exception.InterruptThreadException;
import com.example.request.models.hotels.HotelRequest;
import com.example.utils.RespHotelsParser;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class HotelsServiceImpl {
    @Qualifier("WebClientApi")
    private final WebClient webClient;

    @Value("${hotels.api}")
    private String baseUrl;

    private final RateLimiter rateLimiter;

    public HotelsServiceImpl(WebClient.Builder webClientBuilder, @Qualifier("hotel") RateLimiter rateLimiter) {
        this.webClient = webClientBuilder.build();
        this.rateLimiter = rateLimiter;
    }

    private String handleUrl(HotelRequest hotelRequest) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("location", hotelRequest.getCity())
                .queryParam("checkIn", hotelRequest.getStartDateTime())
                .queryParam("checkOut", hotelRequest.getEndDateTime())
                .build()
                .toUriString();
    }

    // асинхронно получаем данные
    public List<List<HotelDto>> handleRequest(List<HotelRequest> hotelRequests) throws InterruptedException {
        return rateLimiter.executeSupplier(() -> {
            List<List<HotelDto>> hotelDtoList = new ArrayList<>(hotelRequests.size());
            for (int i = 0; i < hotelRequests.size(); i++) {
                hotelDtoList.add(new ArrayList<>());
            }
            CountDownLatch latch = new CountDownLatch(hotelRequests.size());

            Flux<JsonNode> responses = Flux.concat(
                    hotelRequests.stream()
                            .map(this::makeApiRequest)
                            .collect(Collectors.toList())
            );

            responses
                    .index()
                    .subscribe(
                            response -> {
                                int index = response.getT1().intValue();
                                JsonNode responseFinal = response.getT2();
                                HotelRequest hotelRequest = hotelRequests.get(index);

                                RespHotelsParser respHotelsParser = new RespHotelsParser(responseFinal);
                                hotelDtoList.set(hotelRequest.getOrder(), respHotelsParser.getInfo(hotelRequest));
                                latch.countDown();
                            }
                    );
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new InterruptThreadException("Can not wait all avia api requests");
            }
            return hotelDtoList;
        });
    }

    public Mono<JsonNode> makeApiRequest(HotelRequest hotelRequest){
        return webClient.get()
                .uri(handleUrl(hotelRequest))
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}

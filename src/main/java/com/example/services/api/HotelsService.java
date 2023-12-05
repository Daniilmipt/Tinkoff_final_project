package com.example.services.api;

import com.example.dto.hotel.HotelDto;
import com.example.exception.CustomRuntimeException;
import com.example.exception.InterruptThreadException;
import com.example.request.models.hotels.HotelRequest;
import com.example.utils.RespHotelsParser;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class HotelsService {
    @Qualifier("WebClientApi")
    private final WebClient webClient;

    @Value("${hotels.api}")
    private String baseUrl;

    private final RateLimiter rateLimiter;

    public HotelsService(WebClient.Builder webClientBuilder, @Qualifier("hotel") RateLimiter rateLimiter) {
        this.webClient = webClientBuilder.build();
        this.rateLimiter = rateLimiter;
    }

    private URI handleUrl(HotelRequest hotelRequest) {
        return URI.create(UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("location", hotelRequest.getCity())
                .queryParam("checkIn", hotelRequest.getStartDateTime())
                .queryParam("checkOut", hotelRequest.getEndDateTime())
                .build()
                .toUriString());
    }

    public List<List<HotelDto>> handleRequest(List<HotelRequest> hotelRequests) throws InterruptedException {
        return rateLimiter.executeSupplier(() -> {
            List<CompletableFuture<List<HotelDto>>> futures = hotelRequests.stream()
                    .map(this::makeApiRequestAsync)
                    .toList();

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            CountDownLatch latch = new CountDownLatch(1);

            allOf.thenRun(latch::countDown);

            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new InterruptThreadException("Ошибка пока ожидали выполнение всех запросов отелей");
            }

            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        });
    }

    public CompletableFuture<List<HotelDto>> makeApiRequestAsync(HotelRequest hotelRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return makeApiRequest(hotelRequest);
            } catch (IOException | InterruptedException e) {
                throw new CustomRuntimeException(e.getMessage());
            }
        });
    }

    public List<HotelDto> makeApiRequest(HotelRequest hotelRequest) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(handleUrl(hotelRequest))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        RespHotelsParser respHotelsParser = new RespHotelsParser(response.body());
        return respHotelsParser.getInfo(hotelRequest);
    }
}

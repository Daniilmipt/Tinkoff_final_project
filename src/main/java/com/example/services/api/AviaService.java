package com.example.services.api;

import com.example.dto.avia.AviaDto;
import com.example.request.models.api.AviaRequestApi;
import com.example.request.models.aviasales.AviaRequest;
import com.example.utils.RespAviaParser;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AviaService {

    @Qualifier("WebClientApi")
    private final WebClient webClient;

    @Value("${avia-sales.api}")
    private String baseUrl;

    private final RateLimiter rateLimiter;

    public AviaService(WebClient.Builder webClientBuilder, @Qualifier("avia") RateLimiter rateLimiter) {
        this.webClient = webClientBuilder.build();
        this.rateLimiter = rateLimiter;
    }

    public List<List<AviaDto>> handleRequest(List<AviaRequest> aviaRequests) throws InterruptedException {
        return rateLimiter.executeSupplier(() -> {
            List<CompletableFuture<List<AviaDto>>> futures = aviaRequests.stream()
                    .map(this::makeApiRequestAsync)
                    .toList();

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            try {
                allOf.get();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка пока ожидали выполнение всех запросов авиа", e);
            }

            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        });
    }

    public CompletableFuture<List<AviaDto>> makeApiRequestAsync(AviaRequest aviaRequest) {
        return CompletableFuture.supplyAsync(() -> makeApiRequest(aviaRequest));
    }

    public List<AviaDto> makeApiRequest(AviaRequest aviaRequest) {
        try {
            JsonNode response = webClient.post()
                    .uri(baseUrl)
                    .body(Mono.just(new AviaRequestApi(aviaRequest)), AviaRequestApi.class)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            RespAviaParser respAviaParser = new RespAviaParser(response);
            return respAviaParser.getInfo(aviaRequest);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса и получения результата", e);
        }
    }
}

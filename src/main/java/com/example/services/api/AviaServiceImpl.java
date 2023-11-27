package com.example.services.api;

import com.example.dto.avia.AviaDto;
import com.example.exception.InterruptThreadException;
import com.example.request.models.api.AviaRequestApi;
import com.example.request.models.aviasales.AviaRequest;
import com.example.utils.RespAviaParser;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.resilience4j.ratelimiter.RateLimiter;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class AviaServiceImpl {

    @Qualifier("WebClientApi")
    private final WebClient webClient;

    @Value("${avia-sales.api}")
    private String baseUrl;

    private final RateLimiter rateLimiter;

    public AviaServiceImpl(WebClient.Builder webClientBuilder, @Qualifier("avia") RateLimiter rateLimiter) {
        this.webClient = webClientBuilder.build();
        this.rateLimiter = rateLimiter;
    }
    public List<List<AviaDto>> handleRequest(List<AviaRequest> aviaRequests) throws InterruptedException {
        return rateLimiter.executeSupplier(() -> {
            List<List<AviaDto>> aviaDtoList = new ArrayList<>(aviaRequests.size());
            for (int i = 0; i < aviaRequests.size(); i++) {
                aviaDtoList.add(new ArrayList<>());
            }
            CountDownLatch latch = new CountDownLatch(aviaRequests.size());

            Flux<JsonNode> responses = Flux.concat(
                    aviaRequests.stream()
                            .map(this::makeApiRequest)
                            .collect(Collectors.toList())
            );

            responses
                    .index()
                    .subscribe(
                            response -> {
                                int index = response.getT1().intValue();
                                JsonNode responseFinal = response.getT2();
                                AviaRequest aviaRequest = aviaRequests.get(index);

                                RespAviaParser respAviaParser = new RespAviaParser(responseFinal);
                                aviaDtoList.set(aviaRequest.getOrder(), respAviaParser.getInfo(aviaRequest));
                                latch.countDown();
                            },
                            error -> System.out.println("All API Requests Completed")
                    );
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new InterruptThreadException("Can not wait all avia api requests");
            }
            return aviaDtoList;
        });
    }

    public Mono<JsonNode> makeApiRequest(AviaRequest aviaRequest){
        // в .body преобразуем aviaRequest в AviaRequestApi и его уже передаем во внешнее апи
        return webClient.post()
                .uri(baseUrl)
                .body(Mono.just(new AviaRequestApi(aviaRequest)), AviaRequestApi.class)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}

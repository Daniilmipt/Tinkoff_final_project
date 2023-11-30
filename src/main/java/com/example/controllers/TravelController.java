package com.example.controllers;

import com.example.dto.PathDto;
import com.example.dto.TravelAndSubjectDto;
import com.example.dto.UsersDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.example.exception.AuthException;
import com.example.request.models.TravelRequest;
import com.example.services.UserServiceImpl;
import com.example.services.api.AviaServiceImpl;
import com.example.services.api.HotelsServiceImpl;
import com.example.services.common.TravelAccommodationServiceImpl;
import com.example.utils.RespTravel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Validated
public class TravelController {
    //Сервис по получению данных с отелей
    private final HotelsServiceImpl hotelsService;

    //Сервис по получению данных с авиабилетов
    private final AviaServiceImpl aviaSalesService;

    //Итоговый сервис по изменению данных во все таблицах. Подробнее смотерть в нем
    private final TravelAccommodationServiceImpl travelAccommodationService;
    private final UserServiceImpl userService;

    public TravelController(HotelsServiceImpl hotelsService,
                            AviaServiceImpl aviaSalesService,
                            TravelAccommodationServiceImpl travelAccommodationService, UserServiceImpl userService) {
        this.hotelsService = hotelsService;
        this.aviaSalesService = aviaSalesService;
        this.travelAccommodationService = travelAccommodationService;
        this.userService = userService;
    }

    //Эндпоинт по просто путешествий(с бд не взаимодействуем)
    @GetMapping("/show")
    public ResponseEntity<Object> show(@Valid @RequestBody TravelRequest travelRequest) throws InterruptedException, ExecutionException {
        //Асинхроно достаются
        //Надо разместить это в одну функцию, но не знаю пока как сделать
        CompletableFuture<List<List<HotelDto>>> futureHotel = CompletableFuture.supplyAsync(() -> {
            try {
                return hotelsService.handleRequest(
                        travelRequest.getHotelRequest()
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<List<List<AviaDto>>> futureAvia = CompletableFuture.supplyAsync(() -> {
            try {
                return aviaSalesService.handleRequest(
                        travelRequest.getAviaRequest()
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        futureAvia.join();
        futureHotel.join();

        List<List<HotelDto>> hotelsResponseList = futureHotel.get();
        List<List<AviaDto>> aviaResponseList = futureAvia.get();

        RespTravel responseTravel = new RespTravel(aviaResponseList, hotelsResponseList);
        //получаем готовые пары
        List<PathDto> responses = responseTravel.getSubjectPairs();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Эндпоинт по сохранению путешествий в бд. Также как и в show
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody TravelRequest travelRequest, Authentication authentication) throws JsonProcessingException, InterruptedException, ExecutionException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        CompletableFuture<List<List<HotelDto>>> futureHotel = CompletableFuture.supplyAsync(() -> {
            try {
                return hotelsService.handleRequest(
                        travelRequest.getHotelRequest()
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<List<List<AviaDto>>> futureAvia = CompletableFuture.supplyAsync(() -> {
            try {
                return aviaSalesService.handleRequest(
                        travelRequest.getAviaRequest()
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        futureAvia.join();
        futureHotel.join();

        List<List<HotelDto>> hotelsResponseList = futureHotel.get();
        List<List<AviaDto>> aviaResponseList = futureAvia.get();

        RespTravel responseTravel = new RespTravel(aviaResponseList, hotelsResponseList);
        List<PathDto> responses = responseTravel.getSubjectPairs();

        //сохраняем в бд
        List<TravelAndSubjectDto> travelAndSubjectDtosSaved = travelAccommodationService.save(responses, usersDto.get().getId());

        return new ResponseEntity<>(travelAndSubjectDtosSaved, HttpStatus.OK);
    }

    //Эндпоинт по удалению путешествий из бд
    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(Authentication authentication) {
        if (authentication == null)
            throw new AuthException("Do not have permissions");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        //удаляем из бд
        travelAccommodationService.remove(usersDto.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Эндпоинт по получению сохраненных путешествий из бд
    @GetMapping("/get")
    public ResponseEntity<Object> get(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        //получаем из бд
        List<TravelAndSubjectDto> travelAndSubjectDtoListSaved = travelAccommodationService.get(usersDto.get().getId());
        return new ResponseEntity<>(travelAndSubjectDtoListSaved, HttpStatus.OK);
    }
}

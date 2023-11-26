package com.example.controllers;

import com.example.dto.PathDto;
import com.example.dto.TravelAndSubjectDto;
import com.example.dto.UsersDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
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
    private final HotelsServiceImpl hotelsService;
    private final AviaServiceImpl aviaSalesService;
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

    @GetMapping("/show")
    public ResponseEntity<Object> show(@Valid @RequestBody TravelRequest travelRequest) throws InterruptedException, ExecutionException {
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

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

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

        List<TravelAndSubjectDto> travelAndSubjectDtosSaved = travelAccommodationService.save(responses, usersDto.get().getId());

        return new ResponseEntity<>(travelAndSubjectDtosSaved, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        travelAccommodationService.remove(usersDto.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        List<TravelAndSubjectDto> travelAndSubjectDtoListSaved = travelAccommodationService.get(usersDto.get().getId());
        return new ResponseEntity<>(travelAndSubjectDtoListSaved, HttpStatus.OK);
    }
}

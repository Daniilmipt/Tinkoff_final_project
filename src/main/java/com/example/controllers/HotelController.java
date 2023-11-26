package com.example.controllers;

import com.example.services.api.HotelsServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class HotelController {
    private final HotelsServiceImpl hotelsService;

    public HotelController(HotelsServiceImpl hotelsService) {
        this.hotelsService = hotelsService;
    }

//    @PostMapping("/hotels")
//    public ResponseEntity<Object> get(@Valid @RequestBody HotelRequest hotelRequest){
//        System.out.println(hotelRequest);
//        List<HotelDto> response = hotelsService.handleResponse(hotelRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}

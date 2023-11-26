package com.example.controllers;

import com.example.request.models.aviasales.AviaRequest;
import com.example.services.api.AviaServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class AviaController {
    private final AviaServiceImpl aviaSalesService;

    public AviaController(AviaServiceImpl aviaSalesService) {
        this.aviaSalesService = aviaSalesService;
    }

    @PostMapping("/aviasales")
    public void get(@Valid @RequestBody AviaRequest aviaRequest) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        Users user = userService.findByName("misha").get();
//
//        Travel travel = new Travel();
////        travel.setUser(user);
//        travel.setDeleted(0);
//        System.out.println(LocalDate.parse("2023-12-12").atStartOfDay());
//        travel.setStartDateTime(LocalDate.parse("2023-12-12").atStartOfDay());
//        travel.setEndDateTime(LocalDate.parse("2023-12-25").atStartOfDay());
//        travel.setTotalAmount("12345");
//
//        SubjectType subjectType = new SubjectType();
//        subjectType.setName("avia");
//
//        Subject subject = new Subject();
//        subject.setName("S7");
//
//        TravelAndSubject travelAndSubject = new TravelAndSubject();
//        travelAndSubject.setDeleted(0);
//        travelAndSubject.setStartDateTime(LocalDate.parse("2023-12-12").atStartOfDay());
//        travelAndSubject.setEndDateTime(LocalDate.parse("2023-12-25").atStartOfDay());
//        travelAndSubject.setTotalAmount("12345");
//        travelAndSubject.setFeatures("");

//        travelService.save(travelAndSubject, subject, subjectType, travel);


        System.out.println(aviaRequest);
//        List<List<AviaDto>> response = aviaSalesService.handleRequest(aviaRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

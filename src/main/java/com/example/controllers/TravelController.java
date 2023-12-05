package com.example.controllers;

import com.example.dto.PathDto;
import com.example.dto.TravelAndSubjectDto;
import com.example.dto.TravelDto;
import com.example.request.models.TravelRequest;
import com.example.utils.accomodate.ApplicationAccomodate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@Validated
@RequiredArgsConstructor
public class TravelController {

    private final ApplicationAccomodate applicationAccomodate;

    @GetMapping("/show")
    public ResponseEntity<List<PathDto>> show(@Valid @RequestBody TravelRequest travelRequest) {
        return new ResponseEntity<>(applicationAccomodate.show(travelRequest), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<List<PathDto>> save(@Valid @RequestBody TravelRequest travelRequest, Authentication authentication) throws JsonProcessingException, InterruptedException, ExecutionException {
        return new ResponseEntity<>(applicationAccomodate.save(travelRequest, authentication), HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Authenticator.Success> remove(@RequestParam(value="user_id") UUID userId) {
        applicationAccomodate.remove(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TravelAndSubjectDto>> get(Authentication authentication) throws JsonProcessingException {
        return new ResponseEntity<>(applicationAccomodate.get(authentication), HttpStatus.OK);
    }

    @GetMapping("/travelers")
    public ResponseEntity<List<TravelDto>> getTravelers(){
        return new ResponseEntity<>(applicationAccomodate.getAllCurrentTravels(), HttpStatus.OK);
    }
}

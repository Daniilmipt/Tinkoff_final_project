package com.example.services;

import com.example.dto.TravelDto;
import com.example.mapper.TravelMapper;
import com.example.models.Travel;
import com.example.repositories.TravelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TravelService {
    private final TravelRepository travelRepository;

    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public TravelDto save(Travel travel){
        Optional<Travel> travelDataBase = travelRepository.findTravelByTravelId(travel.getTravelId());
        return TravelMapper.entityToDto(travelDataBase.orElseGet(() -> travelRepository.save(travel)));
    }

    @Transactional
    public void remove(UUID userId){
        travelRepository.removeAllByUserId(userId);
    }

    public List<TravelDto> findAllCurrentTravel(){
        return travelRepository.findAllCurrentTravel()
                .stream()
                .map(TravelMapper::entityToDto)
                .collect(Collectors.toList());
    }
}

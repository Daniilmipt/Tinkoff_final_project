package com.example.services;

import com.example.dto.TravelDto;
import com.example.mapper.TravelMapper;
import com.example.models.Subject;
import com.example.models.SubjectType;
import com.example.models.Travel;
import com.example.models.TravelAndSubject;
import com.example.repositories.SubjectRepository;
import com.example.repositories.SubjectTypeRepository;
import com.example.repositories.TravelAndSubjectRepository;
import com.example.repositories.TravelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TravelServiceImpl {
    private final TravelRepository travelRepository;

    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public TravelDto save(Travel travel){
        Optional<TravelDto> travelDto = findTravelByTravelId(travel.getTravelId());
        return travelDto.orElseGet(() -> TravelMapper.entityToDto(travelRepository.save(travel)));
    }

    public Optional<TravelDto> findTravelByTravelId(UUID travelId){
        return TravelMapper.optionalEntityToDto(travelRepository.findTravelByTravelId(travelId));
    }

    public List<UUID> findTravelIdByUserId(UUID userId){
        return travelRepository.findTravelIdByUserId(userId);
    }

    @Transactional
    public void remove(UUID userId){
        travelRepository.removeAllByUserId(userId);
    }

}

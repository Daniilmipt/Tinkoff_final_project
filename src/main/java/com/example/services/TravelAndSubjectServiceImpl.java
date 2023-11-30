package com.example.services;

import com.example.SubjectTypeEnum;
import com.example.dto.*;
import com.example.mapper.TravelAndSubjectMapper;
import com.example.models.TravelAndSubject;
import com.example.repositories.TravelAndSubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TravelAndSubjectServiceImpl {
    private final TravelAndSubjectRepository travelAndSubjectRepository;

    public TravelAndSubjectServiceImpl(TravelAndSubjectRepository travelAndSubjectRepository) {
        this.travelAndSubjectRepository = travelAndSubjectRepository;
    }

    public TravelAndSubjectDto save(TravelAndSubject travelAndSubject, SubjectTypeEnum subjectTypeEnum) {
        TravelAndSubjectDto travelAndSubjectDtoSaved = TravelAndSubjectMapper.entityToDto(travelAndSubjectRepository.save(travelAndSubject));
        travelAndSubjectDtoSaved.setSubjectTypeEnum(subjectTypeEnum);
        return travelAndSubjectDtoSaved;
    }

    public List<TravelAndSubjectDto> findByUserId(UUID userId){
        return travelAndSubjectRepository.findTravelAndSubjectsByUserId(userId)
                .stream()
                .map(TravelAndSubjectMapper::entityToDto)
                .toList();
    }

}

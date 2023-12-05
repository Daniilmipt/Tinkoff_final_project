package com.example.services;

import com.example.SubjectTypeEnum;
import com.example.dto.*;
import com.example.mapper.TravelAndSubjectMapper;
import com.example.models.TravelAndSubject;
import com.example.repositories.TravelAndSubjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TravelAndSubjectService {
    private final TravelAndSubjectRepository travelAndSubjectRepository;
    private final JsonMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public TravelAndSubjectService(TravelAndSubjectRepository travelAndSubjectRepository) {
        this.travelAndSubjectRepository = travelAndSubjectRepository;
    }

    public TravelAndSubjectDto save(TravelAndSubject travelAndSubject, SubjectTypeEnum subjectTypeEnum) {
        TravelAndSubjectDto travelAndSubjectDtoSaved = TravelAndSubjectMapper.entityToDto(travelAndSubjectRepository.save(travelAndSubject));
        travelAndSubjectDtoSaved.setSubjectTypeEnum(subjectTypeEnum);
        return travelAndSubjectDtoSaved;
    }

    public List<TravelAndSubjectDto> findByUserId(UUID userId) throws JsonProcessingException {
        List<TravelAndSubjectDto> travelAndSubjectDtoList =  travelAndSubjectRepository.findTravelAndSubjectsByUserId(userId)
                .stream()
                .map(TravelAndSubjectMapper::entityToDto)
                .toList();

        for (TravelAndSubjectDto travelAndSubjectDto : travelAndSubjectDtoList){
            String featureContent = mapper.readTree(travelAndSubjectDto.getFeatures()).get("content").asText();
            SubjectTypeEnum content = featureContent.equals(SubjectTypeEnum.AVIA.get()) ? SubjectTypeEnum.AVIA : SubjectTypeEnum.HOTEL;
            travelAndSubjectDto.setSubjectTypeEnum(content);
        }
        return travelAndSubjectDtoList;
    }
}

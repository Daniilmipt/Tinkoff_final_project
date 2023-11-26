package com.example.services.common;

import com.example.SubjectTypeEnum;
import com.example.dto.*;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.example.models.Subject;
import com.example.models.SubjectType;
import com.example.models.Travel;
import com.example.models.TravelAndSubject;
import com.example.models.keys.TravelAndSubjectId;
import com.example.services.SubjectServiceImpl;
import com.example.services.SubjectTypeServiceImpl;
import com.example.services.TravelAndSubjectServiceImpl;
import com.example.services.TravelServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TravelAccommodationServiceImpl {
    private final SubjectTypeServiceImpl subjectTypeService;
    private final SubjectServiceImpl subjectService;
    private final TravelServiceImpl travelService;
    private final TravelAndSubjectServiceImpl travelAndSubjectService;

    public TravelAccommodationServiceImpl(SubjectTypeServiceImpl subjectTypeService,
                                          SubjectServiceImpl subjectService,
                                          TravelServiceImpl travelService,
                                          TravelAndSubjectServiceImpl travelAndSubjectService) {
        this.subjectTypeService = subjectTypeService;
        this.subjectService = subjectService;
        this.travelService = travelService;
        this.travelAndSubjectService = travelAndSubjectService;
    }

    public List<TravelAndSubjectDto> get(UUID userId){
        return travelAndSubjectService.findByUserId(userId);
    }


    @Transactional
    public void remove(UUID userId){
        travelService.remove(userId);
    }

    public List<TravelAndSubjectDto> save(List<PathDto> responses, UUID userId) throws JsonProcessingException {
        List<TravelAndSubjectDto> travelAndSubjectDtosSaved = new ArrayList<>();

        for (PathDto response : responses){
            List<AviaDto> aviaDtoList = response.getAviaDto();
            List<HotelDto> hotelDtoList = response.getHotelDto();
            int minDim = Math.min(aviaDtoList.size(), hotelDtoList.size());
            Travel travel = TravelDto.convertToTravel(aviaDtoList, hotelDtoList, userId);

            for (int i = 0; i < minDim; i++) {
                AviaDto aviaDto = aviaDtoList.get(i);
                HotelDto hotelDto = hotelDtoList.get(i);

                SubjectType aviaSubjectType = aviaDto.convertToSubjectType();
                SubjectType hotelSubjectType = hotelDto.convertToSubjectType();

                Subject aviaSubject = aviaDto.convertToSubject();
                Subject hotelSubject = hotelDto.convertToSubject();

                travelAndSubjectDtosSaved.add(
                        saveRow(travel, aviaSubject, aviaSubjectType, aviaDto)
                );
                travelAndSubjectDtosSaved.add(
                        saveRow(travel, hotelSubject, hotelSubjectType, hotelDto)
                );
            }

            for (int i = minDim; i < Math.max(aviaDtoList.size(), hotelDtoList.size()); i++) {
                TravelSubjectDto travelSubjectDto = aviaDtoList.size() > hotelDtoList.size() ?
                        aviaDtoList.get(i) : hotelDtoList.get(i);
                travelAndSubjectDtosSaved.add(
                        saveSubjectAndSubjectType(travelSubjectDto, travel)
                );
            }
        }
        return travelAndSubjectDtosSaved;
    }


    public TravelAndSubjectDto saveRow(Travel travel,
                        Subject subject,
                        SubjectType subjectType,
                        TravelSubjectDto travelSubjectDto) throws JsonProcessingException {

        SubjectTypeDto subjectTypeDtoSaved = subjectTypeService.save(subjectType);

        subject.setSubjectTypeId(subjectTypeDtoSaved.getId());
        SubjectDto subjectDtoSaved = subjectService.save(subject);

        return saveData(subjectDtoSaved, travel, travelSubjectDto);
    }

    private TravelAndSubjectDto saveSubjectAndSubjectType(TravelSubjectDto travelSubjectDto, Travel travel) throws JsonProcessingException {
        SubjectType aviaSubjectType = travelSubjectDto.convertToSubjectType();
        Subject aviaSubject = travelSubjectDto.convertToSubject();
        return saveRow(travel, aviaSubject, aviaSubjectType, travelSubjectDto);
    }

    private TravelAndSubjectDto saveData(SubjectDto subjectDto,
                                         Travel travel,
                                         TravelSubjectDto travelSubjectDto) throws JsonProcessingException {
        TravelAndSubject travelAndSubject = new TravelAndSubject();

        TravelDto travelDtoSaved = travelService.save(travel);

        travelAndSubject.setTravelAndSubjectId(new TravelAndSubjectId(travelDtoSaved.getTravelId(), subjectDto.getId()));
        travelAndSubject.setStartDateTime(travelSubjectDto.getDepartureDateTime());
        travelAndSubject.setEndDateTime(travelSubjectDto.getArrivalDateTime());
        travelAndSubject.setTotalAmount(travelSubjectDto.getPrice());
        travelAndSubject.getTravelAndSubjectId().setFeatures(travelSubjectDto.createJson());

        SubjectTypeEnum subjectTypeEnum = travelSubjectDto instanceof AviaDto ?
                SubjectTypeEnum.AVIA : SubjectTypeEnum.HOTEL;
        return travelAndSubjectService.save(travelAndSubject, subjectTypeEnum);
    }
}

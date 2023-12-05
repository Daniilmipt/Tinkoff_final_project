package com.example.utils.accomodate;

import com.example.SubjectTypeEnum;
import com.example.dto.*;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
import com.example.mapper.TravelSubjectDtoMapper;
import com.example.models.Subject;
import com.example.models.SubjectType;
import com.example.models.Travel;
import com.example.models.TravelAndSubject;
import com.example.models.keys.TravelAndSubjectId;
import com.example.services.SubjectService;
import com.example.services.SubjectTypeService;
import com.example.services.TravelAndSubjectService;
import com.example.services.TravelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class TravelAccommodate {
    private final SubjectTypeService subjectTypeService;
    private final SubjectService subjectService;
    private final TravelService travelService;
    private final TravelAndSubjectService travelAndSubjectService;

    private final JsonMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public List<TravelAndSubjectDto> get(UUID userId) throws JsonProcessingException {
        return travelAndSubjectService.findByUserId(userId);
    }


    @Transactional
    public void remove(UUID userId){
        travelService.remove(userId);
    }

    public List<TravelDto> getAllCurrentTravels(){
        return travelService.findAllCurrentTravel();
    }

    @Transactional
    public List<PathDto> save(List<PathDto> responses, UUID userId) throws JsonProcessingException {

        for (PathDto response : responses){
            List<AviaDto> aviaDtoList = response.getAviaDto();
            List<HotelDto> hotelDtoList = response.getHotelDto();
            int minDim = Math.min(aviaDtoList.size(), hotelDtoList.size());
            Travel travel = TravelDto.convertToTravel(aviaDtoList, hotelDtoList, userId);

            for (int i = 0; i < minDim; i++) {
                AviaDto aviaDto = aviaDtoList.get(i);
                HotelDto hotelDto = hotelDtoList.get(i);

                SubjectType aviaSubjectType = new SubjectType(SubjectTypeEnum.AVIA.get());
                SubjectType hotelSubjectType = new SubjectType(SubjectTypeEnum.HOTEL.get());

                Subject aviaSubject = new Subject(aviaDto.getCarrier());
                Subject hotelSubject = new Subject(hotelDto.getHotelName());

                saveRow(travel, aviaSubject, aviaSubjectType, aviaDto);
                saveRow(travel, hotelSubject, hotelSubjectType, hotelDto);
            }

            for (int i = minDim; i < Math.max(aviaDtoList.size(), hotelDtoList.size()); i++) {
                TravelSubjectDto travelSubjectDto = aviaDtoList.size() > hotelDtoList.size() ?
                        aviaDtoList.get(i) : hotelDtoList.get(i);
                saveSubjectAndSubjectType(travelSubjectDto, travel);
            }
        }
        return responses;
    }


    public void saveRow(Travel travel,
                                       Subject subject,
                                       SubjectType subjectType,
                                       TravelSubjectDto travelSubjectDto) throws JsonProcessingException {

        SubjectTypeDto subjectTypeDtoSaved = subjectTypeService.save(subjectType);

        subject.setSubjectTypeId(subjectTypeDtoSaved.getId());
        SubjectDto subjectDtoSaved = subjectService.save(subject);

        saveData(subjectDtoSaved, travel, travelSubjectDto);
    }

    private void saveSubjectAndSubjectType(TravelSubjectDto travelSubjectDto, Travel travel) throws JsonProcessingException {

        SubjectType subjectType = new SubjectType(TravelSubjectDtoMapper.getSubjectTypeName(travelSubjectDto));
        Subject subject = new Subject(TravelSubjectDtoMapper.getSubjectName(travelSubjectDto));
        saveRow(travel, subject, subjectType, travelSubjectDto);
    }

    private void saveData(SubjectDto subjectDto,
                                         Travel travel,
                                         TravelSubjectDto travelSubjectDto) throws JsonProcessingException {
        TravelAndSubject travelAndSubject = new TravelAndSubject();

        TravelDto travelDtoSaved = travelService.save(travel);

        travelAndSubject.setTravelAndSubjectId(new TravelAndSubjectId(travelDtoSaved.getTravelId(), subjectDto.getId()));
        travelAndSubject.setStartDateTime(travelSubjectDto.getDepartureDateTime());
        travelAndSubject.setEndDateTime(travelSubjectDto.getArrivalDateTime());
        travelAndSubject.setTotalAmount(travelSubjectDto.getPrice());
        travelAndSubject.getTravelAndSubjectId().setFeatures(mapper.writeValueAsString(travelSubjectDto));

        SubjectTypeEnum subjectTypeEnum = travelSubjectDto instanceof AviaDto ?
                SubjectTypeEnum.AVIA : SubjectTypeEnum.HOTEL;
        travelAndSubjectService.save(travelAndSubject, subjectTypeEnum);
        travelAndSubjectService.save(travelAndSubject, subjectTypeEnum);
    }
}

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

/*
Итоговый сервис по изменению данных во все таблицах.
 Лучше сделать отдельный модуль, пока не успел
 */
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
        // сохраненный список пар путешествие-объект
        List<TravelAndSubjectDto> travelAndSubjectDtosSaved = new ArrayList<>();

        for (PathDto response : responses){
            List<AviaDto> aviaDtoList = response.getAviaDto();
            List<HotelDto> hotelDtoList = response.getHotelDto();
            //число отелей и перелетов может не совпадать. Вначале сохраняем пары
            int minDim = Math.min(aviaDtoList.size(), hotelDtoList.size());
            // Получаем объект путешествия, внутри считаем итоговую длительность и стоимость,
            // исходя из перелетов и отелей
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

            // добавляем отсавшиеся перелеты или отели
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


    // сохраняем объект путешествия, его тип и само путешествие
    public TravelAndSubjectDto saveRow(Travel travel,
                        Subject subject,
                        SubjectType subjectType,
                        TravelSubjectDto travelSubjectDto) throws JsonProcessingException {

        SubjectTypeDto subjectTypeDtoSaved = subjectTypeService.save(subjectType);

        subject.setSubjectTypeId(subjectTypeDtoSaved.getId());
        SubjectDto subjectDtoSaved = subjectService.save(subject);

        // сохраняем путешествие и его пары путешествие-объект
        return saveData(subjectDtoSaved, travel, travelSubjectDto);
    }

    // сохраняем объект путешествия, его тип и само путешествие
    private TravelAndSubjectDto saveSubjectAndSubjectType(TravelSubjectDto travelSubjectDto, Travel travel) throws JsonProcessingException {
        SubjectType aviaSubjectType = travelSubjectDto.convertToSubjectType();
        Subject aviaSubject = travelSubjectDto.convertToSubject();
        return saveRow(travel, aviaSubject, aviaSubjectType, travelSubjectDto);
    }

    // сохраняем путешествие и его пары путешествие-объект
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

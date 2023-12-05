package com.example.utils.accomodate;

import com.example.SubjectTypeEnum;
import com.example.dto.*;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;
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

        SubjectTypeDto hotelType = null;
        SubjectTypeDto aviaType = null;
        if (!responses.isEmpty()){
            if (!responses.get(0).getHotelDto().isEmpty())
                hotelType = subjectTypeService.save(new SubjectType(SubjectTypeEnum.HOTEL.get()));

            if (!responses.get(0).getAviaDto().isEmpty())
                aviaType = subjectTypeService.save(new SubjectType(SubjectTypeEnum.AVIA.get()));
        }

        for (PathDto response : responses){
            if (!response.getAviaDto().isEmpty() || !response.getHotelDto().isEmpty()) {
                List<AviaDto> aviaDtoList = response.getAviaDto();
                List<HotelDto> hotelDtoList = response.getHotelDto();
                int minDim = Math.min(aviaDtoList.size(), hotelDtoList.size());
                Travel travel = TravelDto.convertToTravel(aviaDtoList, hotelDtoList, userId);

                TravelDto travelDtoSaved = travelService.save(travel);

                for (int i = 0; i < minDim; i++) {
                    AviaDto aviaDto = aviaDtoList.get(i);
                    HotelDto hotelDto = hotelDtoList.get(i);

                    Subject aviaSubject = new Subject(aviaDto.getCarrier());
                    Subject hotelSubject = new Subject(hotelDto.getHotelName());

                    SubjectDto aviaDtoSaved = saveRow(aviaSubject, aviaType);
                    SubjectDto hotelDtoSaved = saveRow(hotelSubject, hotelType);

                    saveData(aviaDtoSaved, travelDtoSaved, aviaDto);
                    saveData(hotelDtoSaved, travelDtoSaved, hotelDto);
                }

                for (int i = minDim; i < Math.max(aviaDtoList.size(), hotelDtoList.size()); i++) {
                    TravelSubjectDto travelSubjectDto;
                    SubjectTypeDto subjectTypeDto;

                    if (aviaDtoList.size() > hotelDtoList.size()){
                        travelSubjectDto = aviaDtoList.get(i);
                        subjectTypeDto = subjectTypeService.save(new SubjectType(SubjectTypeEnum.AVIA.get()));
                    } else {
                        travelSubjectDto = hotelDtoList.get(i);
                        subjectTypeDto = subjectTypeService.save(new SubjectType(SubjectTypeEnum.HOTEL.get()));
                    }

                    Subject subject = new Subject(travelSubjectDto.getSubjectType().get());
                    SubjectDto travelSubjectDtoSaved = saveRow(subject, subjectTypeDto);

                    saveData(travelSubjectDtoSaved, travelDtoSaved, travelSubjectDto);
                }
            }
        }
        return responses;
    }


    public SubjectDto saveRow(Subject subject, SubjectTypeDto subjectTypeDto) {

        subject.setSubjectTypeId(subjectTypeDto.getId());
        return subjectService.save(subject);
    }

    private void saveData(SubjectDto subjectDtoSaved,
                                         TravelDto travelDtoSaved,
                                         TravelSubjectDto travelSubjectDto) throws JsonProcessingException {
        TravelAndSubject travelAndSubject = new TravelAndSubject();
        travelAndSubject.setTravelAndSubjectId(new TravelAndSubjectId(travelDtoSaved.getTravelId(), subjectDtoSaved.getId()));
        travelAndSubject.setStartDateTime(travelSubjectDto.getDepartureDateTime());
        travelAndSubject.setEndDateTime(travelSubjectDto.getArrivalDateTime());
        travelAndSubject.setTotalAmount(travelSubjectDto.getPrice());
        travelAndSubject.getTravelAndSubjectId().setFeatures(mapper.writeValueAsString(travelSubjectDto));

        SubjectTypeEnum subjectTypeEnum = travelSubjectDto.getSubjectType();
        travelAndSubjectService.save(travelAndSubject, subjectTypeEnum);
        travelAndSubjectService.save(travelAndSubject, subjectTypeEnum);
    }
}

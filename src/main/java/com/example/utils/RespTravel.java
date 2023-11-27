package com.example.utils;

import com.example.dto.PathDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/*
Проверяем, что дата и время указаны корректно и сортируем по самому праннему времени прибытия и цене
 */
public class RespTravel {
    private final List<List<AviaDto>> aviaLists;
    private final List<List<HotelDto>> hotelLists;

    public RespTravel(List<List<AviaDto>> aviaLists, List<List<HotelDto>> hotelLists){
        this.aviaLists = aviaLists;
        this.hotelLists = hotelLists;
    }

    public List<PathDto> getSubjectPairs() {
        List<Integer> sizeAviaArray = new ArrayList<>(aviaLists.size());
        List<Integer> sizeHotelArray = new ArrayList<>(hotelLists.size());
        for (List<AviaDto> aviaList : aviaLists) {
            sizeAviaArray.add(aviaList.size());
        }
        for (List<HotelDto> hotelList : hotelLists) {
            sizeHotelArray.add(hotelList.size());
        }

        List<List<Integer>> sequencesAvia = SequenceConstruction.generateSequences(sizeAviaArray);
        List<List<Integer>> sequencesHotel = SequenceConstruction.generateSequences(sizeHotelArray);
        
        List<PathDto> pathDtoList = new ArrayList<>();
        for (List<Integer> sequenceAvia : sequencesAvia) {
            for (List<Integer> sequenceHotel : sequencesHotel) {
                Optional<PathDto> path = filterSubjects(sequenceAvia, sequenceHotel);
                path.ifPresent(pathDtoList::add);
            }
        }
        sortPathList(pathDtoList);
        return pathDtoList.subList(0, 2);
    }

    //проверяем дату
    private Optional<PathDto> filterSubjects(List<Integer> sequenceAvia, List<Integer> sequenceHotel){
        int minDim = Math.min(sequenceHotel.size(), sequenceAvia.size());
        LocalDateTime prevEndDateTime = LocalDateTime.MIN;
        List<AviaDto> aviaDtoList = new ArrayList<>(sequenceAvia.size());
        List<HotelDto> hotelDtoList = new ArrayList<>(sequenceHotel.size());

        for (int i = 0; i < minDim; i++) {
            AviaDto aviaDto = aviaLists.get(i).get(sequenceAvia.get(i));
            if (Duration.between(prevEndDateTime, aviaDto.getDepartureDateTime()).getSeconds() < 0)
                return Optional.empty();

            HotelDto hotelDto = hotelLists.get(i).get(sequenceHotel.get(i));
            if (Duration.between(aviaDto.getArrivalDateTime(), hotelDto.getEndDateTime()).getSeconds() < 0)
                return Optional.empty();

            if (!hotelDto.getCity().equals(aviaDto.getArrivalCity()))
                return Optional.empty();

            prevEndDateTime = hotelDto.getStartDateTime();
            aviaDtoList.add(aviaDto);
            hotelDtoList.add(hotelDto);
        }

        int maxDim = Math.max(sequenceHotel.size(), sequenceAvia.size());
        if (sequenceHotel.size() < sequenceAvia.size()){
            for (int i = minDim; i < maxDim; i++) {
                AviaDto aviaDto = aviaLists.get(i).get(sequenceAvia.get(i));
                if (Duration.between(prevEndDateTime, aviaDto.getDepartureDateTime()).getSeconds() < 0)
                    return Optional.empty();
                prevEndDateTime = aviaDto.getDepartureDateTime();
                aviaDtoList.add(aviaDto);
            }
        } else {
            for (int i = minDim; i < maxDim; i++) {
                HotelDto hotelDto = hotelLists.get(i).get(sequenceHotel.get(i));
                if (Duration.between(prevEndDateTime, hotelDto.getEndDateTime()).getSeconds() < 0)
                    return Optional.empty();
                prevEndDateTime = hotelDto.getStartDateTime();
                hotelDtoList.add(hotelDto);
            }
        }

        return Optional.of(new PathDto(aviaDtoList, hotelDtoList));
    }

    //сортируем по дате и цене
    private void sortPathList(List<PathDto> pathList){
        pathList.sort(new PathComparator());
    }
}

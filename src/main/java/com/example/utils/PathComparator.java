package com.example.utils;

import com.example.dto.PathDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;

import java.math.BigDecimal;
import java.util.Comparator;

public class PathComparator implements Comparator<PathDto> {
    @Override
    public int compare(PathDto pathDto, PathDto t1) {
        BigDecimal firstSum = pathDto.getAviaDto()
                .stream()
                .map(AviaDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(
                        pathDto.getHotelDto()
                                .stream()
                                .map(HotelDto::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                );

        BigDecimal secondSum = t1.getAviaDto()
                .stream()
                .map(AviaDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(
                        t1.getHotelDto()
                                .stream()
                                .map(HotelDto::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                );

        int firstTime = pathDto
                .getAviaDto().get(pathDto.getAviaDto().size()-1)
                .getArrivalDateTime().getNano();

        int secondTime = t1
                .getAviaDto().get(pathDto.getAviaDto().size()-1)
                .getArrivalDateTime().getNano();

        if (firstTime < secondTime){
            return 1;
        }
        else if (firstTime > secondTime){
            return -1;
        }
        else {
            return secondSum.compareTo(firstSum);
        }
    }
}

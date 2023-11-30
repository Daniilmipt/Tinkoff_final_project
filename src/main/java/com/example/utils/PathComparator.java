package com.example.utils;

import com.example.dto.PathDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;

import java.util.Comparator;

//сортируем по дате и цене
public class PathComparator implements Comparator<PathDto> {
    @Override
    public int compare(PathDto pathDto, PathDto t1) {
        Double firstSum = pathDto.getAviaDto().stream().mapToDouble(AviaDto::getPrice).sum()
                + pathDto.getHotelDto().stream().mapToDouble(HotelDto::getPrice).sum();

        Double secondSum = t1.getAviaDto().stream().mapToDouble(AviaDto::getPrice).sum()
                + t1.getHotelDto().stream().mapToDouble(HotelDto::getPrice).sum();

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

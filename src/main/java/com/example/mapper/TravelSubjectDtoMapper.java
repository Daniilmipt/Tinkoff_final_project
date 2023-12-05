package com.example.mapper;

import com.example.SubjectTypeEnum;
import com.example.dto.TravelSubjectDto;
import com.example.dto.avia.AviaDto;
import com.example.dto.hotel.HotelDto;

public class TravelSubjectDtoMapper {
    public static String getSubjectName(TravelSubjectDto travelSubjectDto){
        return travelSubjectDto instanceof AviaDto ?
                ((AviaDto) travelSubjectDto).getCarrier() : ((HotelDto) travelSubjectDto).getHotelName();
    }

    public static String getSubjectTypeName(TravelSubjectDto travelSubjectDto){
        return travelSubjectDto instanceof AviaDto ? SubjectTypeEnum.AVIA.get() : SubjectTypeEnum.HOTEL.get();
    }
}

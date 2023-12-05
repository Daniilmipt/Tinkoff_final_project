package com.example.dto;

import com.example.SubjectTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class TravelSubjectDto {
    public LocalDateTime getDepartureDateTime() {
        return null;
    }

    public LocalDateTime getArrivalDateTime() {
        return null;
    }

    public BigDecimal getPrice() {
        return BigDecimal.ZERO;
    }

    public String getName() {
        return null;
    }

    public SubjectTypeEnum getSubjectType() {
        return null;
    }
}

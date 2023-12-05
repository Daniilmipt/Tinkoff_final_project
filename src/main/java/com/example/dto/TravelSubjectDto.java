package com.example.dto;

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
}

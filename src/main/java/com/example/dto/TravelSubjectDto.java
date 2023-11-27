package com.example.dto;

import com.example.models.Subject;
import com.example.models.SubjectType;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.util.UUID;

/*
Интерфейс для получения общих данных для разных видов путешествий
 */
public interface TravelSubjectDto {
    LocalDateTime getDepartureDateTime();
    LocalDateTime getArrivalDateTime();
    double getPrice();
    String createJson() throws JsonProcessingException;
    SubjectType convertToSubjectType();
    Subject convertToSubject();
}

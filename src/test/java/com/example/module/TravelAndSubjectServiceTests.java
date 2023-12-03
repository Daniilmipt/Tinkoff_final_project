package com.example.module;

import com.example.SubjectTypeEnum;
import com.example.dto.TravelAndSubjectDto;
import com.example.mapper.TravelAndSubjectMapper;
import com.example.models.TravelAndSubject;
import com.example.models.keys.TravelAndSubjectId;
import com.example.repositories.TravelAndSubjectRepository;
import com.example.services.TravelAndSubjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelAndSubjectServiceTests {
    @InjectMocks
    private TravelAndSubjectService travelAndSubjectService;

    @Mock
    private TravelAndSubjectRepository travelAndSubjectRepository;

    @Test
    public void save_test() {
        TravelAndSubject travelAndSubject = new TravelAndSubject();

        TravelAndSubjectId travelAndSubjectId = new TravelAndSubjectId();
        travelAndSubjectId.setFeatures("");

        SubjectTypeEnum subjectTypeEnum = SubjectTypeEnum.AVIA;

        travelAndSubject.setTravelAndSubjectId(travelAndSubjectId);
        TravelAndSubjectDto travelAndSubjectDto = TravelAndSubjectMapper.entityToDto(travelAndSubject);
        travelAndSubjectDto.setSubjectTypeEnum(subjectTypeEnum);


        when(travelAndSubjectRepository.save(ArgumentMatchers.any(TravelAndSubject.class))).thenReturn(travelAndSubject);
        TravelAndSubjectDto travelAndSubjectDtoSaved = travelAndSubjectService.save(travelAndSubject, subjectTypeEnum);

        assertNotNull(travelAndSubjectDto);
        assertEquals(travelAndSubjectDto, travelAndSubjectDtoSaved);
        verify(travelAndSubjectRepository).save(travelAndSubject);
    }

    @Test
    public void findByUserId() throws JsonProcessingException {
        UUID userId = UUID.randomUUID();
        TravelAndSubject travelAndSubject = new TravelAndSubject();

        TravelAndSubjectId travelAndSubjectId = new TravelAndSubjectId();
        travelAndSubjectId.setFeatures("{\"content\":\"AVIA\"}");

        travelAndSubject.setTravelAndSubjectId(travelAndSubjectId);

        when(travelAndSubjectRepository.findTravelAndSubjectsByUserId(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Collections.singletonList(travelAndSubject));

        List<TravelAndSubjectDto> travelAndSubjectDtoListSaved = travelAndSubjectService.findByUserId(userId);

        TravelAndSubjectDto travelAndSubjectDtoTest = TravelAndSubjectMapper.entityToDto(travelAndSubject);
        travelAndSubjectDtoTest.setSubjectTypeEnum(SubjectTypeEnum.AVIA);

        assertNotNull(travelAndSubjectDtoListSaved);
        assertEquals(travelAndSubjectDtoListSaved, Collections.singletonList(travelAndSubjectDtoTest));
        verify(travelAndSubjectRepository).findTravelAndSubjectsByUserId(userId);
    }
}

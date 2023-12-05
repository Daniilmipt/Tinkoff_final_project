package com.example.module;

import com.example.dto.TravelDto;
import com.example.mapper.TravelMapper;
import com.example.models.Travel;
import com.example.repositories.TravelRepository;
import com.example.services.TravelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelServiceTests {
    @InjectMocks
    private TravelService travelService;

    @Mock
    private TravelRepository travelRepository;

    @Test
    public void save_test() {
        Travel travel = new Travel();

        when(travelRepository.save(ArgumentMatchers.any(Travel.class))).thenReturn(travel);
        TravelDto travelDtoSaved = travelService.save(travel);

        assertNotNull(travelDtoSaved);
        assertEquals(travelDtoSaved, TravelMapper.entityToDto(travel));
        verify(travelRepository).save(travel);
    }

    @Test
    public void findAllCurrentTravel() {
        List<Travel> travelList = new ArrayList<>();

        when(travelRepository.findAllCurrentTravel()).thenReturn(travelList);
        List<TravelDto> travelListSaved = travelService.findAllCurrentTravel();

        assertNotNull(travelListSaved);
        assertEquals(
                travelListSaved,
                travelList
                        .stream()
                        .map(TravelMapper::entityToDto)
                        .collect(Collectors.toList())
        );
        verify(travelRepository).findAllCurrentTravel();
    }
}

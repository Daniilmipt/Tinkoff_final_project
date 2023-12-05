package com.example.utils.accomodate;

import com.example.dto.PathDto;
import com.example.dto.TravelAndSubjectDto;
import com.example.dto.TravelDto;
import com.example.dto.UsersDto;
import com.example.request.models.TravelRequest;
import com.example.services.UserService;
import com.example.utils.CacheUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Configuration
public class ApplicationAccomodate {
    private final TravelAccommodate travelAccommodate;
    private final TravelPathAccommodate travelPathAccommodate;
    private final UserService userService;
    private final CacheUtil cacheUtil;

    @Value("${cache-name}")
    private String cacheName;

    @Cacheable(value = "travelCache", key = "#travelRequest")
    public List<PathDto> show(TravelRequest travelRequest) {
        List<PathDto> pathDtoListCached = cacheUtil.getCacheValue(cacheName, travelRequest);
        if (pathDtoListCached == null){
            return travelPathAccommodate.getTravelPairs(travelRequest);
        }
        return pathDtoListCached;
    }

    public List<TravelDto> getAllCurrentTravels(){
        return travelAccommodate.getAllCurrentTravels();
    }

    public List<PathDto> save(TravelRequest travelRequest, Authentication authentication) throws JsonProcessingException, InterruptedException, ExecutionException {
        List<PathDto> pathDtoListCached = cacheUtil.getCacheValue(cacheName, travelRequest);
        if (pathDtoListCached == null){
            return saveNoCache(travelRequest, authentication);
        }
        saveCache(pathDtoListCached, authentication);
        return pathDtoListCached;
    }

    public List<PathDto> saveNoCache(TravelRequest travelRequest, Authentication authentication) throws JsonProcessingException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        List<PathDto> responses = travelPathAccommodate.getTravelPairs(travelRequest);

        return travelAccommodate.save(responses, usersDto.get().getId());
    }


    @CacheEvict(value = "travelCache", allEntries = true)
    public void saveCache(List<PathDto> pathDtoListCached, Authentication authentication) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        travelAccommodate.save(pathDtoListCached, usersDto.get().getId());
    }

    public void remove(UUID userId) {
       travelAccommodate.remove(userId);
    }

    public List<TravelAndSubjectDto> get(Authentication authentication) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UsersDto> usersDto = userService.findByName(userDetails.getUsername());

        return travelAccommodate.get(usersDto.get().getId());
    }
}

package com.example.repositories;

import com.example.models.Travel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TravelRepository extends CrudRepository<Travel, UUID> {
    Optional<Travel> findTravelByTravelId(UUID travelId);

    @Query("select t.travelId from Travel t where t.userId = :userId")
    List<UUID> findTravelIdByUserId(UUID userId);

    @Query("update Travel t set t.deleted = 1 where t.userId=:userId")
    @Modifying
    void removeAllByUserId(UUID userId);
}

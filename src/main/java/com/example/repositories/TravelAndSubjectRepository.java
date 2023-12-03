package com.example.repositories;

import com.example.models.TravelAndSubject;
import com.example.models.keys.TravelAndSubjectId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TravelAndSubjectRepository extends CrudRepository<TravelAndSubject, TravelAndSubjectId> {

    @Query("select tas from Travel as t inner join TravelAndSubject as tas" +
            " on t.travelId=tas.travelAndSubjectId.travel where t.userId=:userId and t.deleted=0")
    List<TravelAndSubject> findTravelAndSubjectsByUserId(UUID userId);
}

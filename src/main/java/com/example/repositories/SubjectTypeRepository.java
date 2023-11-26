package com.example.repositories;

import com.example.models.SubjectType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectTypeRepository extends CrudRepository<SubjectType, UUID> {
    Optional<SubjectType> findByName(String name);
}

package com.example.repositories;

import com.example.models.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, UUID> {
    Optional<Subject> findByName(String name);
}

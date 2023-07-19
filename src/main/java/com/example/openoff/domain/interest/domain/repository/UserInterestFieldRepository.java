package com.example.openoff.domain.interest.domain.repository;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.interest.domain.entity.UserInterestField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestFieldRepository extends JpaRepository<UserInterestField, Long> {
    void deleteAllByUser_Id(String userId);
    boolean existsByUser_IdAndFieldType(String userId, FieldType fieldType);
}

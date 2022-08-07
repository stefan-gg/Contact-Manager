package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {

    Optional<ContactType> findByUid(UUID uuid);

    void deleteByUid(UUID uuid);

    ContactType getContactTypeByContactTypeName(String contactTypeName);
}
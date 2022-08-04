package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {

    ContactType findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

}
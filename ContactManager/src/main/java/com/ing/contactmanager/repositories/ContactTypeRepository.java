package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {

    ContactType findByUid(UUID uuid);

    @Transactional
    void deleteByUid(UUID uuid);

}
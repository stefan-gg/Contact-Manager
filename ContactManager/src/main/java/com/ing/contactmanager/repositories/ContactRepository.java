package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByUid(UUID uuid);

    Optional<Page<Contact>> getContactsByUser_Uid(UUID uuid, Pageable pageable);

    void deleteByUid(UUID uuid);

    Page<Contact> findContactsByUser_Uid(UUID uuid, Pageable pageable);
}
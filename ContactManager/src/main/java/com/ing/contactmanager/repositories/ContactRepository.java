package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByUid(UUID uuid);

    Optional<Page<Contact>> getContactsByUser_Uid(UUID uuid, Pageable pageable);

    void deleteByUid(UUID uuid);

    Page<Contact> findContactsByUser_Uid(UUID uuid, Pageable pageable);

    @Query(value = "SELECT * FROM contacts AS c LEFT JOIN users AS u ON c.user_id = u.id WHERE u" +
            ".email" +
            " LIKE :userEmail AND CONCAT(TRIM(c.email), TRIM(c.first_name), TRIM(c.last_name), " +
            "TRIM(c.phone_number)) LIKE CONCAT('%', :value, '%')",
            nativeQuery = true)
    Optional<Page<Contact>> searchContactsByUser(
            @Param("value") String value, @Param("userEmail") String userEmail,
            Pageable pageable);

    @Query(value = "SELECT * FROM contacts c WHERE CONCAT(TRIM(c.email), TRIM(c.first_name), TRIM" +
            "(c.last_name), TRIM(c.phone_number)) LIKE CONCAT('%', :value, '%')",
            nativeQuery = true)
    Optional<Page<Contact>> searchAllContacts(
            @Param("value") String value, Pageable pageable);
}
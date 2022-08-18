package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByUid(UUID uuid);

    Optional<Page<Contact>> getContactsByUser_Uid(UUID uuid, Pageable pageable);

    void deleteByUid(UUID uuid);

    Page<Contact> findContactsByUser_Uid(UUID uuid, Pageable pageable);

    @Query(value = "SELECT * FROM contacts AS c JOIN users AS u ON c.user_id = u.id WHERE u.email" +
            " LIKE :userEmail AND (c.first_name " +
            "LIKE CONCAT('%', :value, '%') OR " +
            "c.last_name LIKE CONCAT('%', :value, '%') OR c.email LIKE CONCAT('%', :value, '%') " +
            "OR c.phone_number LIKE CONCAT('%', :value, '%'))",
            nativeQuery = true)
    Page<Contact> searchContactsByUser(
            @Param("value") String value, @Param("userEmail") String userEmail,
            Pageable pageable);

    @Query(value = "SELECT * FROM contacts c WHERE c.first_name " +
            "LIKE CONCAT('%', :value, '%') OR " +
            "c.last_name LIKE CONCAT('%', :value, '%') OR c.email LIKE CONCAT('%', :value, '%')"
            +
            "OR c.phone_number LIKE CONCAT('%', :value, '%')",
            nativeQuery = true)
    Page<Contact> searchAllContacts(
            @Param("value") String value, Pageable pageable);
}
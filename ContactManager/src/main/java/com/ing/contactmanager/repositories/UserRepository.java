package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUid(UUID uuid);

    void deleteByUid(UUID uuid);
    Optional<User> getUserByEmail(String email);

    Page<User> findAllByOrderByLastNameAsc(Pageable pageable);

    Optional<User> findByEmail(String email);
}
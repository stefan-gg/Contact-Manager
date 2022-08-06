package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUid(UUID uuid);

    void deleteByUid(UUID uuid);
    List<User> findAllByOrderByLastNameAsc();
}
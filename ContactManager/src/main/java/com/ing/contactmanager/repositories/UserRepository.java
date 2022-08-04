package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
    List<User> findAllByOrderByLastNameAsc();
}
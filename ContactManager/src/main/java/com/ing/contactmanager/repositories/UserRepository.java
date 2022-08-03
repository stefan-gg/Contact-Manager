package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
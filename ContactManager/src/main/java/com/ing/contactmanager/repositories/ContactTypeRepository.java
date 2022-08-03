package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
}
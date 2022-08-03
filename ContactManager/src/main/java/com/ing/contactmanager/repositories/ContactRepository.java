package com.ing.contactmanager.repositories;

import com.ing.contactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
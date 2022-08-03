package com.ing.contactmanager.services;

import com.ing.contactmanager.entities.Contact;

import java.util.List;

public interface CRUDService<T> {
    void deleteById(Integer id);

    T getById(Integer id);

    T createOrUpdate(T object);

    List<T> getAll();
}

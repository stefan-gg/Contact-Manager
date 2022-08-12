package com.ing.contactmanager.services;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CRUDService<T, U> {
    void deleteByUuid(UUID uuid);

    T getByUuid(UUID uuid);

    T createOrUpdate(U object, UUID uuid);

    Page<T> getAll();
}

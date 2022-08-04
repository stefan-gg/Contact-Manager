package com.ing.contactmanager.services;

import java.util.List;
import java.util.UUID;

public interface CRUDService<T> {
    void deleteByUuid(UUID uuid);

    T getByUuid(UUID uuid);

    T createOrUpdate(T object);

    List<T> getAll();
}

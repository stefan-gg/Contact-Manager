package com.ing.contactmanager.services;

import java.util.UUID;

public interface CRUDService<T, U> {
    void deleteByUuid(UUID uuid);

    T getByUuid(UUID uuid);

    T createOrUpdate(U object, UUID uuid);
}

package com.ing.contactmanager.services;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface CRUDService<T, U> {
    void deleteByUuid(UUID uuid);

    T getByUuid(UUID uuid);

    U createOrUpdate(U object);

    List<T> getAll();
}

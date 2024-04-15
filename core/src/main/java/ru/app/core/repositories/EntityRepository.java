package ru.app.core.repositories;

import java.util.List;

public interface EntityRepository<T> {
    void insert(List<T> entities);
}

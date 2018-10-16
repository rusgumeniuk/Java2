package simpleData.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface Dao<T> {
    void insert(T model);
    T getById(UUID id);
    void update(T model);
    void delete(T model);
    List<T> getAll();
}

package tjv.semestralka.weaponsoftheworld.service;


import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface JpaService<T, ID> {
    T create(T e);
    T readById(ID id) throws EntityNotFoundException;
    List<T> readAll();
    T update(T e) throws IllegalArgumentException;
    void deleteById(ID id)throws EntityNotFoundException;
    String getEntityName();
}

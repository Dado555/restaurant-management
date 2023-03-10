package aldentebackend.service;

import java.util.Collection;
import java.util.List;

public interface CRUDService<T> {
    List<T> findAll();
    T findOne(Long id);
    T save(T entity);
    T update(T entity);
    List<T> saveAll(Collection<T> entities);
    void delete(Long id);
}

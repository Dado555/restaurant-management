package aldentebackend.service;

import aldentebackend.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface JPAService<T extends BaseEntity> extends CRUDService<T> {

    Iterable<T> findAll(Sort sorter);
    Page<T> findAll(Pageable page);
}

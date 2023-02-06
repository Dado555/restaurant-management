package aldentebackend.service.impl;

import aldentebackend.model.BaseEntity;
import aldentebackend.service.JPAService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.transaction.annotation.Transactional;

public abstract class JPAServiceImpl<T extends BaseEntity> extends CRUDServiceImpl<T> implements JPAService<T> {

    @Override
    @Transactional(readOnly = true)
    public Iterable<T> findAll(Sort sorter) {
        return getEntityRepository().findAll(sorter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable page) {
        return getEntityRepository().findAll(page);
    }
}

package aldentebackend.service.impl;

import aldentebackend.exception.NotFoundException;
import aldentebackend.model.BaseEntity;
import aldentebackend.service.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CRUDServiceImpl<T extends BaseEntity> implements CRUDService<T> {

    protected abstract JpaRepository<T, Long> getEntityRepository();

    //.filter(User.class::isInstance).map(User.class::cast)
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return (List<T>) getEntityRepository().findAll().stream().filter(n -> n.getActive()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public T findOne(Long id) {
        return findActiveEntity(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return getEntityRepository().save(entity);
    }

    @Override
    public List<T> saveAll(Collection<T> entities) {
        return getEntityRepository().saveAll(entities);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getEntityRepository().delete(findOne(id));
    }

    private T findActiveEntity(Long id) {
        var entity = getEntityRepository().findById(id).orElseThrow(() -> new NotFoundException("Cannot find entity with id: " + id));
        if (Boolean.TRUE.equals(entity.getActive()))
            return entity;

        throw new NotFoundException("Cannot find entity with id: " + id);
    }

}

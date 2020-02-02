package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.model.BaseEntity;
import fr.graynaud.eu4saveconverter.repository.BaseRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends BaseEntity, R extends BaseRepository<E>> implements BaseService<E> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public E findOne(Integer id) {
        return this.repository.getOne(id);
    }

    @Override
    public Optional<E> findOne(Example<E> example) {
        return this.repository.findOne(example);
    }

    @Override
    public List<E> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<E> findAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    @Override
    public List<E> findAll(Iterable<Integer> ids) {
        return this.repository.findAllById(ids);
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<E> findAll(Example<E> example) {
        return this.repository.findAll(example);
    }

    @Override
    public List<E> findAll(Example<E> example, Sort sort) {
        return this.repository.findAll(example, sort);
    }

    @Override
    public Page<E> findAll(Example<E> example, Pageable pageable) {
        return this.repository.findAll(example, pageable);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public long count(Example<E> example) {
        return this.repository.count(example);
    }

    @Override
    public boolean exists(Example<E> example) {
        return this.repository.exists(example);
    }

    @Override
    public boolean exists(Integer id) {
        return this.repository.existsById(id);
    }

    @Override
    public E create(E toCreate) {
        assert toCreate != null;
        return this.repository.save(toCreate);
    }

    @Override
    public E update(E toUpdate) {
        assert toUpdate != null && toUpdate.getId() != null;
        return this.repository.save(toUpdate);
    }

    @Override
    public void delete(E toDelete) {
        if (toDelete != null) {
            this.repository.delete(toDelete);
        }
    }

    @Override
    public List<E> create(Iterable<E> toCreates) {
        assert toCreates != null;
        if (!toCreates.iterator().hasNext()) {
            return new ArrayList<>();
        }

        return this.repository.saveAll(toCreates);
    }

    @Override
    public List<E> update(Iterable<E> toUpdates) {
        assert toUpdates != null;
        toUpdates.forEach(entity -> {
            assert entity != null && entity.getId() != null;
        });

        return this.repository.saveAll(toUpdates);
    }

    @Override
    public void delete(Collection<E> toDelete) {
        this.repository.deleteInBatch(toDelete);
    }
}

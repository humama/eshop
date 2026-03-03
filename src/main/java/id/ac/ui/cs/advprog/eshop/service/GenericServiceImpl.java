package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.BaseRepository;

public class GenericServiceImpl<T extends Product> implements BaseService<T> {

    protected BaseRepository<T> repository;

    public GenericServiceImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T entity) {
        return repository.create(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(String id) {
        return repository.findById(id);
    }

    @Override
    public T update(T entity) {
        return repository.update(entity);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
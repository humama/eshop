package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.Iterator;
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
        Iterator<T> productIterator = repository.findAll();
        List<T> allProducts = new ArrayList<>();
        productIterator.forEachRemaining(allProducts::add);
        return allProducts;
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
    public boolean delete(String id) {
        return repository.delete(id);
    }
}
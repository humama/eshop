package id.ac.ui.cs.advprog.eshop.repository;

import java.util.*;

import id.ac.ui.cs.advprog.eshop.model.Product;

public class InMemoryRepository<T extends Product>
        implements BaseRepository<T> {

    protected List<T> data = new ArrayList<>();

    @Override
    public T create(T entity) {
        if(entity.getId() == null || entity.getId().isEmpty()){
            entity.setId(UUID.randomUUID().toString());
        }
        data.add(entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public T findById(String id) {
        return data.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public T update(T entity) {
        delete(entity.getId());
        data.add(entity);
        return entity;
    }

    @Override
    public void delete(String id) {
        data.removeIf(e -> e.getId().equals(id));
    }
}
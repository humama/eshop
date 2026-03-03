package id.ac.ui.cs.advprog.eshop.repository;

import java.util.*;

import id.ac.ui.cs.advprog.eshop.model.Product;

public class InMemoryRepository<T extends Product> implements BaseRepository<T> {

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
    public Iterator<T> findAll() {
        return data.iterator();
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
        if(entity == null || entity.getId() == null) return null;
        for(int i = 0; i < data.size(); i++){
            T p = data.get(i);
            if(p.getId() != null && p.getId().equals(entity.getId())){
                p.setName(entity.getName());
                p.setQuantity(entity.getQuantity());
                data.set(i, p);
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        if(id == null) return false;
        return data.removeIf(e -> e.getId().equals(id));
    }
}
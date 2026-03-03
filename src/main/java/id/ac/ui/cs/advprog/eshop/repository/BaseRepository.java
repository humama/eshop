package id.ac.ui.cs.advprog.eshop.repository;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface BaseRepository<T extends Product> {
    T create(T entity);
    List<T> findAll();
    T findById(String id);
    T update(T entity);
    void delete(String id);
}
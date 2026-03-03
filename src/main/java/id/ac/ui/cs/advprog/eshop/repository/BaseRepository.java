package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface BaseRepository<T extends Product> {
    T create(T entity);
    Iterator<T> findAll();
    T findById(String id);
    T update(T entity);
    boolean delete(String id);
}
package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface BaseService<T extends Product> {
    T create(T entity);
    List<T> findAll();
    T findById(String id);
    T update(T entity);
    boolean delete(String id);
}
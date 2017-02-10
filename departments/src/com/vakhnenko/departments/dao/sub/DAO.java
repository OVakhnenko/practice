package com.vakhnenko.departments.dao.sub;

import com.vakhnenko.departments.entity.Entity;

import java.util.List;

interface DAO<T extends Entity> {
    T getById(int id);

    void remove(int id);

    void save(T entity);

    List<T> getAll();
}
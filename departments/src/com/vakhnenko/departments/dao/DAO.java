package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.Entity;

import java.util.*;

abstract class DAO<T extends Entity> {
    abstract public void add(T item);

    abstract public void delete(String name);

    abstract public T search(String name);

    abstract public int getSize();

    abstract public List<T> getAll();

    abstract public T getByName(String name);

    public boolean save() {
        return false;
    }

    public List<String> read() {
        return null;
    }
}
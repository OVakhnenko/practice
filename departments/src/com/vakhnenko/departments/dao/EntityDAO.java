package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.*;

import java.util.*;

public abstract class EntityDAO<T extends Entity> extends DAO<T> {
    private String entityStatus = "";
    private List<T> list = new ArrayList<>();

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public void delete(String name) {
        T tmp;

        if ((tmp = search(name)) == null) {
            System.out.println(getEntityStatus() + " \"" + name + "\" not found!");
        } else {
            System.out.println(getEntityStatus() + " \"" + name + "\" removed.");
            list.remove(tmp);
        }
    }

    @Override
    public T search(String name) {
        T result = null;
        for (T item : list) {
            if (item.getName().equals(name)) {
                result = item;
                break;
            }
        }
        return result;
    }

    public boolean exists(String name) {
        return search(name) != null;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(list);
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    abstract void done();
}

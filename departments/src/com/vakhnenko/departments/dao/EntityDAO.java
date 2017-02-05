package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.*;

import java.util.*;

public abstract class EntityDAO<T extends Entity> {
    private List<T> list = new ArrayList<>();
    private String entityStatus = "";

    public void add(T item) {
        list.add(item);
    }

    public void delete(String name) {
        T tmp;

        if ((tmp = search(name)) == null) {
            System.out.println(getEntityStatus() + " \"" + name + "\" not found!");
        } else {
            System.out.println(getEntityStatus() + " \"" + name + "\" removed.");
            list.remove(tmp);
        }
    }

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

    public int getSize() {
        return list.size();
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(list);
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public void printAll() {
        if (list.size() > 0) {
            for (T item : list) {
                print(item);
            }
        } else {
            System.out.println("Erroe! Employees not found!" +
                    ". Type \"create\" for create first employee");
        }
    }

    private void print(T employee) {
        System.out.println(getEntityStatus() + " name \"" + employee.getName() + "\"");
    }
}

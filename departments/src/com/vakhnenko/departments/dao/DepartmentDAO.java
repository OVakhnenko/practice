package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.department.*;

public class DepartmentDAO extends EntityDAO<Department> {

    public DepartmentDAO() {
        setEntityStatus("Department");
    }

    public void create(String name) {
        if (search(name) != null) {
            System.out.println("Department  \"" + name + "\" already exists");
        } else {
            add(new Department(name));
        }
    }

    public Department getByName(String name) {
        return search(name);
    }

    public void done() {
    }

    public boolean save() {
        return false;
    }
}

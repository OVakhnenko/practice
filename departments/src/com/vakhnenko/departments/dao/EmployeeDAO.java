package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.employee.Employee;

import java.util.*;

public class EmployeeDAO<T extends Entity> extends EntityDAO<T> {

    public EmployeeDAO() {
        setEntityStatus("Employee");
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {}

    public String getType(String employeeName) {
        return ((Employee) search(employeeName)).getType();
    }

    public T getByName(String name) {
        return search(name);
    }

    public List<T> getAll(String departmentName) {
        List<T> result = new ArrayList<>();

        for (T list : getAll()) {
            if (((Employee) list).getDepartment().equals(departmentName)) {
                result.add(list);
            }
        }
        return result;
    }

    public List<T> getAll(String departmentName, int age) {
        List<T> result = new ArrayList<>();

        for (T list : getAll()) {
            if (((Employee) list).getDepartment().equals(departmentName)&&(((Employee) list).getAge() == age)) {
                result.add(list);
            }
        }
        return result;
    }
    public int getMaxEmployees(String departmentName, String type) {
        return 0;
    }

    public void done() {}
}

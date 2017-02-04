package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class EntityDAO {
    protected List<Entity> employees = new ArrayList<>();
    private String employeeStatus = "";

    public void add(Entity entiny) {
        employees.add(entiny);
    }

    public void delete(String name) {
        Entity tmp;

        if ((tmp = search(name)) == null) {
            System.out.println(employeeStatus + " \"" + name + "\" not found!");
        } else {
            System.out.println(employeeStatus + " \"" + name + "\" removed.");
            employees.remove(tmp);
        }
    }

    public Entity search(String name) {
        Entity result = null;
        for (Entity employee : employees) {
            if (employee.getName().equals(name)) {
                result = employee;
                break;
            }
        }
        return result;
    }

    public boolean exists(String name) {
        return search(name) != null;
    }

    public void printAll() {
        if (employees.size() > 0) {
            for (Entity employee : employees) {
                print(employee);
            }

        } else {
            System.out.println("No " + employeeStatus.toLowerCase() +
                    ". Type \"create\" for create first " + employeeStatus.toLowerCase() + ".");
        }
    }

    private void print(Entity employee) {
        System.out.println(employeeStatus + " name \"" + employee.getName() + "\"");
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public int getSize() {
        return employees.size();
    }

    public List<Entity> getAll() {
        return Collections.unmodifiableList(employees);
    }
}


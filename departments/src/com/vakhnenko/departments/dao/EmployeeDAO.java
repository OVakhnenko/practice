package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.employee.Employee;

public class EmployeeDAO extends EntityDAO {

    public EmployeeDAO() {
        setEmployeeStatus("Employee");
    }

    void create(String name, String type, int age, String lenguage, String metodology, String department) {
        if (search(name) != null) {
            System.out.println(getEmployeeStatus() + " \"" + name + "\" already exists");
        } else {
            employees.add(new Employee(name, type, age, department));
        }
    }
}

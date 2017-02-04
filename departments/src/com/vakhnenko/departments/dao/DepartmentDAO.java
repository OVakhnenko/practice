package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.department.Department;

import java.io.FileWriter;
import java.io.IOException;

import com.vakhnenko.departments.entity.*;

import static com.vakhnenko.departments.constants.Constants.CREATE_COMMAND;
import static com.vakhnenko.departments.constants.Constants.DEPARTMENT_KEY;

public class DepartmentDAO extends EntityDAO {

    public DepartmentDAO() {
        setEmployeeStatus("Department");
    }

    public void create(String name) {
        if (search(name) != null) {
            System.out.println(getEmployeeStatus() + " \"" + name + "\" already exists");
        } else {
            employees.add(new Department(name));
        }
    }
}

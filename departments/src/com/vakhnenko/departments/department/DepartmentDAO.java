package com.vakhnenko.departments.department;

import com.vakhnenko.departments.employee.*;

public class DepartmentDAO extends EmployeeDAO {

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

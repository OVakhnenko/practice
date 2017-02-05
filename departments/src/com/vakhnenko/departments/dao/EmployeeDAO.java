package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.employee.Employee;

public class EmployeeDAO extends EntityDAO<Employee> {

    public EmployeeDAO() {
        setEntityStatus("Employee");
    }
}

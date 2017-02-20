package com.vakhnenko.departments.dao;


import com.vakhnenko.departments.entity.employee.Employee;

import java.util.List;

public interface EmployeeDAO extends DAO<Employee> {
    List<Employee> find(String departmentName, int age);
}

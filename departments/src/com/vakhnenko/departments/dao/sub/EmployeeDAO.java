package com.vakhnenko.departments.dao.sub;


import com.vakhnenko.departments.employee.Employee;

import java.util.List;

public interface EmployeeDAO extends DAO<Employee> {
    List<Employee> find(String departmentName, int age);
}

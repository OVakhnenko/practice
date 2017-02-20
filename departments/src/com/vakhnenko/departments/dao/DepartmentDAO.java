package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.department.Department;

import java.util.List;

public interface DepartmentDAO extends DAO<Department> {
    List<Department> findTop(String employeeType);
}

package com.vakhnenko.departments.dao.sub;

import com.vakhnenko.departments.entity.department.Department;

import java.util.List;

public interface DepartmentDAO extends DAO<Department> {
    List<Department> findTop(String employeeType);
}

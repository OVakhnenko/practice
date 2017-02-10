package com.vakhnenko.departments.dao.sub;

import com.vakhnenko.departments.department.Department;

import java.util.List;

public interface DepartmentDAO extends DAO<Department> {
    List<Department> findTop(String employeeType);
}

package com.vakhnenko.departments.dao.sub;

import com.vakhnenko.departments.department.Department;

import java.io.Writer;
import java.util.List;

public class DepartmentFileDAO implements DepartmentDAO {
    private Writer writer;

    //Work only with file here
    public DepartmentFileDAO(Writer writer) {
        this.writer = writer;
    }

    @Override
    public Department getById(int id) {
        //read from file name and set it into this department
        Department department = new Department("");
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save(Department entity) {

    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public List<Department> findTop(String employeeType) {
        return null;
    }
}

package com.vakhnenko.departments.dao.sub;


import com.vakhnenko.departments.entity.employee.Employee;

import java.sql.Connection;
import java.util.List;

public class EmployeeDbDAO implements EmployeeDAO {
    private Connection dbConnection;

    public EmployeeDbDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Employee getById(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save(Employee entity) {

    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public List<Employee> find(String departmentName, int age) {
        return null;
    }
}

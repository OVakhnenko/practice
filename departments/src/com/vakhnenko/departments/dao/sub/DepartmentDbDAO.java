package com.vakhnenko.departments.dao.sub;

import com.vakhnenko.departments.department.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDbDAO implements DepartmentDAO {
    private Connection dbConnection;

    public DepartmentDbDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Department getById(int id) {
        Department department = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("select * from departments where id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String departmentName = resultSet.getString(1);
            department = new Department(departmentName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("delete * from departments where id =?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Department department) {
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("insert into departments values(?,?,?,?)");
            preparedStatement.setInt(1, department.getID());
            preparedStatement.setString(2, department.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

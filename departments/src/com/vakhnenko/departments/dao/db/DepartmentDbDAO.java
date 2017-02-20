package com.vakhnenko.departments.dao.db;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.entity.department.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.insertIntoDB;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.swq;

/**
 * Created for practice on 10.02.2017 10:20
 */
public class DepartmentDbDAO implements DepartmentDAO {
    private Connection dbConnection;
    private Statement statement;

    public DepartmentDbDAO(Connection dbConnection){
        this.dbConnection = dbConnection;
        try {
            this.statement = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String name) {
        String query = SELECT_NAME_FROM_DB_DEPARTMENT + WHERE_NAME_IS_EQUAL + swq(name);
        boolean result = false;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    //todo: move it to SERVICE
    public void printAll() {
        try {
            ResultSet rs = statement.executeQuery(SELECT_NAME_FROM_DB_DEPARTMENT);
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println("name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + SELECT_NAME_FROM_DB_DEPARTMENT);
        }
    }

    public void done() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("MySQL error! DB statement not close!");
            }
        }
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("MySQL error! DB connection not close!");
            }
        }
    }

    @Override
    public Department getById(int id) {
        return null;
    }

    @Override
    public void remove(int id) {
        //change to delete by ID
        String query = DELETE_FROM_DB_DEPERTMENT + WHERE_NAME_IS_EQUAL + swq(name);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
    }

    @Override
    public void save(Department department) {
        if (exists(department.getName())) {
            System.out.println("Error! Department " + department.getName() +" already exists!");
        } else {
            insertIntoDB(statement, INSERT_INTO_DB_DEPERTMENT + swq(department.getName() + CLOSING_STRUCTURE);
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

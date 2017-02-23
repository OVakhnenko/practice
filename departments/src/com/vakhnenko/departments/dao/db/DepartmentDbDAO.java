package com.vakhnenko.departments.dao.db;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.dao.file.DepartmentFileDAO;
import com.vakhnenko.departments.entity.department.Department;
import com.vakhnenko.departments.entity.employee.Employee;

import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.*;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created for practice on 10.02.2017 10:20
 */
public class DepartmentDbDAO extends DepartmentDAO {
    private Connection dbConnection;
    private Statement statement;

    public DepartmentDbDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        this.statement = dbConnection.createStatement();
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public List<String> read() {
        return null;
    }

    @Override
    public void create(String name) {
        if (exists(name)) {
            System.out.println("Error! Department " + name + " already exists!");
        } else {
            insertIntoDB(statement, INSERT_INTO_DB_DEPERTMENT + swq(name) + CLOSING_STRUCTURE);
        }
    }

    @Override
    public void delete(String name) {
        String query = DELETE_FROM_DB_DEPERTMENT + WHERE_NAME_IS_EQUAL + swq(name);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
    }

    @Override
    public boolean exists(String name) {
        boolean result = false;
        String query = SELECT_NAME_FROM_DB_DEPARTMENT + WHERE_NAME_IS_EQUAL + swq(name);

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public Department getByName(String name) {
        String query = SELECT_NAME_FROM_DB_DEPARTMENT + WHERE_NAME_IS_EQUAL + swq(name);
        Department result = null;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                result = new Department(name);
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public List<Department> getAll() {
        List<Department> result = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery(SELECT_NAME_FROM_DB_DEPARTMENT);
            while (rs.next()) {
                String name = rs.getString("name");
                result.add(new Department(name));
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + SELECT_NAME_FROM_DB_DEPARTMENT);
        }
        return result;
    }

    @Override
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
}

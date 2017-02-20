package com.vakhnenko.departments.dao.jdbc;

import com.vakhnenko.departments.dao.file.DepartmentFileDAO;

import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.*;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.*;

import java.sql.*;

/**
 * Created for practice on 10.02.2017 10:20
 */
public class DepartmentDbDAO extends DepartmentFileDAO {
    private Connection dbConnection;
    private Statement statement;

    public DepartmentDbDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        this.statement = dbConnection.createStatement();
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

    @Override
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

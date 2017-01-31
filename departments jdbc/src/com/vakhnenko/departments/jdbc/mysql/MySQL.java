package com.vakhnenko.departments.jdbc.mysql;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.vakhnenko.departments.constants.Constants.*;

public class MySQL {
    private Connection dbConnection = null;
    private Statement statement = null;

    public void createDBIfNotExists() throws SQLException {
        if (!(createMySQLDB(CREATE_DB_DEPARTMENT_IF_NOT_EXISTS_SCRIPT, DEPARTMENT_TABLE_NAME)) ||
                (!createMySQLDB(CREATE_DB_EMPLOYEE_IF_NOT_EXISTS_SCRIPT, EMPLOYEE_TABLE_NAME))) {
            System.out.println("MySQL error! Tables not created!");
            System.exit(DB_CREATE_ERROR_EXIT_CODE);
        }
    }

    private boolean createMySQLDB(String createStatement, String bdName) throws SQLException {
        boolean result = false;
        try {
            dbConnection = getDBConnection();
            if (dbConnection == null) {
                System.out.println("MySQL error! Connection is not established");
            } else {
                statement = dbConnection.createStatement();
                statement.execute(createStatement);
                System.out.println("Table \"" + bdName + "\" is created or exists!");
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("MySQL error! Table \"" + bdName + "\" not created!");
        }
        return result;
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL error! MySQL driver not found!");
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println("MySQL error! MySQL DB not connected!");
        }
        return dbConnection;
    }

    public void insertIntoDB(String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("MySQL error! Record not inserted!");
        }
    }

    public void closeMySQLDB() {
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

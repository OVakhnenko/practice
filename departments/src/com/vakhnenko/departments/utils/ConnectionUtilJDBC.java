package com.vakhnenko.departments.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.vakhnenko.departments.utils.Constants.*;

public abstract class ConnectionUtilJDBC {

    static {
        createDBIfNotExists();
    }

    public static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL error! MySQL driver not found!");
            System.exit(DB_DRIVER_ERROR_EXIT_CODE);
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println("MySQL error! MySQL DB not connected!");
            System.exit(DB_CONNECTION_ERROR_EXIT_CODE);
        }
        return dbConnection;
    }

    private static void createDBIfNotExists() {
        try {
            if (!(createMySQLDB(CREATE_DB_DEPARTMENT_IF_NOT_EXISTS, DEPARTMENT_TABLE_NAME)) ||
                    (!createMySQLDB(CREATE_DB_EMPLOYEE_IF_NOT_EXISTS, EMPLOYEE_TABLE_NAME))) {
                System.out.println("MySQL error! Tables not created!");
                System.exit(DB_CREATE_ERROR_EXIT_CODE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean createMySQLDB(String createStatement, String bdName) throws SQLException {
        boolean result = false;
        try {
            Connection dbConnection = getDBConnection();
            if (dbConnection == null) {
                System.out.println("MySQL error! Connection is not established");
            } else {
                Statement statement = dbConnection.createStatement();
                statement.execute(createStatement);
                System.out.println("Table \"" + bdName + "\" is created or exists!");
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("MySQL error! Table \"" + bdName + "\" not created!");
        }
        return result;
    }

    public static void insertIntoDB(Statement statement, String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("MySQL error! Record not inserted!");
        }
    }
}

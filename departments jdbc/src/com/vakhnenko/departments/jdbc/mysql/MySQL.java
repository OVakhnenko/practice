package com.vakhnenko.departments.jdbc.mysql;

import java.sql.*;

import static com.vakhnenko.departments.constants.Constants.*;
import static com.vakhnenko.departments.jdbc.strings.Strings.*;

public class MySQL {
    private Connection dbConnection = null;
    private Statement statement = null;

    public void createDBIfNotExists() throws SQLException {
        if (!(createMySQLDB(CREATE_DB_DEPARTMENT_IF_NOT_EXISTS, DEPARTMENT_TABLE_NAME)) ||
                (!createMySQLDB(CREATE_DB_EMPLOYEE_IF_NOT_EXISTS, EMPLOYEE_TABLE_NAME))) {
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

    private boolean exists(String query) {
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

    public boolean existsDepartment(String departmentName) {
        return exists(SELECT_NAME_FROM_DB_DEPARTMENT + WHERE_NAME_IS_EQUAL + swq(departmentName));
    }

    public boolean existsEmployee(String employeeName) {
        return exists(SELECT_NAME_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName));
    }

    public void printAllDepartments() {
        try {
            ResultSet rs = statement.executeQuery(SELECT_NAME_FROM_DB_DEPARTMENT);
            while (rs.next()) {
                String name = rs.getString(1);
                System.out.println("name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + SELECT_NAME_FROM_DB_DEPARTMENT);
        }
    }

    public void printAllEmployee(String departmentName) {
        if (existsDepartment(departmentName)) {
            printEmployeeWhereValue(SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL + swq(departmentName), NOT_USE_BR);
        } else {
            System.out.println("The department \"" + departmentName + "\" not found");
        }
    }

    public void printEmployee(String employeeName, boolean use_br) {
        if (existsEmployee(employeeName)) {
            printEmployeeWhereValue(SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName), NOT_USE_BR);
        } else {
            System.out.println("The employee \"" + employeeName + "\" not found");
        }
    }

    private void printEmployeeWhereValue(String query, boolean use_br) {
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString(1);
                System.out.print("name: " + name + " " + ((use_br) ? "\n" : ""));

                int age = rs.getInt(2);
                System.out.print("age: " + age + " " + ((use_br) ? "\n" : ""));

                String type = rs.getString(3);
                String departmentName = rs.getString(4);
                System.out.print("department: " + departmentName + " " + ((use_br) ? "\n" : ""));

                if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
                    System.out.print("type: " + type + " (MANAGER) " + ((use_br) ? "\n" : ""));
                    String methodology = rs.getString(5);
                    System.out.print("methodology: " + methodology + " " + ((use_br) ? "\n" : ""));
                } else {
                    System.out.print("type: " + type + " (DEVELOPER) " + ((use_br) ? "\n" : ""));
                    String language = rs.getString(6);
                    System.out.print("language: " + language + " " + ((use_br) ? "\n" : ""));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
    }

    public String getTypeEmployee(String employeeName) {
        String query = SELECT_TYPE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + employeeName;
        String result = "";

        if (existsEmployee(employeeName)) {
            System.out.println("Error! Employee " + employeeName + " not found!");
        } else {
            try {
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    result = rs.getString(1);
                }
            } catch (SQLException e) {
                System.out.println("MySQL query error! " + query);
            }
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

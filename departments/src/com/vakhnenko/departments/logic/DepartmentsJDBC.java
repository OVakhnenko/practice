package com.vakhnenko.departments.logic;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vakhnenko.departments.utils.PrintHelper.*;
import static com.vakhnenko.departments.utils.PrintHelper.printHelpExit;
import static com.vakhnenko.departments.utils.PrintHelper.printHelpSomething;
import static com.vakhnenko.departments.utils.Strings.*;
import static com.vakhnenko.departments.constants.Constants.*;

public class DepartmentsJDBC extends DepartmentsFile {
    private Connection dbConnection = null;
    private Statement statement = null;

    public DepartmentsJDBC() throws SQLException {
        createDBIfNotExists();
    }

    @Override
    public boolean saveToFile() {
        return false;
    }

    @Override
    public void readFromFile() {
    }

    @Override
    public void run() throws IOException {
        super.run();
    }

    @Override
    public void done() {
        closeMySQLDB();
    }

    @Override
    public void createDepartmentAndPrintAll(String name) {
        if (departmentExists(name)) {
            System.out.println("Error! Department " + name + " already exists!");
        } else {
            insertIntoDB(INSERT_INTO_DB_DEPERTMENT + swq(name) + CLOSING_STRUCTURE);
        }
        printAllDepartments();
    }

    @Override
    public void createManagerAndPrint(String employeeName, String type, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Manager " + employeeName + " already exists!");
        } else {
            insertIntoDB(INSERT_INTO_DB_MANAGER
                    + swq(employeeName) + ","
                    + swq(Integer.toString(age)) + ","
                    + swq(type) + ","
                    + swq(departmentName) + ","
                    + swq(methodology) + CLOSING_STRUCTURE);
        }
        printEmployee(employeeName, NOT_USE_BR);
    }

    @Override
    public void createDeveloperAndPrint(String employeeName, String type, int age, String departmentName, String language) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Developer " + employeeName + " already exists!");
        } else {
            insertIntoDB(INSERT_INTO_DB_DEVELOPER
                    + swq(employeeName) + ","
                    + swq(Integer.toString(age)) + ","
                    + swq(type) + ","
                    + swq(departmentName) + ","
                    + swq(language) + CLOSING_STRUCTURE);
        }
        printEmployee(employeeName, NOT_USE_BR);
    }

    @Override
    public void updateManagerAndPrint(String employeeName, int age, String departmentName, String methodology) {
        if (!employeeExists(employeeName)) {
            System.out.println("Error! Manager " + employeeName + " not found!");
        }
        if (age > 0) {
            updateEmployee(" age = " + age, swq(employeeName));
        }
        if (!departmentName.equals("")) {
            updateEmployee(" department_name = " + swq(departmentName), swq(employeeName));
        }
        if (!methodology.equals("")) {
            updateEmployee(" methodology = " + swq(methodology), swq(employeeName));
        }
        System.out.println("Manager " + employeeName + " updated");
    }

    @Override
    public void updateDeveloperAndPrint(String employeeName, int age, String departmentName, String language) {
        if (!employeeExists(employeeName)) {
            System.out.println("Error! Developer " + employeeName + " not found!");
        }
        if (age > 0) {
            updateEmployee(" age = " + age, swq(employeeName));
        }
        if (!departmentName.equals("")) {
            updateEmployee(" department_name = " + swq(departmentName), swq(employeeName));
        }
        if (!language.equals("")) {
            updateEmployee(" language = " + swq(language), swq(employeeName));
        }
        System.out.println("Developer " + employeeName + " updated");
    }

    private void updateEmployee(String updateQuery, String employeeName) {
        String query = UPDATE_EMPLOYEE_DB_EMPLOYEE + updateQuery + WHERE_NAME_IS_EQUAL + employeeName;
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("MySQL error! " + query);
        }
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

    @Override
    public boolean departmentExists(String departmentName) {
        return exists(SELECT_NAME_FROM_DB_DEPARTMENT + WHERE_NAME_IS_EQUAL + swq(departmentName));
    }

    @Override
    public boolean employeeExists(String employeeName) {
        return exists(SELECT_NAME_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName));
    }

    @Override
    public String getTypeEmployee(String employeeName) {
        String query = SELECT_TYPE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName);
        String result = "";

        if (!employeeExists(employeeName)) {
            System.out.println("Error! Employee " + employeeName + " not found!");
        } else {
            try {
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    result = rs.getString("type");
                }
            } catch (SQLException e) {
                System.out.println("MySQL query error! " + query);
            }
        }
        return result;
    }

    @Override
    public void printAllDepartments() {
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
    public void printAllEmployee(String departmentName) {
        if (departmentExists(departmentName)) {
            printEmployeeWhereValue(SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL + swq(departmentName), NOT_USE_BR);
        } else {
            System.out.println("The department \"" + departmentName + "\" not found");
        }
    }

    @Override
    public void printAll() {
        String tmpString;
        try {
            printStringSetLength("Department Name", 26);
            printStringSetLength("Employee Name", 26);
            printStringSetLength("Type", 20);
            printStringSetLength("Age", 20);
            System.out.println();

            ResultSet rs = statement.executeQuery(SELECT_ALL_FROM_DB_EMPLOYEE_WO);
            while (rs.next()) {
                tmpString = rs.getString("department_name");
                printStringSetLength(tmpString, 26);
                tmpString = rs.getString("name");
                printStringSetLength(tmpString, 26);
                tmpString = rs.getString("type");
                printStringSetLength(tmpString, 20);
                tmpString = Integer.toString(rs.getInt("age"));
                printStringSetLength(tmpString, 20);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + SELECT_ALL_FROM_DB_EMPLOYEE_WO);
        }
    }

    @Override
    public void printSearchedEmployeeAge(String departmentName, int age) {
        String tmpString;
        String query = "";

        if (age <= 0) {
            System.out.println("Error! Age " + age);
            return;
        }
        if (!departmentExists(departmentName)) {
            System.out.println("The department \"" + departmentName + "\" not found");
        } else {
            try {
                printStringSetLength("Employee Name", 26);
                printStringSetLength("Type", 20);
                printStringSetLength("Department Name", 26);
                System.out.println();

                query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_AGE_IS_EQUAL + age + " AND "
                        + DEPARTMENT_IS_EQUAL + swq(departmentName);
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    tmpString = rs.getString("name");
                    printStringSetLength(tmpString, 26);
                    tmpString = rs.getString("type");
                    printStringSetLength(tmpString, 20);
                    tmpString = rs.getString("department_name");
                    printStringSetLength(tmpString, 26);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("MySQL query error! " + query);
            }
        }
    }

    @Override
    public void openEntityWithName(String employeeName) {
        printEmployee(employeeName, USE_BR);
    }

    @Override
    public void printEmployee(String employeeName, boolean use_br) {
        if (employeeExists(employeeName)) {
            printEmployeeWhereValue(SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName), use_br);
        } else {
            System.out.println("The employee \"" + employeeName + "\" not found");
        }
    }

    private void printEmployeeWhereValue(String query, boolean use_br) {
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.print("name: " + name + " " + ((use_br) ? "\n" : ""));

                int age = rs.getInt("age");
                System.out.print("age: " + age + " " + ((use_br) ? "\n" : ""));

                String type = rs.getString("type");
                String departmentName = rs.getString("department_name");
                System.out.print("department: " + departmentName + " " + ((use_br) ? "\n" : ""));

                if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
                    System.out.print("type: " + type + " (MANAGER) " + ((use_br) ? "\n" : ""));
                    String methodology = rs.getString("methodology");
                    System.out.print("methodology: " + methodology + " " + ((use_br) ? "\n" : ""));
                } else {
                    System.out.print("type: " + type + " (DEVELOPER) " + ((use_br) ? "\n" : ""));
                    String language = rs.getString("language");
                    System.out.print("language: " + language + " " + ((use_br) ? "\n" : ""));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
    }

    @Override
    public void printTopEmployee(String type) {
        List<String> departments = new ArrayList<>();
        String department = "";
        int max = 0;
        int tmp;

        try {
            ResultSet rs = statement.executeQuery(SELECT_NAME_FROM_DB_DEPARTMENT);
            while (rs.next()) {
                String name = rs.getString("name");
                departments.add(name);
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + SELECT_NAME_FROM_DB_DEPARTMENT);
        }
        for (Object dep : departments) {
            tmp = getMaxEmployees("" + dep, type);
            if (tmp > max) {
                max = tmp;
                department = "" + dep;
            }
        }
        if (max > 0) {
            System.out.println("Department " + department + " has " + max + ((type.equals("D")) ? " developers" : " managers"));
        } else {
            System.out.println("Department's is not have any " + ((type.equals("D")) ? " developers" : " managers"));
        }
    }

    private int getMaxEmployees(String departmentName, String type) {
        int result = 0;

        String query = SELECT_COUNT_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL
                + swq(departmentName) + " AND " + TYPE_IS_EQUAL + swq(type);
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                result = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    public void printHelp() {
        printHelpCommandsList();
        printHelpDepartment();
        printHelpEmployee();
        printHelpSomething();
        printHelpExit();
    }

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

    private void insertIntoDB(String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("MySQL error! Record not inserted!");
        }
    }

    private void closeMySQLDB() {
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


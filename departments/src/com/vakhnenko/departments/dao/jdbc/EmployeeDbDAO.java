package com.vakhnenko.departments.dao.jdbc;

import com.vakhnenko.departments.dao.file.*;
import com.vakhnenko.departments.entity.employee.*;

import java.sql.*;
import java.util.*;

import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.*;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.*;

/**
 * Created for practice on 10.02.2017 10:21
 */
public class EmployeeDbDAO<T extends Employee> extends EmployeeFileDAO<T> {
    private Connection dbConnection;
    private Statement statement;

    public EmployeeDbDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        this.statement = dbConnection.createStatement();
    }

    @Override
    public void add(Employee employee) {
        String type = employee.getType();

        if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
            insertIntoDB(statement, INSERT_INTO_DB_MANAGER
                    + swq(employee.getName()) + ","
                    + swq(Integer.toString(employee.getAge())) + ","
                    + swq(type) + ","
                    + swq(employee.getDepartment()) + ","
                    + swq(((Manager) employee).getMethodology()) + CLOSING_STRUCTURE);
        } else {
            insertIntoDB(statement, INSERT_INTO_DB_DEVELOPER
                    + swq(employee.getName()) + ","
                    + swq(Integer.toString(employee.getAge())) + ","
                    + swq(type) + ","
                    + swq(employee.getDepartment()) + ","
                    + swq(((Developer) employee).getLanguage()) + CLOSING_STRUCTURE);
        }
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
        if (age > 0) {
            updateEmployee(" age = " + age, swq(employeeName));
        }
        if (!departmentName.equals("")) {
            updateEmployee(" department_name = " + swq(departmentName), swq(employeeName));
        }
        if (!methodology.equals("")) {
            updateEmployee(" methodology = " + swq(methodology), swq(employeeName));
        }
        if (!language.equals("")) {
            updateEmployee(" language = " + swq(language), swq(employeeName));
        }
    }

    private void updateEmployee(String updateQuery, String employeeName) {
        String query = UPDATE_EMPLOYEE_DB_EMPLOYEE + updateQuery + WHERE_NAME_IS_EQUAL + employeeName;
        try {
            //employeeDAO.save(new Employee(id, name, age ....))
            //
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("MySQL error! " + query);
        }
    }

    @Override
    public void delete(String name) {
        String query = DELETE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
    }

    @Override
    public String getType(String employeeName) {
        String query = SELECT_TYPE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName);
        String result = "";

        if (!exists(employeeName)) {
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
    public boolean exists(String name) {
        String query = SELECT_NAME_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);
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

    public void print(String employeeName, boolean use_br) {
        printEmployeeWhereValue(SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName), use_br);
    }

    public void printAll(String departmentName) {
        printEmployeeWhereValue(SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL + swq(departmentName), NOT_USE_BR);
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

    public void printTop(String type) {
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

    public void printAll(String departmentName, int age) {
        String tmpString;
        String query = "";

        if (age <= 0) {
            System.out.println("Error! Age " + age);
            return;
        }
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

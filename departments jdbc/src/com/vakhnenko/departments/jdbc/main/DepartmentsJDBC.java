package com.vakhnenko.departments.jdbc.main;

import java.io.*;
import java.sql.*;
import com.vakhnenko.departments.main.*;
import com.vakhnenko.departments.jdbc.mysql.*;

import static com.vakhnenko.departments.jdbc.strings.Strings.*;
import static com.vakhnenko.departments.constants.Constants.*;

public class DepartmentsJDBC extends Departments {
    private MySQL mysql = new MySQL();

    DepartmentsJDBC() throws SQLException {
        mysql.createDBIfNotExists();
    }

    @Override
    public boolean saveToFile() {
        return false;
    }

    @Override
    public void readFromFile() {
    }

    private void done() {
        mysql.closeMySQLDB();
    }

    @Override
    public void createDepartmentAndPrintAll(String name) {
        if (departmentExists(name)) {
            System.out.println("Error! Department " + name + " already exists!");
        } else {
            mysql.insertIntoDB(INSERT_INTO_DB_DEPERTMENT + swq(name) + CLOSING_STRUCTURE);
        }
        printAllDepartments();
    }

    @Override
    public void createManagerAndPrint(String employeeName, String type, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Manager " + employeeName + " already exists!");
        } else {
            mysql.insertIntoDB(INSERT_INTO_DB_MANAGER
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
            mysql.insertIntoDB(INSERT_INTO_DB_DEVELOPER
                    + swq(employeeName) + ","
                    + swq(Integer.toString(age)) + ","
                    + swq(type) + ","
                    + swq(departmentName) + ","
                    + swq(language) + CLOSING_STRUCTURE);
        }
        printEmployee(employeeName, NOT_USE_BR);
    }

    //@Override
    //public void updateManagerAndPrint(String employeeName, int age, String departmentName, String methodology) {}

    //@Override
    //public void updateDeveloperAndPrint(String employeeName, int age, String departmentName, String language) {}*/

    @Override
    public boolean departmentExists(String departmentName) {
        return mysql.existsDepartment(departmentName);
    }

    @Override
    public boolean employeeExists(String employeeName) {
        return mysql.existsEmployee(employeeName);
    }

    @Override
    public String getTypeEmployee(String employeeName) {
        return mysql.getTypeEmployee(employeeName);
    }

    @Override
    public void printAllDepartments() {
        mysql.printAllDepartments();
    }

    @Override
    public void printAllEmployee(String department) {
        mysql.printAllEmployee(department);
    }

    @Override
    public void printEmployee(String employeeName, boolean use_br) {
        mysql.printEmployee(employeeName, use_br);
    }

    @Override
    public void printHelpReadSave() {
    }

    @Override
    public void printHelpSomething() {
    }

    public static void main(String[] args) throws IOException {
        DepartmentsJDBC departments = null;
        try {
            departments = new DepartmentsJDBC();
        } catch (SQLException e) {
            //
        }
        try {
            departments.run();
        } catch (Exception e) {
            System.out.print("Error! ");
            e.printStackTrace();
        } finally {
            departments.done();
        }
    }
}


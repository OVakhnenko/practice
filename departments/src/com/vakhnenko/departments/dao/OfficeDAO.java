package com.vakhnenko.departments.dao;

import java.io.IOException;
import java.util.List;

/**
 * Created for practice on 09.02.2017 21:04
 */
public abstract class OfficeDAO {
    abstract public void createDepartmentAndPrintAll(String name);

    abstract public void createManagerAndPrint(String employeeName, String type, int age, String departmentName, String methodology);

    abstract public void createDeveloperAndPrint(String employeeName, String type, int age, String departmentName, String language);

    abstract public void updateManagerAndPrint(String employeeName, int age, String departmentName, String methodology);

    abstract public void updateDeveloperAndPrint(String employeeName, int age, String departmentName, String language);

    abstract public void remoteDepartmentAndPrint(String name);

    abstract public void remoteEmployeeAndPrint(String name);

    abstract public void openEntityWithName(String employeeName);

    abstract public boolean departmentExists(String departmentName);

    abstract public boolean employeeExists(String employeeName);

    abstract public String getTypeEmployee(String employeeName);

    abstract public boolean saveToFile() throws IOException;

    abstract public List<String> readFromFile() throws IOException;

    abstract public void printAllDepartments();

    abstract public void printAllEmployee(String department);

    abstract public void printEmployee(String employeeName, boolean use_br);

    abstract public void printAll();

    abstract public void printSearchedEmployeeAge(String departmentName, int age);

    abstract public void printTopEmployee(String type);

    abstract public void done();
}
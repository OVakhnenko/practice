package com.vakhnenko.departments.daojdbc;

import com.vakhnenko.departments.daofile.*;
import com.vakhnenko.departments.employee.Employee;
import com.vakhnenko.departments.utils.*;

import java.sql.*;
import java.util.*;

import static com.vakhnenko.departments.constants.Constants.*;
import static com.vakhnenko.departments.utils.PrintHelper.*;

/**
 * Created for practice on 09.02.2017 22:33
 */
public class OfficeDbDAO extends OfficeFileDAO {
    private DepartmentDbDAO departmentDAO;
    private EmployeeDbDAO<Employee> employeeDAO;

    public OfficeDbDAO() {
        try {
            setDepartmentDAO(departmentDAO = new DepartmentDbDAO(ConnectionUtilJDBC.getDBConnection()));
            setEmployeeDAO(employeeDAO = new EmployeeDbDAO<Employee>(ConnectionUtilJDBC.getDBConnection()));
        } catch (SQLException e) {
            System.out.println("Set DAO error! " + e.getMessage());
        }
    }

    @Override
    public boolean saveToFile() {
        System.out.println("Error! Unknown command - \" type \"help\" for commands list");
        return false;
    }

    @Override
    public List<String> readFromFile() {
        System.out.println("Error! Unknown command - \" type \"help\" for commands list");
        return null;
    }

    @Override
    public String getTypeEmployee(String employeeName) {
        if (!employeeExists(employeeName)) {
            System.out.println("The employee \"" + employeeName + "\" not found");
            return "";
        }
        return employeeDAO.getType(employeeName);
    }

    @Override
    public void printAllEmployee(String departmentName) {
        if (departmentExists(departmentName)) {
            employeeDAO.printAll(departmentName);
        } else {
            System.out.println("The department \"" + departmentName + "\" not found");
        }
    }

    @Override
    public void printAll() {
        employeeDAO.printAll();
    }

    @Override
    public void printSearchedEmployeeAge(String departmentName, int age) {
        if (departmentExists(departmentName)) {
            employeeDAO.printAll(departmentName, age);
        } else {
            System.out.println("The department \"" + departmentName + "\" not found");
        }
    }

    @Override
    public void printEmployee(String employeeName, boolean use_br) {
        if (employeeExists(employeeName)) {
            employeeDAO.print(employeeName, use_br);
        } else {
            System.out.println("The employee \"" + employeeName + "\" not found");
        }
    }

    @Override
    public void printTopEmployee(String type) {
        employeeDAO.printTop(type);
    }

    public void printHelp() {
        printHelpCommandsList();
        printHelpDepartment();
        printHelpEmployee();
        printHelpSomething();
        printHelpExit();
    }

    @Override
    public void done() {
        departmentDAO.done();
        employeeDAO.done();
    }
}

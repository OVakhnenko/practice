package com.vakhnenko.departments.service;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.dao.db.DepartmentDbDAO;
import com.vakhnenko.departments.dao.db.EmployeeDbDAO;
import com.vakhnenko.departments.dao.file.*;
import com.vakhnenko.departments.entity.employee.Employee;
import com.vakhnenko.departments.utils.*;

import java.sql.*;
import java.util.*;

import static com.vakhnenko.departments.utils.PrintHelper.*;

/**
 * Created for practice on 09.02.2017 22:33
 */
public class DepartmentService {
    private DepartmentDAO departmentDAO = new DepartmentDbDAO(ConnectionUtilJDBC.getDBConnection());
    private EmployeeDAO employeeDAO = new EmployeeDbDAO(ConnectionUtilJDBC.getDBConnection());

    public String getTypeEmployee(String employeeName) {
        if (employeeDAO.exists(employeeName)) {
            System.out.println("The employee \"" + employeeName + "\" not found");
            return "";
        }
        return employeeDAO.getType(employeeName);
    }

    public void printAllEmployee(String departmentName) {
        if (departmentDAO.exists(departmentName)) {
            employeeDAO.printAll(departmentName);
        } else {
            System.out.println("The department \"" + departmentName + "\" not found");
        }
    }

    public void printAll() {
        employeeDAO.printAll();
    }

    public void printSearchedEmployeeAge(String departmentName, int age) {
        if (departmentExists(departmentName)) {
            employeeDAO.printAll(departmentName, age);
        } else {
            System.out.println("The department \"" + departmentName + "\" not found");
        }
    }

    public void printEmployee(String employeeName, boolean use_br) {
        if (employeeExists(employeeName)) {
            employeeDAO.print(employeeName, use_br);
        } else {
            System.out.println("The employee \"" + employeeName + "\" not found");
        }
    }

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

    public void done() {
        departmentDAO.done();
        employeeDAO.done();
    }
}

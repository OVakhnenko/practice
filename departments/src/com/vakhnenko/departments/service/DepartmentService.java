package com.vakhnenko.departments.service;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.dao.file.*;
import com.vakhnenko.departments.dao.db.*;
import com.vakhnenko.departments.entity.department.*;
import com.vakhnenko.departments.entity.employee.*;
import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.utils.*;

import java.io.*;
import java.util.*;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 09.02.2017 21:07
 */
public class DepartmentService {
    private DepartmentDAO departmentDAO = new DepartmentFileDAO(ConnectionUtilFile.getFileConnectionWriter()); //DepartmentDbDAO(ConnectionUtilJDBC.getDBConnection());
    private EmployeeDAO<Employee> employeeDAO = new EmployeeFileDAO(ConnectionUtilFile.getFileConnectionWriter()); //EmployeeDbDAO(ConnectionUtilJDBC.getDBConnection());

    public void saveToFile() {
        if (departmentDAO.save()) {
            if (employeeDAO.save()) {
                System.out.println("All data saved successfully");
            }
        }
    }

    public List<String> readFromFile() throws IOException {
        return departmentDAO.read();
    }

    public void printAllEmployee(String department) {
        for (Entity employee : employeeDAO.getAll()) {
            if (((Employee) employee).getDepartment().equals(department))
                printEmployee(employee.getName(), NOT_USE_BR);
        }
    }

    @Override
    public void printEmployee(String employeeName, boolean use_br) {
        Entity entity = employeeDAO.search(employeeName);

        if (entity != null) {
            System.out.print("Name " + entity.getName() + " " + ((use_br) ? "\n" : ""));
            System.out.print("ID " + entity.getID() + " " + ((use_br) ? "\n" : ""));

            System.out.print("Age " + ((Employee) entity).getAge() + " " + ((use_br) ? "\n" : ""));
            System.out.print("Dep " + ((Employee) entity).getDepartment() + " " + ((use_br) ? "\n" : ""));

            if (entity.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Manager")) {
                System.out.print("Type (" + ((Employee) entity).getType() + ") - MANAGER " + ((use_br) ? "\n" : ""));
                System.out.print("Meth " + ((Manager) entity).getMethodology() + " " + ((use_br) ? "\n" : ""));
            } else if (entity.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Developer")) {
                System.out.print("Type (" + ((Employee) entity).getType() + ") - DEVELOPER " + ((use_br) ? "\n" : ""));
                System.out.print("Lang " + ((Developer) entity).getLanguage() + " " + ((use_br) ? "\n" : ""));
            }
            System.out.println();
        } else {
            System.out.println("The employee \"" + employeeName + "\" not found");
        }
    }

    @Override
    public void printAll() {
        System.out.println("Error! Unknown command - type \"help\" for commands list");
    }

    @Override
    public void printSearchedEmployeeAge(String departmentName, int age) {
        System.out.println("Error! Unknown command - type \"help\" for commands list");
    }

    @Override
    public void printTopEmployee(String type) {
        System.out.println("Error! Unknown command - type \"help\" for commands list");
    }

    public void done() {
        departmentDAO.done();
        employeeDAO.done();
        System.out.println("Bye!");
    }
}

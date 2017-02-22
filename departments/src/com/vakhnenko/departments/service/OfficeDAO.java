package com.vakhnenko.departments.service;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.entity.employee.*;

import java.io.IOException;
import java.util.List;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 09.02.2017 21:04
 */
public abstract class OfficeDAO<TD extends DepartmentDAO, TE extends EmployeeDAO> {
    private TD departmentDAO;
    private TE employeeDAO;

    public void setDepartmentDAO(TD departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public TD getDepartmentDAO() {
        return departmentDAO;
    }

    public void setEmployeeDAO(TE employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public TE getEmployeeDAO() {
        return employeeDAO;
    }

    public void createDepartment(String name) {
        if (departmentDAO.exists(name)) {
            System.out.println("Error! Department " + name + " already exists!");
        } else {
            departmentDAO.create(name);
        }
    }

    public void createManager(String employeeName, String type, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Manager " + employeeName + " already exists!");
        } else {
            employeeDAO.add(new Manager(employeeName, type, age, departmentName, methodology));
        }
    }

    public void createDeveloper(String employeeName, String type, int age, String departmentName, String language) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Developer " + employeeName + " already exists!");
        } else {
            employeeDAO.add(new Developer(employeeName, type, age, departmentName, language));
        }
    }

    public void updateManager(String employeeName, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            employeeDAO.update(employeeName, age, departmentName, methodology, "");
        } else {
            System.out.println("Error! Employee " + employeeName + " not found!");
        }
    }

    public void updateDeveloper(String employeeName, int age, String departmentName, String language) {
        if (employeeExists(employeeName)) {
            employeeDAO.update(employeeName, age, departmentName, "", language);
        } else {
            System.out.println("Error! Employee " + employeeName + " not found!");
        }
    }

    public void removeDepartment(String name) {
        if (departmentExists(name)) {
            departmentDAO.delete(name);
        } else {
            System.out.println("Error! Department " + name + " not found!");
        }
    }

    public void removeEmployee(String name) {
        if (employeeExists(name)) {
            employeeDAO.delete(name);
        } else {
            System.out.println("Error! Employee " + name + " not found!");
        }
    }

    public void openEntityWithName(String employeeName) {
        if (employeeExists(employeeName)) {
            printEmployee(employeeName, USE_BR);
        } else {
            System.out.println(employeeDAO.getEntityStatus() + " \"" + employeeName + "\" not found!");
        }
    }

    public boolean departmentExists(String departmentName) {
        return departmentDAO.exists(departmentName);
    }

    public boolean employeeExists(String employeeName) {
        return employeeDAO.exists(employeeName);
    }

    abstract public String getTypeEmployee(String employeeName);

    abstract public boolean saveToFile() throws IOException;

    abstract public List<String> readFromFile() throws IOException;

    public void printAllDepartments() {
        departmentDAO.printAll();
    }

    abstract public void printAllEmployee(String department);

    abstract public void printEmployee(String employeeName, boolean use_br);

    abstract public void printAll();

    abstract public void printSearchedEmployeeAge(String departmentName, int age);

    abstract public void printTopEmployee(String type);

    abstract public void done();
}
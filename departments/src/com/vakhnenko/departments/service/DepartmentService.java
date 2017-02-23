package com.vakhnenko.departments.service;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.dao.file.*;
import com.vakhnenko.departments.dao.db.*;
import com.vakhnenko.departments.entity.department.*;
import com.vakhnenko.departments.entity.employee.*;
import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.utils.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 09.02.2017 21:07
 */
public class DepartmentService {
    //private DepartmentDAO departmentDAO = new DepartmentFileDAO(ConnectionUtilFile.getFileConnectionWriter());
    //private EmployeeDAO<Employee> employeeDAO = new EmployeeFileDAO(ConnectionUtilFile.getFileConnectionWriter());
    private DepartmentDAO departmentDAO = new DepartmentDbDAO(ConnectionUtilJDBC.getDBConnection());
    private EmployeeDAO<Employee> employeeDAO = new EmployeeDbDAO(ConnectionUtilJDBC.getDBConnection());

    public DepartmentService() throws SQLException {
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

    public boolean departmentExists(String departmentName) {
        return departmentDAO.exists(departmentName);
    }

    public boolean employeeExists(String employeeName) {
        return employeeDAO.exists(employeeName);
    }

    public String getTypeEmployee(String employeeName) {
        String result;

        if (employeeExists(employeeName)) {
            result = employeeDAO.getType(employeeName);
        } else {
            result = "";
        }

        return result;
    }

    public void saveToFile() {
        if (departmentDAO.save()) {
            if (employeeDAO.save()) {
                System.out.println("All data saved successfully");
            }
        } else {
            System.out.println("Data not been saved!");
        }
    }

    public List<String> readFromFile() throws IOException {
        return departmentDAO.read();
    }

    public void openEntityWithName(String employeeName) {
        if (employeeExists(employeeName)) {
            PrintEntity.printEmployee(employeeDAO.getByName(employeeName), USE_BR);
        } else {
            System.out.println(employeeDAO.getEntityStatus() + " \"" + employeeName + "\" not found!");
        }
    }

    public void printAllDepartments() {
        System.out.println("Departmnents:");
        PrintEntity.printAllDepartments(departmentDAO.getAll());
    }

    public void printAllEmployee(String department) {
        System.out.println("Employees of departmnent " + department + ":");
        PrintEntity.printAllEmployee(employeeDAO.getAll(department));
    }

    public void printEmployee(String employeeName, boolean use_br) {
        PrintEntity.printEmployee(employeeDAO.getByName(employeeName), use_br);
    }

    public void printAllEmployeeGrid() {
        PrintEntity.printAllEmployeeGrid(employeeDAO.getAll());
    }

    public void printSearchedEmployeeAge(String departmentName, int age) {
        PrintEntity.printAllEmployeeGrid(employeeDAO.getAll(departmentName, age));
    }

    public void printTopEmployee(String type) {
        String department = "";
        int max = 0;
        int tmp;

        List<Department> departments = departmentDAO.getAll();
        if (departments.size() == 0) {
            System.out.println("Error! No departments!");
            return;
        }

        for (Department dep : departments) {
            tmp = employeeDAO.getMaxEmployees(dep.getName(), type);
            if (tmp > max) {
                max = tmp;
                department = dep.getName();
            }
        }

        if (max > 0) {
            System.out.println("Department " + department + " has " + max + ((type.equals("D")) ? " developers" : " managers"));
        } else {
            System.out.println("Department's is not have any " + ((type.equals("D")) ? " developers" : " managers"));
        }
    }

    public void done() {
        departmentDAO.done();
        employeeDAO.done();
        System.out.println("Bye!");
    }
}

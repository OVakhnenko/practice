package com.vakhnenko.departments.daofile;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.department.*;
import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.utils.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.vakhnenko.departments.constants.Constants.*;

/**
 * Created for practice on 09.02.2017 21:07
 */
public class OfficeFileDAO extends OfficeDAO {
    private DepartmentFileDAO departmentDAO;
    private EmployeeFileDAO employeeDAO;

    public OfficeFileDAO() {
        departmentDAO = new DepartmentFileDAO();
        employeeDAO = new EmployeeFileDAO();
    }

    @Override
    public boolean saveToFile() throws IOException {
        FileWriter writer = ConnectionUtilFile.getFileConnectionWriter();
        boolean saved = false;

        if (departmentDAO.getSize() == 0) {
            System.out.println("Error! No departments");
        } else {
            for (Entity department : departmentDAO.getAll()) {
                saved = departmentDAO.save((Department) department, writer);
                if (!saved)
                    return false;
            }
            for (Entity employee : employeeDAO.getAll()) {
                if (((Employee) employee).getType().equals(EMPLOYEE_MANAGER_TYPE)) {
                    saved = employeeDAO.save((Manager) employee, writer);
                } else {
                    saved = employeeDAO.save((Developer) employee, writer);
                }
                employeeDAO.writeln(writer);
                if (!saved)
                    return false;
            }
            writer.close();
            if (saved) {
                System.out.println("All data saved successfully");
            }
        }
        return true;
    }

    @Override
    public List<String> readFromFile() throws IOException {
        BufferedReader reader = ConnectionUtilFile.getFileConnectionReader();
        List<String> lines = new ArrayList<String>();
        String line;

        if (departmentDAO.getSize() != 0) {
            System.out.println("Error! Departments are exists");
        } else {
            {
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    @Override
    public void createDepartmentAndPrintAll(String name) {
        departmentDAO.create(name);
        printAllDepartments();
    }

    @Override
    public void createManagerAndPrint(String employeeName, String type, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Manager " + employeeName + " already exists!");
        } else {
            employeeDAO.add(new Manager(employeeName, type, age, departmentName, methodology));
        }
        printEmployee(employeeName, NOT_USE_BR);
    }

    @Override
    public void createDeveloperAndPrint(String employeeName, String type, int age, String departmentName, String language) {
        if (employeeExists(employeeName)) {
            System.out.println("Error! Developer " + employeeName + " already exists!");
        } else {
            employeeDAO.add(new Developer(employeeName, type, age, departmentName, language));
        }
        printEmployee(employeeName, NOT_USE_BR);
    }

    @Override
    public void updateManagerAndPrint(String employeeName, int age, String departmentName, String methodology) {
        Entity entity = employeeDAO.search(employeeName);

        ((Manager) entity).setAge(age);
        ((Manager) entity).setMethodology(methodology);
        ((Manager) entity).setDepartment(departmentName);
        printEmployee(employeeName, NOT_USE_BR);
    }

    @Override
    public void updateDeveloperAndPrint(String employeeName, int age, String departmentName, String language) {
        Entity entity = employeeDAO.search(employeeName);

        ((Developer) entity).setAge(age);
        ((Developer) entity).setLanguage(language);
        ((Developer) entity).setDepartment(departmentName);
        printEmployee(employeeName, NOT_USE_BR);
    }

    @Override
    public void remoteDepartmentAndPrint(String name) {
        departmentDAO.delete(name);
    }

    @Override
    public void remoteEmployeeAndPrint(String name) {
        employeeDAO.delete(name);
    }

    @Override
    public void openEntityWithName(String employeeName) {
        Entity tmp = employeeDAO.search(employeeName);

        if (tmp != null) {
            printEmployee(tmp.getName(), USE_BR);
        } else {
            System.out.println(employeeDAO.getEntityStatus() + " \"" + employeeName + "\" not found!");
        }
    }

    @Override
    public boolean departmentExists(String departmentName) {
        return departmentDAO.exists(departmentName);
    }

    @Override
    public boolean employeeExists(String employeeName) {
        return employeeDAO.exists(employeeName);
    }

    @Override
    public String getTypeEmployee(String employeeName) {
        Entity entity = employeeDAO.search(employeeName);
        return ((Employee) entity).getType();
    }

    @Override
    public void printAllDepartments() {
        departmentDAO.printAll();
    }

    @Override
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

            if (entity.getClass().getName().equals("com.vakhnenko.departments.Manager")) {
                System.out.print("Type (" + ((Employee) entity).getType() + ") - MANAGER " + ((use_br) ? "\n" : ""));
                System.out.print("Meth " + ((Manager) entity).getMethodology() + " " + ((use_br) ? "\n" : ""));
            } else if (entity.getClass().getName().equals("com.vakhnenko.departments.Developer")) {
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
        System.out.println("Error! Unknown command - \" type \"help\" for commands list");
    }

    @Override
    public void printSearchedEmployeeAge(String departmentName, int age) {
        System.out.println("Error! Unknown command - \" type \"help\" for commands list");
    }

    @Override
    public void printTopEmployee(String type) {
        System.out.println("Error! Unknown command - \" type \"help\" for commands list");
    }

    @Override
    public void done() {
        System.out.println("Bye!");
    }
}

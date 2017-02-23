package com.vakhnenko.departments.utils;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.department.Department;
import com.vakhnenko.departments.entity.employee.*;

import java.util.*;

import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.printStringSetLength;

/**
 * Created for practice on 22.02.2017 20:35
 */
public class PrintEntity {

    public static void printAllDepartments(List<Department> departments) {
        for (Department department : departments) {
            System.out.print(department.getName() + "\n");
        }
    }

    public static void printAllEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            printEmployee(employee, NOT_USE_BR);
        }
    }

    public static void printAllEmployeeGrid(List<Employee> employees) {
        String tmpString;

        printStringSetLength("Department Name", 26);
        printStringSetLength("Employee Name", 26);
        printStringSetLength("Type", 20);
        printStringSetLength("Age", 20);
        System.out.println();

        for (Employee employee : employees) {
            tmpString = employee.getDepartment();
            printStringSetLength(tmpString, 26);
            tmpString = employee.getName();
            printStringSetLength(tmpString, 26);
            tmpString = employee.getType();
            printStringSetLength(tmpString, 20);
            tmpString = Integer.toString(employee.getAge());
            printStringSetLength(tmpString, 20);
            System.out.println();
        }
    }

    public static void printEmployee(Employee employee, boolean use_br) {
        System.out.print("Name " + employee.getName() + " " + ((use_br) ? "\n" : ""));
        System.out.print("ID " + employee.getID() + " " + ((use_br) ? "\n" : ""));

        System.out.print("Age " + employee.getAge() + " " + ((use_br) ? "\n" : ""));
        System.out.print("Dep " + employee.getDepartment() + " " + ((use_br) ? "\n" : ""));

        if (employee.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Manager")) {
            System.out.print("Type (" + employee.getType() + ") - MANAGER " + ((use_br) ? "\n" : ""));
            System.out.print("Meth " + ((Manager) employee).getMethodology() + " " + ((use_br) ? "\n" : ""));
        } else if (employee.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Developer")) {
            System.out.print("Type (" + employee.getType() + ") - DEVELOPER " + ((use_br) ? "\n" : ""));
            System.out.print("Lang " + ((Developer) employee).getLanguage() + " " + ((use_br) ? "\n" : ""));
        }
        System.out.println();
    }
}

/*
    public void printTop(String type) {
        String department = "";
        int max = 0;
        int tmp;
        List<String> departments = new ArrayList<>();

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
 */
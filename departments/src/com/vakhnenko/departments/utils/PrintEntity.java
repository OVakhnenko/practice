package com.vakhnenko.departments.utils;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.department.Department;
import com.vakhnenko.departments.entity.employee.*;

import java.util.*;

import static com.vakhnenko.departments.utils.Constants.*;

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

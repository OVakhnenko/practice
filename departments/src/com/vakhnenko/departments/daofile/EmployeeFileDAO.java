package com.vakhnenko.departments.daofile;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.entity.*;

import java.io.*;

import static com.vakhnenko.departments.constants.Constants.*;

/**
 * Created for practice on 10.02.2017 9:36
 */
public class EmployeeFileDAO<T extends Employee> extends EmployeeDAO<T> {
    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
        Employee employee = search(employeeName);

        if (employee != null) {
            if (age > 0) employee.setAge(age);
            if (!departmentName.equals("")) employee.setDepartment(departmentName);
            if (!methodology.equals("")) ((Manager) employee).setMethodology(methodology);
            if (!language.equals("")) ((Developer) employee).setLanguage(language);
        } else {
            System.out.println("Error! Employee " + employeeName + " not founf!");
        }
    }

    public boolean save(Employee employee, FileWriter writer) throws IOException {
        String type;

        try {
            writer.write(CREATE_COMMAND + " " + EMPLOYEE_KEY + " " + NAME_EMPLOYEE_KEY + " " + employee.getName() + " ");
            writer.write(TYPE_EMPLOYEE_KEY + " " + (type = employee.getType()) + " ");
            writer.write(AGE_EMPLOYEE_KEY + " " + employee.getAge() + " ");
            writer.write(DEPARTMENT_EMPLOYEE_KEY + " " + employee.getDepartment() + " ");

            if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
                writer.write(METHODOLOGY_EMPLOYEE_KEY + " " + ((Manager) employee).getMethodology() + " ");
            } else {
                writer.write(LANGUAGE_EMPLOYEE_KEY + " " + ((Developer) employee).getLanguage() + " ");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Write error!");
            return false;
        }
    }

    public void writeln(FileWriter writer) throws IOException {
        try {
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("WriteLn error!");
        }
    }
}

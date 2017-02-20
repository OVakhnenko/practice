package com.vakhnenko.departments.dao.file;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.entity.employee.*;

import java.io.*;
import java.util.List;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 10.02.2017 9:36
 */
public class EmployeeFileDAO implements EmployeeDAO {
    private FileWriter writer;

    public EmployeeFileDAO(FileWriter writer) {
        this.writer = writer;
    }

    @Override
    public Employee getById(int id) {
        return null;
    }

    @Override
    public void remove(int id) {
        //todo:remove
    }

    @Override
    public void save(Employee employee) {
//        writer.write();  todo: write it
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
        } catch (IOException e) {
            System.out.println("Write error!");
        }
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public List<Employee> find(String departmentName, int age) {
        return null;
    }
}

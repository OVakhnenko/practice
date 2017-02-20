package com.vakhnenko.departments.dao.file;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.entity.department.Department;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.vakhnenko.departments.utils.Constants.CREATE_COMMAND;
import static com.vakhnenko.departments.utils.Constants.DEPARTMENT_KEY;

/**
 * Created for practice on 10.02.2017 9:19
 */
public class DepartmentFileDAO implements DepartmentDAO {

    private FileWriter writer;

    public DepartmentFileDAO(FileWriter fileWriter) {
        this.writer = fileWriter;
    }

    @Override
    public Department getById(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void save(Department entity) {
        try {
            writer.write(CREATE_COMMAND + " " + DEPARTMENT_KEY + " " + entity.getName() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Write error!");
        }
    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public List<Department> findTop(String employeeType) {
        return null;
    }
}

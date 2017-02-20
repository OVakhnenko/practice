package com.vakhnenko.departments.dao.file;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.entity.department.Department;

import java.io.*;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 10.02.2017 9:19
 */
public class DepartmentFileDAO extends DepartmentDAO {

    public boolean save(Department department, FileWriter writer) throws IOException {
        try {
            writer.write(CREATE_COMMAND + " " + DEPARTMENT_KEY + " " + department.getName() + "\n");
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Write error!");
            return false;
        }
    }
}

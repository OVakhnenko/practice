package com.vakhnenko.departments.daofile;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.department.*;

import java.io.*;

import static com.vakhnenko.departments.constants.Constants.*;

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

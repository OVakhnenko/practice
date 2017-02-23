package com.vakhnenko.departments.dao.file;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.department.*;
import com.vakhnenko.departments.utils.ConnectionUtilFile;

import java.io.*;
import java.util.*;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 10.02.2017 9:19
 */
public class DepartmentFileDAO extends DepartmentDAO {
    private FileWriter writer;

    public DepartmentFileDAO(FileWriter fileWriter) {
        this.writer = fileWriter;
    }

    public boolean save() {
        boolean result = false;
        List<Department> departments = getAll();

        if (departments.size() == 0) {
            System.out.println("Error! No departments");
        } else {
            for (Department department : departments) {
                try {
                    writer.write(CREATE_COMMAND + " " + DEPARTMENT_KEY + " " + department.getName() + "\n");
                    writer.flush();
                    result = true;
                } catch (IOException e) {
                    System.out.println("Write error!");
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public List<String> read() {
        String line;
        BufferedReader reader = ConnectionUtilFile.getFileConnectionReader();
        List<Department> departments = getAll();
        List<String> lines = new ArrayList<>();

        if (departments.size() == 0) {
            System.out.println("Error! Departments are exists");
        } else {
            {
                try {
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (IOException e) {
                    System.out.println("Read error! " + e.getMessage());
                }
            }
        }
        return lines;
    }

    public void done() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Close file error!");
        }
    }
}

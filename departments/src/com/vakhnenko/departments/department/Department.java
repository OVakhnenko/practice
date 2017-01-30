package com.vakhnenko.departments.department;

import java.io.FileWriter;
import java.io.IOException;

import static com.vakhnenko.departments.constants.Constants.*;

import com.vakhnenko.departments.entity.*;

public class Department extends Entity {
    Department(String name) {
        super(name);
    }

    public boolean save(FileWriter writer) throws IOException {
        try {
            writer.write(CREATE_COMMAND + " " + DEPARTMENT_KEY + " " + getName() + "\n");
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Write error!");
            return false;
        }
    }
}

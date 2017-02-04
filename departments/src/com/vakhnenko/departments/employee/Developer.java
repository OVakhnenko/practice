package com.vakhnenko.departments.employee;

import java.io.FileWriter;
import java.io.IOException;

import static com.vakhnenko.departments.constants.Constants.*;

public class Developer extends Employee {
    private String language;
    private static int developerID;

    public Developer(String name, String type, int age, String department, String language) {
        super(name, type, age, department);
        this.language = language;
    }

    @Override
    public int getUniqeID() {
        return developerID++;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public boolean save(FileWriter writer) throws IOException {
        super.save(writer);
        try {
            writer.write(LANGUAGE_EMPLOYEE_KEY + " " + getLanguage() + " ");
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Write error!");
            return false;
        }
    }
}

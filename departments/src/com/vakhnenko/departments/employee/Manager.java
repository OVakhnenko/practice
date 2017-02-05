package com.vakhnenko.departments.employee;

import java.io.FileWriter;
import java.io.IOException;

import static com.vakhnenko.departments.constants.Constants.*;

public class Manager extends Employee {
    private String methodology;

    public Manager(String name, String type, int age, String department, String methodology) {
        super(name, type, age, department);
        this.methodology = methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    public String getMethodology() {
        return methodology;
    }

    @Override
    public boolean save(FileWriter writer) throws IOException {
        super.save(writer);
        try {
            writer.write(METHODOLOGY_EMPLOYEE_KEY + " " + getMethodology() + " ");
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Write error!");
            return false;
        }
    }
}

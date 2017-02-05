package com.vakhnenko.departments.main;

import com.vakhnenko.departments.logic.DepartmentsFile;
import com.vakhnenko.departments.logic.DepartmentsJDBC;

public class Application {
    public static void main(String[] args) throws Exception {
        DepartmentsFile departments = new DepartmentsJDBC();
        try {
            departments.run();
        } catch (Exception e) {
            System.out.println("Application error! " + e.getMessage());
        } finally {
            departments.done();
        }
    }
}

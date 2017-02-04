package com.vakhnenko.departments.main;

import com.vakhnenko.departments.logic.Departments;

public class Application {
    public static void main(String[] args) throws Exception {
        Departments departments = new Departments();
        try {
            departments.run();
        } catch (Exception e) {
            System.out.println("Application error!");
        } finally {
            departments.done();
        }
    }
}

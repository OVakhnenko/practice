package com.vakhnenko.departments;

public class Application {
    public static void main(String[] args) throws Exception {
        DepartmentsApplication departments = new DepartmentsApplication();

        try {
            departments.run();
        } catch (Exception e) {
            System.out.println("Application error! " + e.getMessage());
        } finally {
            departments.done();
        }
    }
}

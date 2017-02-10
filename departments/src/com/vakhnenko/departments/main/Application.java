package com.vakhnenko.departments.main;

import com.vakhnenko.departments.daofile.*;
import com.vakhnenko.departments.daojdbc.*;
import com.vakhnenko.departments.logic.*;

public class Application {
    public static void main(String[] args) throws Exception {
        DepartmentsApplication departments = new DepartmentsApplication(new OfficeDbDAO());
        try {
            departments.run();
        } catch (Exception e) {
            System.out.println("Application error! " + e.getMessage());
        } finally {
            departments.done();
        }
    }
}

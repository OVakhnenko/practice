package com.vakhnenko.departments;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.dao.jdbc.*;

public class Application {
    public static void main(String[] args) throws Exception {
        DepartmentsApplication departments = new DepartmentsApplication<OfficeDAO>(new OfficeDbDAO());

        try {
            departments.run();
        } catch (Exception e) {
            System.out.println("Application error! " + e.getMessage());
        } finally {
            departments.done();
        }
    }
}

package com.vakhnenko.departments.jdbc.main;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.vakhnenko.departments.constants.Constants.*;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.department.*;
import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.main.*;

public class DepartmentsJDBC extends Departments {

    DepartmentsJDBC() {
    }

    public static void main(String[] args) throws IOException {
        DepartmentsJDBC departments = new DepartmentsJDBC();
        departments.run();
    }
}


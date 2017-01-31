package com.vakhnenko.departments.jdbc.main;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.sql.SQLException;

import static com.vakhnenko.departments.constants.Constants.*;
import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.department.*;
import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.main.*;

import com.vakhnenko.departments.jdbc.mysql.*;

import static com.vakhnenko.departments.jdbc.strings.Strings.*;

public class DepartmentsJDBC extends Departments {
    private MySQL mysql = new MySQL();

    DepartmentsJDBC() throws SQLException {
        mysql.createDBIfNotExists();
    }

    private void done() {
        mysql.closeMySQLDB();
    }

    @Override
    public void createDepartment(String[] commands) {
        String name = getStringFromManyWords(commands, FIRST_KEY_POSITION);

        if (!name.equals("")) {
            System.out.println(name);
            mysql.insertIntoDB(INSERT__INTO_DB_DEPERTMENT + swq(name) + CLOSING_STRUCTURE);
        } else {
            System.out.println("Error! Name is empty");
        }
    }

    public static void main(String[] args) throws IOException {
        DepartmentsJDBC departments = null;
        try {
            departments = new DepartmentsJDBC();
        } catch (SQLException e) {
            //
        }
        departments.run();
        departments.done();
    }
}


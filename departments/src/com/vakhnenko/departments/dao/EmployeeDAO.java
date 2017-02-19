package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.entity.*;

public class EmployeeDAO<T extends Entity> extends EntityDAO<T> {

    public EmployeeDAO() {
        setEntityStatus("Employee");
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
    }

    public String getType(String employeeName) {
        return "";
    }

    public void print(String employeeName, boolean use_br) {
    }

    public void printAll(String departmentName) {
    }

    public void printAll(String departmentName, int age) {
    }

    public void printTop(String type) {
    }

    public void done() {
    }
}

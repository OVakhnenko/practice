package com.vakhnenko.departments.entity.employee;

import com.vakhnenko.departments.entity.Entity;


public class Employee extends Entity {
    private int age;
    private String type;
    private String department;
    private static int employeeID;

    public Employee(String name, String type, int age, String department) {
        super(name);
        this.age = age;
        this.type = type;
        this.department = department;
    }

    @Override
    public int getUniqeID() {
        return employeeID++;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getType() {
        return type;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}

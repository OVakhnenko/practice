package com.vakhnenko.departments.entity.employee;

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
}

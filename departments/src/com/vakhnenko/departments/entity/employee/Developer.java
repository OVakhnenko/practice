package com.vakhnenko.departments.entity.employee;

public class Developer extends Employee {
    private String language;

    public Developer(String name, String type, int age, String department, String language) {
        super(name, type, age, department);
        this.language = language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}

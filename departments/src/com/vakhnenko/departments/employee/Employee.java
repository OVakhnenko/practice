package com.vakhnenko.departments.employee;

import java.io.FileWriter;
import java.io.IOException;

import static com.vakhnenko.departments.constants.Constants.*;

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

//    public void setType(String type) {
//        this.type = type;
//    }

    public String getType() {
        return type;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    boolean save(FileWriter writer) throws IOException {
        //  create -e -n Ivan3 Ivanovich3 -t d -a 23 -l Java -dn 222 2222 22222"
        try {
            writer.write(CREATE_COMMAND + " " + EMPLOYEE_KEY + " " + NAME_EMPLOYEE_KEY + " " + getName() + " ");
            writer.write(TYPE_EMPLOYEE_KEY + " " + getType() + " ");
            writer.write(AGE_EMPLOYEE_KEY + " " + getAge() + " ");
            writer.write(DEPARTMENT_EMPLOYEE_KEY + " " + getDepartment() + " ");
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Write error!");
            return false;
        }
    }

    public void writeln(FileWriter writer) throws IOException {
        try {
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("WriteLn error!");
        }
    }
}

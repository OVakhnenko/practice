package com.vakhnenko.departments.entity.department;

import com.vakhnenko.departments.entity.*;

public class Department extends Entity {
    private static int departmentID;

    public Department(String name) {
        super(name);
    }

    @Override
    public int getUniqeID() {
        return departmentID++;
    }
}

package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.entity.*;

public class EmployeeDAO<T extends Entity> extends EntityDAO<T> {

    public EmployeeDAO() {
        setEntityStatus("Employee");
    }

    public void done() {
    }
}

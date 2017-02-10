package com.vakhnenko.departments.daojdbc;

import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.employee.Developer;
import com.vakhnenko.departments.employee.Employee;
import com.vakhnenko.departments.employee.Manager;
import com.vakhnenko.departments.entity.Entity;

import java.sql.*;

import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.*;
import static com.vakhnenko.departments.constants.Constants.*;
import static com.vakhnenko.departments.utils.Strings.swq;

/**
 * Created for practice on 10.02.2017 10:21
 */
public class EmployeeDbDAO<T extends Entity> extends EmployeeDAO<T> {
    private Connection dbConnection;
    private Statement statement;

    public EmployeeDbDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        this.statement = dbConnection.createStatement();
    }

    @Override
    public void add(T item) {
        Employee employee = (Employee) item;
        String type = employee.getType();
        String met_or_lan = (type.equals(EMPLOYEE_MANAGER_TYPE)) ?
                ((Manager) employee).getMethodology() : ((Developer) employee).getLanguage();

        insertIntoDB(statement, INSERT_INTO_DB_MANAGER
                + swq(employee.getName()) + ","
                + swq(Integer.toString(employee.getAge())) + ","
                + swq(type + ","
                + swq(employee.getDepartment()) + ","
                + swq(met_or_lan) + CLOSING_STRUCTURE));
    }

    public String getTypeEmployee(String employeeName) {
        String query = SELECT_TYPE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName);
        String result = "";

        if (!exists(employeeName)) {
            System.out.println("Error! Employee " + employeeName + " not found!");
        } else {
            try {
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    result = rs.getString("type");
                }
            } catch (SQLException e) {
                System.out.println("MySQL query error! " + query);
            }
        }
        return result;
    }

    @Override
    public boolean exists(String name) {
        String query = SELECT_NAME_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);
        boolean result = false;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public void done() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("MySQL error! DB statement not close!");
            }
        }
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("MySQL error! DB connection not close!");
            }
        }
    }
}

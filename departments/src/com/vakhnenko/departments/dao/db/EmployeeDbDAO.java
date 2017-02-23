package com.vakhnenko.departments.dao.db;

import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.dao.file.EmployeeFileDAO;
import com.vakhnenko.departments.entity.employee.*;

import java.sql.*;
import java.util.*;

import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.*;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.*;

/**
 * Created for practice on 10.02.2017 10:21
 */
public class EmployeeDbDAO<T extends Employee> extends EmployeeDAO<T> {
    private Connection dbConnection;
    private Statement statement;

    public EmployeeDbDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        this.statement = dbConnection.createStatement();
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public List<String> read() {
        return null;
    }

    @Override
    public void add(Employee employee) {
        String type = employee.getType();

        if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
            insertIntoDB(statement, INSERT_INTO_DB_MANAGER
                    + swq(employee.getName()) + ","
                    + swq(Integer.toString(employee.getAge())) + ","
                    + swq(type) + ","
                    + swq(employee.getDepartment()) + ","
                    + swq(((Manager) employee).getMethodology()) + CLOSING_STRUCTURE);
        } else {
            insertIntoDB(statement, INSERT_INTO_DB_DEVELOPER
                    + swq(employee.getName()) + ","
                    + swq(Integer.toString(employee.getAge())) + ","
                    + swq(type) + ","
                    + swq(employee.getDepartment()) + ","
                    + swq(((Developer) employee).getLanguage()) + CLOSING_STRUCTURE);
        }
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
        if (age > 0) {
            updateEmployee(" age = " + age, swq(employeeName));
        }
        if (!departmentName.equals("")) {
            updateEmployee(" department_name = " + swq(departmentName), swq(employeeName));
        }
        if (!methodology.equals("")) {
            updateEmployee(" methodology = " + swq(methodology), swq(employeeName));
        }
        if (!language.equals("")) {
            updateEmployee(" language = " + swq(language), swq(employeeName));
        }
    }

    private void updateEmployee(String updateQuery, String employeeName) {
        String query = UPDATE_EMPLOYEE_DB_EMPLOYEE + updateQuery + WHERE_NAME_IS_EQUAL + employeeName;
        try {
            //employeeDAO.save(new Employee(id, name, age ....))
            //
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("MySQL error! " + query);
        }
    }

    @Override
    public void delete(String name) {
        String query = DELETE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
    }

    @Override
    public String getType(String employeeName) {
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
    public List<T> getAll() {
        String query = SELECT_ALL_FROM_DB_EMPLOYEE;
        List<T> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String name;

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                names.add(name);
            }
            for (String employeeName:names) {
                result.add(getByName(employeeName));
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public List<T> getAll(String departmentName) {
        String query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL + swq(departmentName);
        List<T> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String name;

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                names.add(name);
            }
            for (String employeeName:names) {
                result.add(getByName(employeeName));
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public List<T> getAll(String departmentName, int age) {
        String query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_AGE_IS_EQUAL + age + " AND "
                + DEPARTMENT_IS_EQUAL + swq(departmentName);
        List<T> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String name;

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                names.add(name);
            }
            for (String employeeName:names) {
                result.add(getByName(employeeName));
            }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public T getByName(String name) {
        String query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);
        T result = null;
        String type = "";
        int age;
        String department;
        String methodology;
        String language;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                type = rs.getString("type");
                age =  rs.getInt("age");
                department =  rs.getString("department_name");
                if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
                    methodology =  rs.getString("methodology");
                    result = (T)(new Manager(name, type, age, department, methodology));
                } else {
                    language =  rs.getString("language");
                    result = (T)(new Developer(name, type, age, department, language));
                }
        } catch (SQLException e) {
            System.out.println("MySQL query error! " + query);
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
    public int getMaxEmployees(String departmentName, String type) {
        int result = 0;

        String query = SELECT_COUNT_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL
                + swq(departmentName) + " AND " + TYPE_IS_EQUAL + swq(type);
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                result = rs.getInt("count");
            }
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

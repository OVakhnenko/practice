package com.vakhnenko.departments.utils;

public class Constants {
    public static final String EXIT_COMMAND = "EXIT";
    public static final String HELP_COMMAND = "HELP";
    public static final String CREATE_COMMAND = "CREATE";
    public static final String PRINT_ALL_DEPARTMENTS_COMMAND = "DEPARTMENTS";
    public static final String REMOVE_COMMAND = "RM";
    public static final String OPEN_COMMAND = "OPEN";
    public static final String UPDATE_COMMAND = "UPDATE";
    public static final String SAVE_COMMAND = "SAVE";
    public static final String READ_COMMAND = "READ";
    public static final String ALL_COMMAND = "ALL";
    public static final String SEARCH_COMMAND = "SEARCH";
    public static final String TOP_COMMAND = "TOP";

    public static final String DEPARTMENT_KEY = "-D";
    public static final String EMPLOYEE_KEY = "-E";
    public static final String NAME_EMPLOYEE_KEY = "-N";
    public static final String AGE_EMPLOYEE_KEY = "-A";
    public static final String TYPE_EMPLOYEE_KEY = "-T";
    public static final String LANGUAGE_EMPLOYEE_KEY = "-L";
    public static final String METHODOLOGY_EMPLOYEE_KEY = "-M";
    public static final String DEPARTMENT_EMPLOYEE_KEY = "-DN";

    public static final String EMPLOYEE_MANAGER_TYPE = "M";
    public static final String EMPLOYEE_DEVELOPER_TYPE = "D";

    public static final int COMMAND_POSITION = 0;
    public static final int FIRST_KEY_POSITION = 1;

    public static final boolean USE_BR = true;
    public static final boolean NOT_USE_BR = false;
    public static final boolean CREATE_EMPLOYEE = false;
    public static final boolean UPDATE_EMPLOYEE = true;

    public static final String FILE_DAO_FILE_NAME = "\\departments.txt";

    public static final String DB_DRIVER = "com.mysql.jdbc.Driver"; //"oracle.jdbc.driver.OracleDriver";
    public static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/departments";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "oJ0K58fE29kZhgXcQQV7";

    public static final int DB_CREATE_ERROR_EXIT_CODE = -1;
    public static final int DB_DRIVER_ERROR_EXIT_CODE = -2;
    public static final int DB_CONNECTION_ERROR_EXIT_CODE = -3;

    public static final String DEPARTMENT_TABLE_NAME = "department";
    public static final String EMPLOYEE_TABLE_NAME = "employee";

    public static final String CREATE_DB_DEPARTMENT_IF_NOT_EXISTS = ""
            + "create table if not exists department ("
            + "id int not null auto_increment,"
            + "name varchar(25) not null,"
            + "primary key (id)"
            + ")engine = innodb";
    public static final String CREATE_DB_EMPLOYEE_IF_NOT_EXISTS = ""
            + "create table if not exists employee ("
            + "id int not null auto_increment,"
            + "name varchar(20) not null,"
            + "age tinyint not null,"
            + "type varchar(1) not null,"
            + "department_name varchar(25) not null,"
            + "methodology varchar(20) not null default '',"
            + "language varchar(20) not null default '' ,"
            + "primary key (id)"
            + ")engine = innodb";

    public static final String INSERT_INTO_DB_DEPERTMENT = ""
            + "insert into department (name) values (";
    public static final String INSERT_INTO_DB_MANAGER = ""
            + "insert into employee (name, age, type, department_name, methodology) values (";
    public static final String INSERT_INTO_DB_DEVELOPER = ""
            + "insert into employee (name, age, type, department_name, language) values (";

    public static final String DELETE_FROM_DB_DEPERTMENT = ""
            + "delete from department ";
    public static final String DELETE_FROM_DB_EMPLOYEE = ""
            + "delete from employee ";

    public static final String SELECT_NAME_FROM_DB_DEPARTMENT = ""
            + "select name from department ";
    public static final String SELECT_NAME_FROM_DB_EMPLOYEE = ""
            + "select name from employee ";
    public static final String SELECT_TYPE_FROM_DB_EMPLOYEE = ""
            + "select type from employee ";
    public static final String SELECT_ALL_FROM_DB_EMPLOYEE = ""
            + "select * from employee ";
    public static final String SELECT_ALL_FROM_DB_EMPLOYEE_WO = ""
            + "select * from employee order by department_name";
    public static final String SELECT_COUNT_FROM_DB_EMPLOYEE = ""
            + "select count(*) as count from employee ";
    public static final String CLOSING_STRUCTURE = ")";

    public static final String UPDATE_EMPLOYEE_DB_EMPLOYEE = ""
            + "update employee set ";

    public static final String WHERE_NAME_IS_EQUAL = " "
            + "where name = ";
    public static final String WHERE_DEPARTMENT_NAME_IS_EQUAL = " "
            + "where department_name = ";
    public static final String WHERE_AGE_IS_EQUAL = " "
            + "where age = ";
    public static final String DEPARTMENT_IS_EQUAL = " "
            + "department_name = ";
    public static final String TYPE_IS_EQUAL = " "
            + "type = ";

    private Constants() {
    }
}

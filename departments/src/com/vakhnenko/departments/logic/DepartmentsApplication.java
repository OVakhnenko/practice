package com.vakhnenko.departments.logic;

import java.io.*;
import java.util.List;

import static com.vakhnenko.departments.constants.Constants.*;
import static com.vakhnenko.departments.utils.PrintHelper.*;
import static com.vakhnenko.departments.utils.Strings.*;
import static com.vakhnenko.departments.utils.Arrays.*;

import com.vakhnenko.departments.dao.*;

public class DepartmentsApplication<T extends OfficeDAO> {
    private T office;

    public DepartmentsApplication(T office) {
        this.office = office;
    }

    public void run() throws IOException {
        String command;
        boolean noExit = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        printFirstScreen();

        /*while (noExit) {
            command = reader.readLine();
            noExit = readCommand(command);
        }*/
        readCommand("create -d 111 1111 11111");
        readCommand("create -d 111 1111 11111");
        readCommand("create -d 222 2222 22222");
        readCommand("create -d 333 3333 33333");
        readCommand("create -d 444 4444 44444");
        readCommand("create -d 555 5555 55555");
        readCommand("create -e -n Ivan1 Ivanovich1 -t m -a 21 -m Scrum -dn 111 1111 11111");
        readCommand("create -e -n Ivan2 Ivanovich2 -t m -a 21 -m Scrum -dn 222 2222 22222");
        readCommand("create -e -n Ivan3 Ivanovich3 -t m -a 21 -m Scrum -dn 333 3333 33333");
        readCommand("create -e -n Ivan2 Ivanovich5 -t d -a 22 -l Java -dn 222 2222 22222");
        readCommand("create -e -n Ivan3 Ivanovich6 -t d -a 23 -l Java -dn 222 2222 22222");
        readCommand("create -e -n Ivan3 Ivanovich7 -t d -a 23 -l Java -dn 222 2222 22222");
        readCommand("create -e -n Ivan3 Ivanovich8 -t d -a 23 -l Java -dn 222 2222 22222");
        readCommand("departments");
        readCommand("open -d 222 2222 22222");
        readCommand("open -e Ivan1 Ivanovich1");
        readCommand("update -e -n Ivan3 Ivanovich3 -a 35 -m Scrum3 -dn 222 2222 22222");
        readCommand("update -e -n Ivan2 Ivanovich5 -a 36 -l Java2 -dn 333 3333 33333");
        readCommand("update -e -n Ivan2 Ivanovich5 -l Java3 -dn 111 1111 11111");
        readCommand("update -e -n Ivan2 Ivanovich5 -dn 555 5555 55555");
        readCommand("update -e -n Ivan2 Ivanovich5 ");
        readCommand("all");
        readCommand("search -e -a 23 -d 222 2222 22222");
        readCommand("top -d -t d");
        readCommand("top -d -t m");
        readCommand("save");
    }

    public void done() {
        office.done();
    }

    public boolean saveToFile() throws IOException {
        return office.saveToFile();
    }

    public void readFromFile() throws IOException {
        List<String> lines = office.readFromFile();

        if (lines != null) {
            for (String line : lines) {
                readCommand(line);
            }
        }
    }

    private boolean readCommand(String command) throws IOException {
        command = shrink(command);
        String[] commands = command.split(" ");

        switch (commands[COMMAND_POSITION]) {
            case EXIT_COMMAND:
                return false;
            case HELP_COMMAND:
                printHelp();
                break;
            case CREATE_COMMAND:
                create(commands);
                break;
            case OPEN_COMMAND:
                open(commands);
                break;
            case UPDATE_COMMAND:
                update(commands);
                break;
            case REMOVE_COMMAND:
                remove(commands);
                break;
            case PRINT_ALL_DEPARTMENTS_COMMAND:
                printAllDepartments();
                break;
            case ALL_COMMAND:
                printAll();
                break;
            case SEARCH_COMMAND:
                printSearchedEmployee(commands);
                break;
            case TOP_COMMAND:
                printTopEmployee(commands);
                break;
            case SAVE_COMMAND:
                saveToFile();
                break;
            case READ_COMMAND:
                readFromFile();
                break;
            default:
                System.out.println("Error! Unknown command - \"" + command + "\" type \"help\" for commands list");
        }
        return true;
    }

    private void create(String[] commands) {
        switch (getCommands(commands, FIRST_KEY_POSITION)) {
            case DEPARTMENT_KEY:
                createDepartment(commands);
                break;
            case EMPLOYEE_KEY:
                createEmployee(commands, CREATE_EMPLOYEE);
                break;
            default:
                printSyntaxError(commands);
        }
    }

    private void createDepartment(String[] commands) {
        String name = getStringFromManyWords(commands, FIRST_KEY_POSITION);

        if (!name.equals("")) {
            office.createDepartment(name);
            office.printAllDepartments();
        } else {
            System.out.println("Error! Name is empty");
        }
    }

    private void createEmployee(String[] commands, boolean update) {
        int positionOfKey;
        String type;

        positionOfKey = searchKeyInArray(commands, NAME_EMPLOYEE_KEY);
        String employeeName = getStringFromManyWords(commands, positionOfKey);

        if (employeeName.equals("")) {
            System.out.println("Error! Name is empty");
            return;
        }

        if (update) {
            if (!employeeExists(employeeName)) {
                System.out.println("The employee \"" + employeeName + "\" not found");
                return;
            } else {
                type = getTypeEmployee(employeeName);
            }
        } else {
            if (employeeExists(employeeName)) {
                System.out.println("The employee \"" + employeeName + "\" already exists");
                return;
            }
            type = getKeyFromArray(commands, TYPE_EMPLOYEE_KEY);
        }

        positionOfKey = searchKeyInArray(commands, DEPARTMENT_EMPLOYEE_KEY);
        String departmentName = getStringFromManyWords(commands, positionOfKey);

        if (!update) {
            if (departmentName.equals("")) {
                System.out.println("Error! Department is empty");
                return;
            }
            if (!departmentExists(departmentName)) {
                System.out.println("Error! Department not exists!");
                printAllDepartments();
                return;
            }
        }
        // type already initialized
        int age;
        try {
            age = Integer.valueOf(getKeyFromArray(commands, AGE_EMPLOYEE_KEY));
        } catch (NumberFormatException e) {
            age = 0;
        }
        String language = getKeyFromArray(commands, LANGUAGE_EMPLOYEE_KEY);
        String methodology = getKeyFromArray(commands, METHODOLOGY_EMPLOYEE_KEY);

        if (type.equals(EMPLOYEE_MANAGER_TYPE) && (!(language.equals("")))) {
            System.out.println("Error! The manager doesn’t have lenguage field");
            return;
        } else if (type.equals(EMPLOYEE_DEVELOPER_TYPE) && (!(methodology.equals("")))) {
            System.out.println("Error! The developer doesn’t have methodology field");
            return;
        }

        switch (type) {
            case EMPLOYEE_MANAGER_TYPE:
                if (update) {
                    updateManagerAndPrint(employeeName, age, departmentName, methodology);
                } else {
                    createManagerAndPrint(employeeName, type, age, departmentName, methodology);
                }
                break;
            case EMPLOYEE_DEVELOPER_TYPE:
                if (update) {
                    updateDeveloperAndPrint(employeeName, age, departmentName, language);
                } else {
                    createDeveloperAndPrint(employeeName, type, age, departmentName, language);
                }
                break;
            default:
                System.out.println("Error! The unknown type of employee!");
        }
    }

    public void createManagerAndPrint(String employeeName, String type, int age, String departmentName, String methodology) {
        office.createManager(employeeName, type, age, departmentName, methodology);
        printEmployee(employeeName, NOT_USE_BR);
    }

    public void createDeveloperAndPrint(String employeeName, String type, int age, String departmentName, String language) {
        office.createDeveloper(employeeName, type, age, departmentName, language);
        printEmployee(employeeName, NOT_USE_BR);
    }

    public void updateManagerAndPrint(String employeeName, int age, String departmentName, String methodology) {
        office.updateManager(employeeName, age, departmentName, methodology);
        printEmployee(employeeName, NOT_USE_BR);
    }

    public void updateDeveloperAndPrint(String employeeName, int age, String departmentName, String language) {
        office.updateDeveloper(employeeName, age, departmentName, language);
        printEmployee(employeeName, NOT_USE_BR);
    }

    private void remove(String[] commands) {
        switch (getCommands(commands, FIRST_KEY_POSITION)) {
            case DEPARTMENT_KEY:
                removeDepartment(commands);
                break;
            case EMPLOYEE_KEY:
                removeEmployee(commands);
                break;
            default:
                printSyntaxError(commands);
        }
    }

    private void removeDepartment(String[] commands) {
        String name = getStringFromManyWords(commands, FIRST_KEY_POSITION);
        office.removeDepartment(name);
        printAllDepartments();
    }

    private void removeEmployee(String[] commands) {
        String name = getStringFromManyWords(commands, FIRST_KEY_POSITION);
        office.removeEmployee(name);
    }

    private void open(String[] commands) {
        switch (getCommands(commands, FIRST_KEY_POSITION)) {
            case DEPARTMENT_KEY:
                openDepartment(commands);
                break;
            case EMPLOYEE_KEY:
                openEmployee(commands);
                break;
            default:
                printSyntaxError(commands);
        }
    }

    private void openDepartment(String[] commands) {
        String departmentName = getStringFromManyWords(commands, FIRST_KEY_POSITION);

        if (!departmentName.equals("")) {
            printAllEmployee(departmentName);
        } else {
            System.out.println("Error! Name is empty");
        }
    }

    private void openEmployee(String[] commands) {
        String employeeName = getStringFromManyWords(commands, FIRST_KEY_POSITION);
        office.openEntityWithName(employeeName);
    }

    private void update(String[] commands) {
        switch (getCommands(commands, FIRST_KEY_POSITION)) {
            case EMPLOYEE_KEY:
                createEmployee(commands, UPDATE_EMPLOYEE);
                break;
            default:
                printSyntaxError(commands);
        }
    }

    public boolean departmentExists(String departmentName) {
        return office.departmentExists(departmentName);
    }

    public boolean employeeExists(String employeeName) {
        return office.employeeExists(employeeName);
    }

    public String getTypeEmployee(String employeeName) {
        return office.getTypeEmployee(employeeName);
    }

    public void printAllEmployee(String employeeName) {
        office.printAllEmployee(employeeName);
    }

    public void printAllDepartments() {
        office.printAllDepartments();
    }

    public void printEmployee(String employeeName, boolean use_br) {
        office.printEmployee(employeeName, use_br);
    }

    public void printAll() {
        office.printAll();
    }

    private void printSearchedEmployee(String[] commands) {
        int positionOfKey;
        int age;

        if (getCommands(commands, FIRST_KEY_POSITION).equals(EMPLOYEE_KEY)) {
            positionOfKey = searchKeyInArray(commands, DEPARTMENT_KEY);
            String departmentName = getStringFromManyWords(commands, positionOfKey);
            try {
                age = Integer.valueOf(getKeyFromArray(commands, AGE_EMPLOYEE_KEY));
            } catch (NumberFormatException e) {
                age = 0;
            }
            office.printSearchedEmployeeAge(departmentName, age);
        } else {
            System.out.println("Error! Unknown command - \" type \"help\" for commands list");
        }
    }

    private void printTopEmployee(String[] commands) {
        String type = getKeyFromArray(commands, TYPE_EMPLOYEE_KEY);
        office.printTopEmployee(type);
    }

    public void printHelp() {
        printHelpCommandsList();
        printHelpDepartment();
        printHelpEmployee();
        printHelpReadSave();
        printHelpExit();
    }
}
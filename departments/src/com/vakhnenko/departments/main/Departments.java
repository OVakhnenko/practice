package com.vakhnenko.departments.main;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.vakhnenko.departments.constants.Constants.*;

import com.vakhnenko.departments.department.*;
import com.vakhnenko.departments.employee.*;
import com.vakhnenko.departments.entity.*;

public class Departments {
    private String fileName = new File("").getAbsolutePath() + "\\departments.txt";
    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    public Departments() {
        departmentDAO = new DepartmentDAO();
        employeeDAO = new EmployeeDAO();
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
        readCommand("create -d 222 2222 22222");
        readCommand("create -d 333 3333 33333");
        /*readCommand("create -e -n Ivan1 Ivanovich1 -t m -a 21 -m Scrum -dn 222 2222 22222");
        readCommand("create -e -n Ivan2 Ivanovich2 -t d -a 22 -l Java -dn 222 2222 22222");
        readCommand("create -e -n Ivan3 Ivanovich3 -t d -a 23 -l Java -dn 222 2222 22222");
        readCommand("save");*/
    }

    private boolean saveToFile() {
        boolean saved = false;

        if (departmentDAO.getSize() == 0) {
            System.out.println("Error! No departments");
        } else {
            try (FileWriter writer = new FileWriter(fileName, false)) {
                for (Entity department : departmentDAO.getAll()) {
                    saved = ((Department) department).save(writer);
                    if (!saved)
                        return false;
                }
                for (Entity employee : employeeDAO.getAll()) {
                    if (((Employee) employee).getType().equals(EMPLOYEE_MANAGER_TYPE)) {
                        saved = ((Manager) employee).save(writer);
                    } else {
                        saved = ((Developer) employee).save(writer);
                    }
                    ((Employee) employee).writeln(writer);
                    if (!saved)
                        return false;
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("Write error!");
            }
            if (saved) {
                System.out.println("All data saved successfully");
            }
        }
        return true;
    }

    private void readFromFile() {
        if (departmentDAO.getSize() != 0) {
            System.out.println("Error! Departments are exists");
        } else {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileName), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    readCommand(line);
                }
            } catch (IOException e) {
                System.out.println("Read error!");
            }
        }

    }

    private boolean readCommand(String command) {
        command = shrink(command);
        String[] commands = command.split(" ");

        switch (commands[COMMAND_POSITION]) {
            case EXIT_COMMAND:
                printBye();
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

    public void createDepartment(String[] commands) {
        String name = getStringFromManyWords(commands, FIRST_KEY_POSITION);

        if (!name.equals("")) {
            departmentDAO.create(name);
            printAllDepartments();
        } else {
            System.out.println("Error! Name is empty");
        }
    }

    private void createEmployee(String[] commands, boolean update) {
        int positionOfKey;
        Entity entity = null; // initialization for IDEA :)
        String type;

        positionOfKey = searchKeyInArray(commands, NAME_EMPLOYEE_KEY);
        String employeeName = getStringFromManyWords(commands, positionOfKey);

        if (employeeName.equals("")) {
            System.out.println("Error! Name is empty");
            return;
        }

        if (update) {
            if (!employeeDAO.exists(employeeName)) {
                System.out.println("The employee \"" + employeeName + "\" not found");
                return;
            } else {
                entity = employeeDAO.search(employeeName);
                type = ((Employee) entity).getType();
            }
        } else {
            if (employeeDAO.exists(employeeName)) {
                System.out.println("The employee \"" + employeeName + "\" already exists");
                return;
            }
            type = getKeyFromArray(commands, TYPE_EMPLOYEE_KEY);
        }

        positionOfKey = searchKeyInArray(commands, DEPARTMENT_EMPLOYEE_KEY);
        String departmentName = getStringFromManyWords(commands, positionOfKey);

        if (departmentName.equals("")) {
            System.out.println("Error! Department is empty");
            return;
        }

        if (!departmentDAO.exists(departmentName)) {
            System.out.println("Error! Department not exists!");
            printAllDepartments();
            return;
        }

        // type already initialized
        int age = Integer.valueOf(getKeyFromArray(commands, AGE_EMPLOYEE_KEY));
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
                    ((Manager) entity).setAge(age);
                    ((Manager) entity).setMethodology(methodology);
                    ((Manager) entity).setDepartment(departmentName);
                } else {
                    entity = new Manager(employeeName, type, age, departmentName, methodology);
                    employeeDAO.add(entity);
                }
                printEmploee(entity, NOT_USE_BR);
                break;
            case EMPLOYEE_DEVELOPER_TYPE:
                if (update) {
                    ((Developer) entity).setAge(age);
                    ((Developer) entity).setLanguage(language);
                    ((Developer) entity).setDepartment(departmentName);
                } else {
                    entity = new Developer(employeeName, type, age, departmentName, language);
                    employeeDAO.add(entity);
                }
                printEmploee(entity, NOT_USE_BR);
                break;
            default:
                System.out.println("Error! The unknown type of employee!");
        }
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
        departmentDAO.delete(name);
        printAllDepartments();
    }

    private void removeEmployee(String[] commands) {
        String name = getStringFromManyWords(commands, FIRST_KEY_POSITION);
        employeeDAO.delete(name);
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
        Entity tmp = employeeDAO.search(employeeName);

        if (tmp != null) {
            printEmploee(tmp, USE_BR);
        } else {
            System.out.println(employeeDAO.getEmployeeStatus() + " \"" + employeeName + "\" not found!");
        }
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

    private void printAllEmployee(String department) {

        for (Entity employee : employeeDAO.getAll()) {
            if (((Employee) employee).getDepartment().equals(department))
                printEmploee(employee, NOT_USE_BR);
        }
    }

    private void printAllDepartments() {
        departmentDAO.printAll();
    }

    private void printEmploee(Entity entity, boolean use_br) {
        System.out.print("Name " + entity.getName() + " " + ((use_br) ? "\n" : ""));
        System.out.print("ID " + entity.getID() + " " + ((use_br) ? "\n" : ""));

        System.out.print("Age " + ((Employee) entity).getAge() + " " + ((use_br) ? "\n" : ""));
        System.out.print("Dep " + ((Employee) entity).getDepartment() + " " + ((use_br) ? "\n" : ""));

        if (entity.getClass().getName().equals("com.vakhnenko.departments.Manager")) {
            System.out.print("Type (" + ((Employee) entity).getType() + ") - MANAGER " + ((use_br) ? "\n" : ""));
            System.out.print("Meth " + ((Manager) entity).getMethodology() + " " + ((use_br) ? "\n" : ""));
        } else if (entity.getClass().getName().equals("com.vakhnenko.departments.Developer")) {
            System.out.print("Type (" + ((Employee) entity).getType() + ") - DEVELOPER " + ((use_br) ? "\n" : ""));
            System.out.print("Lang " + ((Developer) entity).getLanguage() + " " + ((use_br) ? "\n" : ""));
        }
        System.out.println();
    }

    private void printFirstScreen() {
        System.out.println("Departments and Employees");
        System.out.println("Type \"help\" for commands list or type \"exit\" for exit");
        System.out.println("");
    }

    private void printHelp() {
        System.out.println("commanrds list:");
        System.out.println("");
        System.out.println("type \"create -d department_name\" for create department \"department_name\"");
        System.out.println("type \"rm -d department_name\" for remove department \"department_name\"");
        System.out.println("type \"departments\" for print list of all departments");
        System.out.println("");
        System.out.println("type \"create -e -n employee_name -t m -a age -m methodology\" for for create manager of");
        System.out.println("type \"create -e -n employee_name -t d -a age -l language \" for for create developer");
        System.out.println("type \"rm -e employee_name\" for remove employee \"employee_name\"");
        System.out.println("type \"open -d department_name\" for watch department details");
        System.out.println("type \"open -e employee_name\" for watch employee details");
        System.out.println("");
        System.out.println("type \"save\" for save data to file");
        System.out.println("type \"read\" for read data from file");
        System.out.println("");
        System.out.println("type \"help\" for commands list");
        System.out.println("type \"exit\" for exit");
        System.out.println("");
    }

    private void printSyntaxError(String[] commands) {
        System.out.println("Syntax Error! - \"" + getAllArrayStrings(commands) + "\" type \"help\" for commands list");
    }

    private void printBye() {
        System.out.println("Bye!");
    }

    private String getAllArrayStrings(String[] strings) {
        String result = "";
        for (String string : strings) result += string;
        return result;
    }

    private String getCommands(String[] commands, int index) {
        if (index < commands.length) {
            return commands[index];
        } else {
            return getAllArrayStrings(commands);
        }
    }

    private int searchKeyInArray(String[] commands, String key) {
        int result = -1;

        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(key)) {
                result = i;
                break;
            }
        }
        return result;
    }

    private String getKeyFromArray(String[] commands, String key) {
        String result = "";
        int index;

        index = searchKeyInArray(commands, key);
        if ((index != -1) && (index < commands.length - 1)) {
            result = commands[index + 1];
        }
        return result;
    }

    public String getStringFromManyWords(String[] commands, int cindex) {
        String result = "";

        if (cindex == -1) {
            return result;
        }
        if ((cindex + 1 < commands.length) && (!commands[cindex + 1].contains("-"))) {
            result = commands[cindex + 1] + " " + getStringFromManyWords(commands, cindex + 1);
        }
        return result.trim();
    }

    private String shrink(String command) {
        String result = command.toUpperCase().trim();

        while (result.contains("  ")) {
            result = result.replace("  ", " ");
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Departments departments = new Departments();
        departments.run();
    }
}

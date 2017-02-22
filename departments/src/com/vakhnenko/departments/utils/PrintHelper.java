package com.vakhnenko.departments.utils;

import static com.vakhnenko.departments.utils.Arrays.*;

public class PrintHelper {

    public static void printHelpCommandsList() {
        System.out.println("commanrds list:");
        System.out.println("");
    }

    public static void printHelpDepartment() {
        System.out.println("type \"create -d department_name\" for create department \"department_name\"");
        System.out.println("type \"rm -d department_name\" for remove department \"department_name\"");
        System.out.println("type \"departments\" for print list of all departments");
        System.out.println("");
    }

    public static void printHelpEmployee() {
        System.out.println("type \"create -e -n employee_name -t m -a age -m methodology\" for for create manager of");
        System.out.println("type \"create -e -n employee_name -t d -a age -l language \" for for create developer");
        System.out.println("type \"rm -e employee_name\" for remove employee \"employee_name\"");
        System.out.println("type \"open -d department_name\" for watch department details");
        System.out.println("type \"open -e employee_name\" for watch employee details");
        System.out.println("");
    }

    public static void printHelpReadSave() {
        System.out.println("type \"save\" for save data to file");
        System.out.println("type \"read\" for read data from file");
        System.out.println("");
    }

    public static void printHelpExit() {
        System.out.println("type \"help\" for commands list");
        System.out.println("type \"exit\" for exit");
        System.out.println("");
    }

    public static void printSyntaxError(String[] commands) {
        System.out.println("Syntax Error! - \"" + getAllArrayStrings(commands) + "\" type \"help\" for commands list");
    }

    public static void printFirstScreen() {
        System.out.println("Departments and Employees");
        System.out.println("Type \"help\" for commands list or type \"exit\" for exit");
        System.out.println("");
    }

    public static void printHelpSomething() {
        System.out.println("Type \"all\" for print list of all departments and employees");
        System.out.println("Type \"search -e -a age_to_search -d department\" for search employees");
        System.out.println("Type \"top -d -t type_of_employee\"  for search employees");
        System.out.println("");
    }
}

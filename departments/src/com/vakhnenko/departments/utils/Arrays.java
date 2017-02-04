package com.vakhnenko.departments.utils;

public class Arrays {
    public static int searchKeyInArray(String[] commands, String key) {
        int result = -1;

        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(key)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static String getKeyFromArray(String[] commands, String key) {
        String result = "";
        int index;

        index = searchKeyInArray(commands, key);
        if ((index != -1) && (index < commands.length - 1)) {
            result = commands[index + 1];
        }
        return result;
    }

    public static String getAllArrayStrings(String[] strings) {
        String result = "";
        for (String string : strings) result += string;
        return result;
    }

    public static String getCommands(String[] commands, int index) {
        if (index < commands.length) {
            return commands[index];
        } else {
            return getAllArrayStrings(commands);
        }
    }
}

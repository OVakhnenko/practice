package com.vakhnenko.departments.utils;

import java.io.*;
import java.nio.charset.*;

import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 10.02.2017 9:07
 */
abstract public class ConnectionUtilFile {
    private static String fileName = new File("").getAbsolutePath() + FILE_DAO_FILE_NAME;

    public static FileWriter getFileConnectionWriter() {
        FileWriter result = null;
        try {
            result = new FileWriter(fileName, false);
        } catch (IOException e) {
            System.out.println("Write error!");
        }
        return result;
    }

    public static BufferedReader getFileConnectionReader() {
        BufferedReader result = null;

        try {
            result = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileName), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Read error!");
        }
        return result;
    }
}

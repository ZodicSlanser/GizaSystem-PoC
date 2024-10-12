package com.gizasystems.PoC.logging;

import java.io.File;

public class FileUtils {

    public static void createLogsDirectory() {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            if (logDir.mkdir()) {
                System.out.println("Log directory created");
            } else {
                System.err.println("Failed to create log directory");
            }
        }
    }
}

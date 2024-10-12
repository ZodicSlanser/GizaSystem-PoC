package com.gizasystems.PoC.logging;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LoggingService {

    private static final String REQUEST_LOG_FILE = "logs/requests.log";
    private static final String ABNORMAL_LOG_FILE = "logs/abnormal_behavior.log";

    public void logRequest(String requestInfo) {
        logToFile(REQUEST_LOG_FILE, requestInfo);
    }

    public void logAbnormalBehavior(String behaviorInfo) {
        logToFile(ABNORMAL_LOG_FILE, behaviorInfo);
    }

    private void logToFile(String fileName, String logInfo) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(LocalDateTime.now() + " - " + logInfo + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}

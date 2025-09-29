package com.astronaut.schedule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE = LOG_DIR + File.separator + "app.log";
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void ensureLogDir() {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    private static synchronized void write(String level, String msg) {
        ensureLogDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String ts = LocalDateTime.now().format(DF);
            pw.printf("[%s][%s] %s%n", ts, level, msg);
        } catch (IOException e) {
            System.err.println("[LOGGER-ERR] Could not write log: " + e.getMessage());
        }
    }

    public static void info(String msg)  { write("INFO", msg); }
    public static void warn(String msg)  { write("WARN", msg); }
    public static void error(String msg) { write("ERROR", msg); }
}

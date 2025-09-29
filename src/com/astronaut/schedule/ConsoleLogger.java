package com.astronaut.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String format(String level, String msg) {
        String ts = LocalDateTime.now().format(DF);
        return String.format("[%s][%s] %s", ts, level, msg);
    }

    public static void info(String msg) {
        String out = format("INFO", msg);
        System.out.println(out);
        FileLogger.info(msg); // Make sure FileLogger.java exists in same package
    }

    public static void warn(String msg) {
        String out = format("WARN", msg);
        System.out.println(out);
        FileLogger.warn(msg);
    }

    public static void error(String msg) {
        String out = format("ERROR", msg);
        System.err.println(out);
        FileLogger.error(msg);
    }
}

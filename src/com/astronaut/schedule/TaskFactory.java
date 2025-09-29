package com.astronaut.schedule;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TaskFactory {
    /**
     * Create Task from user inputs.
     * Accepts times with colon or dot (e.g., "07:30" or "7:30" or "07.30").
     */
    public static Task createTask(String description, String startStr, String endStr, String priorityStr, String categoryStr) throws IllegalArgumentException {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        // Normalize separators: allow "10.00" or "10:00"
        startStr = startStr == null ? "" : startStr.trim().replace('.', ':');
        endStr = endStr == null ? "" : endStr.trim().replace('.', ':');

        LocalTime start;
        LocalTime end;
        try {
            start = LocalTime.parse(startStr);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid start time '" + startStr + "'. Use HH:mm (e.g., 09:30 or 9:30).");
        }
        try {
            end = LocalTime.parse(endStr);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid end time '" + endStr + "'. Use HH:mm (e.g., 17:00 or 5:00).");
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time (same day). Start: " + start + ", End: " + end + ".");
        }

        Task.Priority priority;
        if (priorityStr == null) priorityStr = "";
        switch (priorityStr.trim().toUpperCase()) {
            case "HIGH": priority = Task.Priority.HIGH; break;
            case "MEDIUM": priority = Task.Priority.MEDIUM; break;
            default: priority = Task.Priority.LOW; break;
        }

        Task.Category category;
        try {
            category = Task.Category.valueOf(categoryStr.trim().toUpperCase().replace(' ', '_'));
        } catch (Exception ex) {
            category = Task.Category.OTHER;
        }

        return new Task(description.trim(), start, end, priority, category);
    }
}

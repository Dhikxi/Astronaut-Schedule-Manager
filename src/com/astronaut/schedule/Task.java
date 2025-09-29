package com.astronaut.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task {
    public enum Priority { LOW, MEDIUM, HIGH }
    public enum Status { PENDING, COMPLETED }
    public enum Category { LIFE_SUPPORT, RESEARCH, EXERCISE, COMMUNICATION, OTHER }

    private String description;
    private LocalTime start;
    private LocalTime end;
    private Priority priority;
    private Status status;
    private Category category;
    private LocalDateTime completedAt; // null if not completed

    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Task(String description, LocalTime start, LocalTime end, Priority priority, Category category) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.status = Status.PENDING;
        this.category = category;
        this.completedAt = null;
    }

    public String getDescription() { return description; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Category getCategory() { return category; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public boolean overlaps(Task other) {
        // overlap if start < other.end && other.start < end
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    public String formattedCompletedAt() {
        return completedAt == null ? "-" : completedAt.format(DF);
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %s [%s] (%s) <%s>",
                start.format(TF),
                end.format(TF),
                description,
                priority,
                status,
                category);
    }
}

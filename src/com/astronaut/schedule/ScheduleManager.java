package com.astronaut.schedule;

import java.util.*;
import java.util.stream.Collectors;

public class ScheduleManager {
    private static ScheduleManager instance;
    private final List<Task> tasks;
    private final List<ITaskObserver> observers;

    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static synchronized ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }

    public void addObserver(ITaskObserver o) {
        observers.add(o);
    }

    public void removeObserver(ITaskObserver o) {
        observers.remove(o);
    }

    private void notifyAdded(Task t) {
        for (ITaskObserver o : observers) o.onTaskAdded(t);
    }
    private void notifyRemoved(String desc) {
        for (ITaskObserver o : observers) o.onTaskRemoved(desc);
    }

    public synchronized String addTask(Task t) {
        // check conflict
        for (Task existing : tasks) {
            if (t.overlaps(existing)) {
                ConsoleLogger.warn("Conflict detected with: " + existing.getDescription());
                return "CONFLICT: Task conflicts with existing task \"" + existing.getDescription() + "\"";
            }
        }
        tasks.add(t);
        ConsoleLogger.info("Task added: " + t.getDescription());
        notifyAdded(t);
        return "Task added successfully.";
    }

    public synchronized String removeTask(String description) {
        Optional<Task> found = tasks.stream()
                .filter(x -> x.getDescription().equalsIgnoreCase(description.trim()))
                .findFirst();
        if (found.isPresent()) {
            tasks.remove(found.get());
            ConsoleLogger.info("Task removed: " + description);
            notifyRemoved(description);
            return "Task removed successfully.";
        } else {
            ConsoleLogger.warn("Attempted to remove non-existent task: " + description);
            return "ERROR: Task not found.";
        }
    }

    public synchronized List<Task> viewTasks() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getStart))
                .collect(Collectors.toList());
    }

    public synchronized String markCompleted(String description) {
        Optional<Task> found = tasks.stream()
                .filter(x -> x.getDescription().equalsIgnoreCase(description.trim()))
                .findFirst();
        if (found.isPresent()) {
            found.get().setStatus(Task.Status.COMPLETED);
            found.get().setCompletedAt(java.time.LocalDateTime.now());
            ConsoleLogger.info("Task marked completed: " + description);
            return "Task marked as completed.";
        } else {
            return "ERROR: Task not found.";
        }
    }

    public synchronized List<Task> viewByPriority(Task.Priority p) {
        return tasks.stream()
                .filter(t -> t.getPriority() == p)
                .sorted(Comparator.comparing(Task::getStart))
                .collect(Collectors.toList());
    }
}

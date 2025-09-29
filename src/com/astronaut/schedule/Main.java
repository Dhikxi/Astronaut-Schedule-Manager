package com.astronaut.schedule;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ScheduleManager manager = ScheduleManager.getInstance();

    public static void main(String[] args) {
        // register simple observer
        manager.addObserver(new ConflictNotifier());

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Astronaut Daily Schedule Organizer (Enhanced) ===");

        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": handleAdd(sc); break;
                case "2": handleRemove(sc); break;
                case "3": handleView(); break;
                case "4": handleMarkCompleted(sc); break;
                case "5": handleViewByPriority(sc); break;
                case "6": running = false; System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Add Task (with Category)");
        System.out.println("2. Remove Task");
        System.out.println("3. View All Tasks (table)");
        System.out.println("4. Mark Task Completed");
        System.out.println("5. View Tasks by Priority");
        System.out.println("6. Exit");
        System.out.print("> ");
    }

    private static void handleAdd(Scanner sc) {
        try {
            System.out.print("Description: "); String desc = sc.nextLine();
            System.out.print("Start (HH:mm): "); String s = sc.nextLine();
            System.out.print("End (HH:mm): "); String e = sc.nextLine();
            System.out.print("Priority (Low/Medium/High): "); String p = sc.nextLine();

            System.out.println("Choose Category (type one): Life_Support, Research, Exercise, Communication, Other");
            System.out.print("Category: "); String c = sc.nextLine();

            Task t = TaskFactory.createTask(desc, s, e, p, c);
            String result = manager.addTask(t);
            System.out.println(result);
        } catch (IllegalArgumentException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
        }
    }

    private static void handleRemove(Scanner sc) {
        System.out.print("Enter description to remove: ");
        String desc = sc.nextLine();
        System.out.println(manager.removeTask(desc));
    }

    private static void handleView() {
        List<Task> tasks = manager.viewTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        printTableHeader();
        tasks.forEach(Main::printTaskRow);
        printTableFooter();
    }

    private static void handleMarkCompleted(Scanner sc) {
        System.out.print("Enter description to mark completed: ");
        String desc = sc.nextLine();
        System.out.println(manager.markCompleted(desc));
    }

    private static void handleViewByPriority(Scanner sc) {
        System.out.print("Priority (Low/Medium/High): ");
        String p = sc.nextLine().trim().toUpperCase();
        Task.Priority pr;
        try {
            pr = Task.Priority.valueOf(p);
        } catch (Exception ex) {
            System.out.println("Invalid priority.");
            return;
        }
        List<Task> tasks = manager.viewByPriority(pr);
        if (tasks.isEmpty()) {
            System.out.println("No tasks for priority: " + pr);
            return;
        }
        printTableHeader();
        tasks.forEach(Main::printTaskRow);
        printTableFooter();
    }

    // ----- Table printing helpers -----
    private static void printTableHeader() {
        String sep = "+--------+--------+----------------+------------------------------+----------+----------+---------------------+";
        System.out.println(sep);
        System.out.printf("| %-6s | %-6s | %-14s | %-28s | %-8s | %-8s | %-19s |%n",
                "Start", "End", "Category", "Description", "Priority", "Status", "CompletedAt");
        System.out.println(sep);
    }

    private static void printTaskRow(Task t) {
        System.out.printf("| %-6s | %-6s | %-14s | %-28s | %-8s | %-8s | %-19s |%n",
                t.getStart().toString(),
                t.getEnd().toString(),
                t.getCategory().toString(),
                shorten(t.getDescription(), 28),
                t.getPriority().toString(),
                t.getStatus().toString(),
                t.formattedCompletedAt());
    }

    private static void printTableFooter() {
        String sep = "+--------+--------+----------------+------------------------------+----------+----------+---------------------+";
        System.out.println(sep);
    }

    private static String shorten(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, max - 3) + "...";
    }
}

Astronaut Daily Schedule Organizer
Overview:

The Astronaut Daily Schedule Organizer is a console-based Java application that helps astronauts manage and organize their daily tasks efficiently. The application allows users to add, remove, view, and prioritize tasks, while ensuring no scheduling conflicts. It demonstrates Object-Oriented Programming (OOP) principles, design patterns, and clean coding practices.

Features
Core Functionality:

Add Task: Create a new task with:

Description

Start time (HH:mm)

End time (HH:mm)

Priority (Low, Medium, High)

Category (Life_Support, Research, Exercise, Communication, Other)

Automatically checks for overlapping tasks.

Remove Task: Delete a task by description.

View All Tasks: Display all tasks in a table format showing description, start/end times, priority, category, and completion status.

Mark Task Completed: Update a task’s status as completed.

View Tasks by Priority: Filter tasks by Low, Medium, or High priority.

Logging:

ConsoleLogger: Displays log messages in the console with timestamps.

FileLogger: Saves logs to logs/app.log for persistent tracking.

Logs include INFO, WARN, and ERROR messages for actions and exceptions.

Design & Best Practices:

Singleton Pattern: Ensures only one instance of the Task Manager exists.

Factory Pattern: Task objects are created via a Task Factory.

Observer Pattern: Alerts users of scheduling conflicts when adding tasks.


Error Handling:

Gracefully handles invalid inputs (time format, non-existent tasks).

Prevents overlapping tasks and provides clear warnings.

Screenshots:

The src/images/ folder contains screenshots demonstrating the application features:

Adding Tasks

Removing Tasks

Viewing All Tasks

Marking Tasks Completed

Viewing Tasks by Priority

UML Diagram of the project

Time Handling Demonstration

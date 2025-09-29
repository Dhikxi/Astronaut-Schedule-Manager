package com.astronaut.schedule;

public interface ITaskObserver {
    void onTaskAdded(Task t);
    void onTaskRemoved(String description);
}

package com.astronaut.schedule;

public class ConflictNotifier implements ITaskObserver {
    @Override
    public void onTaskAdded(Task t) {
        // ScheduleManager handles conflict detection; this observer can be extended later.
    }

    @Override
    public void onTaskRemoved(String description) {
        System.out.println("[Notifier] Task removed: " + description);
    }
}

package com.example.yupeng.todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskLab {
    private static TaskLab sTaskLab;
//    private static Task[] mTasks;
    // TODO 为什么mTasks不用static
    private List<Task> mTasks;

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab();
        }
        return sTaskLab;
    }

    private TaskLab() {
        mTasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Task crime = new Task();
            crime.setTitle("Task #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            mTasks.add(crime);
        }
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(UUID uuid) {
        for (Task task : mTasks) {
            if (task.getUUID().equals(uuid)) {
                return task;
            }
        }
        return null;
    }
}

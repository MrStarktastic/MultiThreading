package com.example.company.multithreading.task_activity;

import com.example.company.multithreading.counter_task.CounterThreadsTask;
import com.example.company.multithreading.counter_task.CounterTask;

public final class ThreadsActivity extends TaskActivity {

    @Override
    protected CounterTask getTask() {
        return new CounterThreadsTask();
    }
}

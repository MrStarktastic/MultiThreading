package com.example.company.multithreading.task_activity;

import com.example.company.multithreading.counter_task.CounterThreadsTask;
import com.example.company.multithreading.counter_task.BaseCounterTask;

public final class ThreadsActivity extends BaseTaskActivity {

    @Override
    protected BaseCounterTask getTask() {
        return new CounterThreadsTask();
    }
}

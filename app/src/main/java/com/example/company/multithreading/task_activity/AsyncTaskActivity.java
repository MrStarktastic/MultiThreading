package com.example.company.multithreading.task_activity;

import com.example.company.multithreading.counter_task.CounterAsyncTask;
import com.example.company.multithreading.counter_task.CounterTask;

public class AsyncTaskActivity extends TaskActivity {

    @Override
    protected CounterTask getTask() {
        return new CounterAsyncTask();
    }
}

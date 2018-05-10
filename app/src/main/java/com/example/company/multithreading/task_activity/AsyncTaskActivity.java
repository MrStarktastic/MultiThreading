package com.example.company.multithreading.task_activity;

import com.example.company.multithreading.counter_task.CounterAsyncTask;
import com.example.company.multithreading.counter_task.BaseCounterTask;

public final class AsyncTaskActivity extends BaseTaskActivity {

    @Override
    protected BaseCounterTask getTask() {
        return new CounterAsyncTask();
    }
}

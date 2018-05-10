package com.example.company.multithreading.task_activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.company.multithreading.R;
import com.example.company.multithreading.TaskFragment;
import com.example.company.multithreading.counter_task.CounterTask;

abstract class TaskActivity extends AppCompatActivity {
    private static final String TAG_TASK_FRAGMENT = "Task Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        final FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentByTag(TAG_TASK_FRAGMENT) == null) {
            final TaskFragment fragment = TaskFragment.newInstance(getTask());
            fm.beginTransaction().add(R.id.container_view, fragment, TAG_TASK_FRAGMENT).commit();
        }
    }

    abstract protected CounterTask getTask();
}

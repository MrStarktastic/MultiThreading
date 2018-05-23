package com.example.company.multithreading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CounterTaskActivity extends AppCompatActivity {
    public static final String EXTRA_COUNTER_TASK_CLASS = "CounterTask class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_task);
    }
}

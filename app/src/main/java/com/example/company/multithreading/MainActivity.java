package com.example.company.multithreading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.company.multithreading.counter_task.BaseCounterTask;
import com.example.company.multithreading.counter_task.CounterAsyncTask;
import com.example.company.multithreading.counter_task.CounterThreadsTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Convenience method for starting a new activity.
     */
    private void startActivity(Class<? extends BaseCounterTask> counterTaskClass) {
        final Intent intent = new Intent(this, CounterTaskActivity.class);
        startActivity(intent.putExtra(CounterTaskActivity.EXTRA_COUNTER_TASK_CLASS, counterTaskClass));
    }

    public void openAsyncTaskActivity(View view) {
        startActivity(CounterAsyncTask.class);
    }

    public void openThreadsActivity(View view) {
        startActivity(CounterThreadsTask.class);
    }
}

package com.example.company.multithreading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.company.multithreading.task_activity.AsyncTaskActivity;
import com.example.company.multithreading.task_activity.ThreadsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Convenience method for starting a new activity.
     *
     * @param activityClass The class of the activity to start.
     */
    private void startActivity(Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    public void openAsyncTaskActivity(View view) {
        startActivity(AsyncTaskActivity.class);
    }

    public void openThreadsActivity(View view) {
        startActivity(ThreadsActivity.class);
    }
}

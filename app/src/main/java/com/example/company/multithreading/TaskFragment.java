package com.example.company.multithreading;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.company.multithreading.counter_task.CounterTask;

public class TaskFragment extends Fragment implements View.OnClickListener, CounterTask.OnProgressUpdateListener {
    private static final String KEY_TASK_PROGRESS_STATE = "Task progress";
    private static final String KEY_BUTTON_ENABLED_STATES = "Button enabled";

    private Button createButton, startButton, cancelButton;
    private TextView taskProgressText;

    private CounterTask task;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(CounterTask task) {
        final TaskFragment fragment = new TaskFragment();
        fragment.task = task;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain this fragment on configuration change (e.q. orientation)
        task.setOnProgressUpdateListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        final boolean[] buttonStates = new boolean[]{
                createButton.isEnabled(),
                startButton.isEnabled(),
                cancelButton.isEnabled()
        };
        outState.putBooleanArray(KEY_BUTTON_ENABLED_STATES, buttonStates);
        outState.putCharSequence(KEY_TASK_PROGRESS_STATE, taskProgressText.getText());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_worker, container, false);

        (createButton = view.findViewById(R.id.create_button)).setOnClickListener(this);
        (startButton = view.findViewById(R.id.start_button)).setOnClickListener(this);
        (cancelButton = view.findViewById(R.id.cancel_button)).setOnClickListener(this);
        taskProgressText = view.findViewById(R.id.task_progress_text);

        if (savedInstanceState != null) { // Restore UI states after configuration change
            taskProgressText.setText(savedInstanceState.getCharSequence(KEY_TASK_PROGRESS_STATE));
            final boolean[] states = savedInstanceState.getBooleanArray(KEY_BUTTON_ENABLED_STATES);
            createButton.setEnabled(states[0]);
            startButton.setEnabled(states[1]);
            cancelButton.setEnabled(states[2]);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel();
    }

    private void toggleEnabled(View toDisable, View toEnable) {
        toDisable.setEnabled(false);
        toEnable.setEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_button:
                toggleEnabled(createButton, startButton);
                task.create();
                taskProgressText.setText(Integer.toString(0));
                break;

            case R.id.start_button:
                toggleEnabled(startButton, cancelButton);
                task.start();
                break;

            case R.id.cancel_button:
                toggleEnabled(cancelButton, createButton);
                task.cancel();
                taskProgressText.setText(R.string.default_task_progress);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressUpdate(int progress) {
        if (progress == CounterTask.MAX_PROGRESS) {
            toggleEnabled(cancelButton, createButton);
            taskProgressText.setText(R.string.default_task_progress);
            return;
        }

        taskProgressText.setText(Integer.toString(progress));
    }
}

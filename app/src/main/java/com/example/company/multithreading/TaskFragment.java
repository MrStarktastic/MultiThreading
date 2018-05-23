package com.example.company.multithreading;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.company.multithreading.counter_task.BaseCounterTask;

import static com.example.company.multithreading.CounterTaskActivity.EXTRA_COUNTER_TASK_CLASS;

public class TaskFragment extends Fragment implements View.OnClickListener, BaseCounterTask.OnProgressUpdateListener {
    private static final String KEY_TASK_PROGRESS_STATE = "Task progress";
    private static final String KEY_BUTTON_ENABLED_STATES = "Button enabled";

    private Button createButton, startButton, cancelButton;
    private TextView taskProgressText;

    private BaseCounterTask task;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Fetches the desired {@link BaseCounterTask} {@link Class} from the parent
     * {@link CounterTaskActivity}'s {@link Intent} and initializes an instance of it.
     */
    @SuppressWarnings("ConstantConditions")
    private void initTask() throws IllegalAccessException, java.lang.InstantiationException {
        final FragmentActivity activity = getActivity();
        final Class taskClass = (Class) activity.getIntent().getSerializableExtra(EXTRA_COUNTER_TASK_CLASS);
        task = (BaseCounterTask) taskClass.newInstance();
        task.setOnProgressUpdateListener(this);
        activity.setTitle(task.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain this fragment on configuration change (e.q. orientation)

        try {
            initTask();
        } catch (IllegalAccessException | java.lang.InstantiationException e) { // Shouldn't happen
            e.printStackTrace();
        }
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

    /**
     * Restores UI states after configuration change (because View is destroyed and recreated).
     *
     * @param savedInstanceState Bundle that contains the saved instance states of the UI components.
     */
    @SuppressWarnings("ConstantConditions")
    private void restoreSavedInstanceState(@NonNull Bundle savedInstanceState) {
        getActivity().setTitle(task.getName());
        taskProgressText.setText(savedInstanceState.getCharSequence(KEY_TASK_PROGRESS_STATE));
        final boolean[] buttonStates = savedInstanceState.getBooleanArray(KEY_BUTTON_ENABLED_STATES);
        createButton.setEnabled(buttonStates[0]);
        startButton.setEnabled(buttonStates[1]);
        cancelButton.setEnabled(buttonStates[2]);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task, container, false);

        (createButton = view.findViewById(R.id.create_button)).setOnClickListener(this);
        (startButton = view.findViewById(R.id.start_button)).setOnClickListener(this);
        (cancelButton = view.findViewById(R.id.cancel_button)).setOnClickListener(this);
        taskProgressText = view.findViewById(R.id.task_progress_text);

        if (savedInstanceState != null) restoreSavedInstanceState(savedInstanceState);

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
                toggleEnabled(v, startButton);
                task.create();
                taskProgressText.setText(Integer.toString(0));
                break;

            case R.id.start_button:
                toggleEnabled(v, cancelButton);
                task.start();
                break;

            case R.id.cancel_button:
                toggleEnabled(v, createButton);
                task.cancel();
                taskProgressText.setText(R.string.default_task_progress);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressUpdate(int progress) {
        if (progress == BaseCounterTask.MAX_PROGRESS) {
            toggleEnabled(cancelButton, createButton);
            taskProgressText.setText(R.string.done_task_progress);
            return;
        }

        taskProgressText.setText(Integer.toString(progress));
    }
}

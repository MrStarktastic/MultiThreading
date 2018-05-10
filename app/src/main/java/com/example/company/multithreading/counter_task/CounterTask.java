package com.example.company.multithreading.counter_task;

public abstract class CounterTask {
    public static final int MAX_PROGRESS = 10;
    static final long SLEEP_DURATION = 500;

    OnProgressUpdateListener listener;

    public abstract void create();

    public abstract void start();

    public abstract void cancel();

    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        this.listener = listener;
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(int progress);
    }
}

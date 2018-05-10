package com.example.company.multithreading.counter_task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public final class CounterThreadsTask extends CounterTask implements Runnable {
    private static final String HANDLER_PROGRESS_MSG_KEY = "message";
    private Handler handler;

    private Thread thread;

    @Override
    public void create() {
        thread = new Thread(this);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                listener.onProgressUpdate(msg.getData().getInt(HANDLER_PROGRESS_MSG_KEY));
            }
        };
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void cancel() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * Implementation of the {@link Runnable} interface.
     */
    @Override
    public void run() {
        for (int i = 1; i <= MAX_PROGRESS; ++i) {
            try {
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            final Message msg = handler.obtainMessage();
            final Bundle bundle = new Bundle();
            bundle.putInt(HANDLER_PROGRESS_MSG_KEY, i);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
}

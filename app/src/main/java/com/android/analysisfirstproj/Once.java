package com.android.analysisfirstproj;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mehseti on 25.11.2017.
 */

public class Once
{
    private AtomicBoolean done = new AtomicBoolean();

    public void run(Runnable task) {
        if (done.get()) return;
        if (done.compareAndSet(false, true)) {
            task.run();
        }
    }
}

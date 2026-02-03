package tech.provve.accounts.service;

import java.util.concurrent.TimeUnit;

public interface ScheduledTaskingService {

    /**
     * Schedule a task to be executed each given period.
     *
     * @param task     what to execute
     * @param timeUnit when to execute
     */
    void schedule(Runnable task, long delay, TimeUnit timeUnit);

}

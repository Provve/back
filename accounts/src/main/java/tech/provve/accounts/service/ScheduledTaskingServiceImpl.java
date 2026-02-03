package tech.provve.accounts.service;

import jakarta.inject.Singleton;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Singleton
public class ScheduledTaskingServiceImpl implements ScheduledTaskingService {

    private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(
            1,
            Thread.ofVirtual()
                  .factory()
    );

    @Override
    public void schedule(Runnable task, long delay, TimeUnit timeUnit) {
        executor.scheduleWithFixedDelay(task, 0L, 1L, timeUnit);
    }
}

package seeemilyplay.core.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;

/**
 * A singleton that provides easy access to a shared
 * <code>ExecutorService</code> and <code>ScheduledExecutorService</code>.
 *
 */
public final class ThreadPool {

	private static final long SHUTDOWN_WAIT_SECS = 5;

	private static ThreadPool instance = new ThreadPool();

	private final ExecutorService executor;
	private final ScheduledExecutorService scheduledExecutor;

	private volatile boolean isShutdown;

	private ThreadPool() {
		super();
		executor = createExecutor();
		scheduledExecutor = createScheduledExecutor();
	}

	private static ExecutorService createExecutor() {
		return Executors.newCachedThreadPool();
	}

	private static ScheduledExecutorService createScheduledExecutor() {
		return Executors.newScheduledThreadPool(2);
	}

	public synchronized static ThreadPool getInstance() {
		if (isNewInstanceRequired()) {
			instance = createThreadPool();
		}
		return instance;
	}

	private synchronized static boolean isNewInstanceRequired() {
		return (instance==null || instance.isShutdown());
	}

	private static ThreadPool createThreadPool() {
		return new ThreadPool();
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public ScheduledExecutorService getScheduledExecutor() {
		return scheduledExecutor;
	}

	public boolean isShutdown() {
		return isShutdown;
	}

	public void shutdown() {
		startShutdown();
		awaitShutdown();
		flagAsShutdown();
	}

	private void startShutdown() {
		startExecutorShutdown();
		startScheduledExecutorShutdown();
	}

	private void startExecutorShutdown() {
		executor.shutdown();
	}

	private void startScheduledExecutorShutdown() {
		scheduledExecutor.shutdown();
	}

	private void awaitShutdown() {
		ErrorHandler.getInstance().run(new AwaitShutdownTask());
	}

	private class AwaitShutdownTask implements ExceptionThrowingTask {

		public void run() throws InterruptedException {
			awaitExecutorShutdown();
			awaitScheduledExecutorShutdown();
		}
	}

	private void awaitExecutorShutdown() throws InterruptedException {
		executor.awaitTermination(SHUTDOWN_WAIT_SECS, TimeUnit.SECONDS);
	}

	private void awaitScheduledExecutorShutdown() throws InterruptedException {
		scheduledExecutor.awaitTermination(SHUTDOWN_WAIT_SECS, TimeUnit.SECONDS);
	}

	private void flagAsShutdown() {
		isShutdown = true;
	}
}

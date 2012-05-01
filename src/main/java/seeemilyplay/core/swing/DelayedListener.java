package seeemilyplay.core.swing;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.threads.ThreadPool;

/**
 * Listener intended as a base for others that require a delay
 * between firing and receiving a notification.
 */
public abstract class DelayedListener implements Listener {

	private ScheduledExecutorService scheduledExecutor;
	private Executor swingExecutor;

	private final long delay;
	private final Runnable delayedTask = new DelayedTask();

	private Future<?> delayedTaskFuture;

	public DelayedListener(long delay) {
		this.delay = delay;
		setup();
	}

	private void setup() {
		setupDefaultExecutor();
		setupDefaultSwingExecutor();
	}

	private void setupDefaultExecutor() {
		setExecutor(getDefaultExecutor());
	}

	private void setupDefaultSwingExecutor() {
		setSwingExecutor(getDefaultSwingExecutor());
	}

	private ScheduledExecutorService getDefaultExecutor() {
		return ThreadPool.getInstance().getScheduledExecutor();
	}

	private Executor getDefaultSwingExecutor() {
		return new SwingExecutor();
	}

	public void setExecutor(ScheduledExecutorService scheduledExecutor) {
		this.scheduledExecutor = scheduledExecutor;
	}


	public void setSwingExecutor(Executor swingExecutor) {
		this.swingExecutor = swingExecutor;
	}

	public synchronized final void onChange() {
		cancelAnyScheduledTask();
		scheduleTask();
	}

	private void cancelAnyScheduledTask() {
		if (delayedTaskFuture!=null) {
			delayedTaskFuture.cancel(false);
		}
	}

	private void scheduleTask() {
		delayedTaskFuture = scheduledExecutor.schedule(
				delayedTask,
				delay,
				TimeUnit.MILLISECONDS);
	}

	private class DelayedTask implements Runnable {

		public DelayedTask() {
			super();
		}

		public void run() {
			swingExecutor.execute(new Runnable() {
				 public void run() {
					 afterDelay();
				 }});
		}
	}

	protected abstract void afterDelay();
}

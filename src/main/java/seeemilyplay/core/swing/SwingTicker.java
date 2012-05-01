package seeemilyplay.core.swing;

import java.util.concurrent.ScheduledFuture;

/**
 * A <code>SwingTicker</code> is used to continuously
 * fire changes to any listeners on the Swing Thread.
 * The purpose of it is to make painting changes
 * scenes easier.
 */
public class SwingTicker extends PollingSwingModel {

	private final long tickInterval;

	private Runnable tickTask;
	private ScheduledFuture<?> tickFuture;

	public SwingTicker(long tickInterval) {
		super();
		this.tickInterval = tickInterval;
	}

	public void start() {
		checkNotStarted();
		createAndStartTickTask();
	}

	private void createAndStartTickTask() {
		createTickTask();
		startTickTask();
	}

	private void startTickTask() {
		tickFuture = start(
				tickTask,
				tickInterval);
	}

	private void checkNotStarted() {
		if (isStarted()) {
			throw new Error("start() already called");
		}
	}

	private void createTickTask() {
		tickTask = new TickTask();
	}

	private class TickTask implements Runnable {

		public TickTask() {
			super();
		}

		public void run() {
			executeOnSwingThread(new FiringTask());
		}

		private class FiringTask implements Runnable {

			public void run() {
				fireChange();
			}
		}
	}

	public void stop() {
		checkStarted();
		cancel();
	}

	private void checkStarted() {
		if (!isStarted()) {
			throw new Error("start() not called");
		}
	}

	public boolean isStarted() {
		return (tickFuture!=null);
	}

	private void cancel() {
		tickFuture.cancel(false);
	}

	public boolean isStopped() {
		return (isStarted() && tickFuture.isCancelled());
	}
}

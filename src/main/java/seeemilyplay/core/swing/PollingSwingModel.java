package seeemilyplay.core.swing;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import seeemilyplay.core.threads.ThreadPool;

/**
 * The purpose of this class is to act as a base of swing component
 * models that executes repeatable tasks off the Swing Thread.
 * It takes care of managing two <code>Executors</code>.  One is used
 * for running the polling tasks off the Swing Thread, the other is
 * used for running tasks on the Swing Thread.
 *
 */
public class PollingSwingModel extends SwingModel {

	private ScheduledExecutorService scheduledExecutor;
	private Executor swingExecutor;


	protected PollingSwingModel() {
		super();
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

	protected ScheduledFuture<?> start(Runnable pollingTask, long pollInterval) {
		return scheduledExecutor.scheduleAtFixedRate(
				pollingTask,
				0,
				pollInterval,
				TimeUnit.MILLISECONDS);
	}

	protected void executeOnSwingThread(Runnable command) {
		swingExecutor.execute(command);
	}
}

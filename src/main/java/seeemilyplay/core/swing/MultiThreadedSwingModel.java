package seeemilyplay.core.swing;

import java.util.concurrent.Executor;

import seeemilyplay.core.threads.ThreadPool;

/**
 * The purpose of this class is to act as a base for most of the standard
 * Swing model implementations whenever they use multiple threads.
 * It takes care of managing two <code>Executors</code>.  One is used
 * for running background tasks off the Swing Thread, the other is
 * used for running tasks on the Swing Thread.
 */
public class MultiThreadedSwingModel extends SwingModel {

	private Executor executor;
	private Executor swingExecutor;

	protected MultiThreadedSwingModel() {
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

	private Executor getDefaultExecutor() {
		return ThreadPool.getInstance().getExecutor();
	}

	private Executor getDefaultSwingExecutor() {
		return new SwingExecutor();
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public void setSwingExecutor(Executor swingExecutor) {
		this.swingExecutor = swingExecutor;
	}


	protected void executeOffSwingThread(Runnable command) {
		executor.execute(command);
	}

	protected void executeOnSwingThread(Runnable command) {
		swingExecutor.execute(command);
	}
}

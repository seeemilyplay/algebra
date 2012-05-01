package seeemilyplay.core.swing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;


public class PollingSwingModelTest extends TestCase {

	private PollingSwingModel model;
	private Runnable task;
	private volatile CountDownLatch latch;
	private volatile boolean isRunOnSwingThread;

	private ScheduledFuture<?> future;


	protected void setUp() throws Exception {
		super.setUp();
		model = new PollingSwingModel();
		task = new Task();
		latch = new CountDownLatch(1);
		isRunOnSwingThread = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		if (future!=null) {
			future.cancel(false);
		}
	}

	public void testExecutionOnSwingThread() {

		whenExecutedOnSwingThread();

		thenIsRunOnSwingThread();
	}

	public void testExecutionOffSwingThread() {

		whenExecutedOffSwingThread();

		thenIsRunOffSwingThread();
	}

	public void testSettingOwnSwingExecutor() {

		whenSetOwnSwingExecutor();

		whenExecutedOnSwingThread();

		thenIsRunOnSwingThread();
	}

	public void testSettingOwnExecutor() {

		whenSetOwnExecutor();

		whenExecutedOffSwingThread();

		thenIsRunOffSwingThread();
	}

	private void whenSetOwnSwingExecutor() {
		model.setSwingExecutor(new SwingExecutor());
	}

	private void whenSetOwnExecutor() {
		model.setExecutor(Executors.newScheduledThreadPool(1));
	}

	private class Task implements Runnable {

		public Task() {
			super();
		}

		public void run() {
			isRunOnSwingThread = SwingUtilities.isEventDispatchThread();
			latch.countDown();
		}
	}

	private void whenExecutedOnSwingThread() {
		model.executeOnSwingThread(task);
	}

	private void whenExecutedOffSwingThread() {
		 future = model.start(task, 1);
	}

	private void thenIsRunOnSwingThread() {
		awaitTask();
		assertTrue(isRunOnSwingThread);
	}

	private void thenIsRunOffSwingThread() {
		awaitTask();
		assertFalse(isRunOnSwingThread);
	}

	private void awaitTask() {
		try {
			assertTrue(latch.await(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			throw new Error(e);
		}
	}
}

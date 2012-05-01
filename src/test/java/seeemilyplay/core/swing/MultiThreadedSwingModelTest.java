package seeemilyplay.core.swing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;


public class MultiThreadedSwingModelTest extends TestCase {

	private MultiThreadedSwingModel model;
	private Runnable task;
	private volatile CountDownLatch latch;
	private volatile boolean isRunOnSwingThread;


	protected void setUp() throws Exception {
		model = new MultiThreadedSwingModel();
		task = new Task();
		latch = new CountDownLatch(1);
		isRunOnSwingThread = false;
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
		model.setExecutor(Executors.newFixedThreadPool(1));
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
		 model.executeOffSwingThread(task);
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

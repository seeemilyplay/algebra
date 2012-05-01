package seeemilyplay.core.swing;



import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import seeemilyplay.core.swing.SwingExecutor;

import junit.framework.TestCase;


public class SwingExecutorTest extends TestCase {

	private SwingExecutor executor;
	private Runnable task;
	private volatile CountDownLatch latch;
	private volatile boolean executedOnSwingThread;


	protected void setUp() throws Exception {
		super.setUp();
		executor = new SwingExecutor();
		task = new Task();
		latch = new CountDownLatch(1);
		executedOnSwingThread = false;
	}

	public void testTasksAreRanOnSwingThread() {
		whenTaskIsExecuted();

		thenTaskIsRanOnSwingThread();
	}

	private class Task implements Runnable {
		public void run() {
			executedOnSwingThread = SwingUtilities.isEventDispatchThread();
			latch.countDown();
		}
	}

	private void whenTaskIsExecuted() {
		executor.execute(task);
	}

	private void thenTaskIsRanOnSwingThread() {
		waitForTaskToRun();
		assertTrue(executedOnSwingThread);
	}

	private void waitForTaskToRun() {
		try {
			assertTrue(latch.await(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			fail();
		}
	}
}

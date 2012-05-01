package seeemilyplay.core.swing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import seeemilyplay.core.listeners.Listener;


public class DelayedListenerTest extends TestCase {

	private static final long DELAY = 100;

	private Listener listener;

	private long fireTime;
	private CountDownLatch latch;
	private volatile int executionCount;
	private volatile long executionTime;

	protected void setUp() throws Exception {
		listener = null;
		fireTime = 0;
		latch = new CountDownLatch(1);
		executionCount = 0;
		executionTime = 0;
	}


	public void testNotificationsAreFiredToListenerWithADelay() {

		givenADelayedListener();

		whenFired();

		thenItIsExecutedAfterDelay();
	}

	public void testNotificationsInQuickSuccessionAreJustCountedAsOne() {

		givenADelayedListener();

		whenFiredTwiceInQuickSuccession();

		thenItIsExecutedJustOnce();
	}

	private void givenADelayedListener() {
		listener = new TestListener();
	}

	private void whenFired() {
		fire();
	}

	private void whenFiredTwiceInQuickSuccession() {
		fire();
		fire();
	}

	private void fire() {
		fireTime = System.currentTimeMillis();
		listener.onChange();
	}

	private void thenItIsExecutedAfterDelay() {
		waitForExecution();
		checkExecutedOnceAfterDelay();
	}

	private void thenItIsExecutedJustOnce() {
		waitForExecution();
		waitForABit();
		checkExecutedOnceAfterDelay();
	}

	private void checkExecutedOnceAfterDelay() {
		checkExecutedOnce();
		checkExecutionWasDelayed();
	}

	private void waitForExecution() {
		try {
			assertTrue(latch.await(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void waitForABit() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void checkExecutedOnce() {
		assertEquals(1, executionCount);
	}

	private void checkExecutionWasDelayed() {
		long delay = executionTime - fireTime;
		assertTrue(delay>=DELAY);
	}

	private class TestListener extends DelayedListener {


		public TestListener() {
			super(DELAY);
		}

		protected void afterDelay() {
			executionCount++;
			executionTime = System.currentTimeMillis();
			latch.countDown();
		}
	}
}

package seeemilyplay.core.swing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.listeners.StrongRefListenerManager;


public class SwingTickerTest extends TestCase {

	private SwingTicker swingTicker;
	private Listener listener;
	private volatile CountDownLatch latch;
	private volatile boolean isFiredOnSwingThread;
	private Throwable t;

	protected void setUp() throws Exception {
		setUpTicker();
		setUpListener();
		setUpTickCounter();
		isFiredOnSwingThread = false;
		t = null;
	}

	private void setUpTicker() {
		swingTicker = new SwingTicker(1);
		swingTicker.setListenerManager(new StrongRefListenerManager());
	}

	private void setUpListener() {
		listener = new TestListener();
		swingTicker.addListener(listener);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		shutdownTicker();
	}

	protected void shutDown() throws Exception {

	}

	private void shutdownTicker() {
		if (swingTicker.isStarted() && !swingTicker.isStopped()) {
			swingTicker.stop();
		}
	}

	private class TestListener implements Listener {

		public TestListener() {
			super();
		}

		public void onChange() {
			isFiredOnSwingThread = SwingUtilities.isEventDispatchThread();
			latch.countDown();
		}
	}

	public void testStartingTickerFiresChangesOnSwingThread() {

		whenTickerIsStarted();

		thenChangeIsFiredOnSwingThread();
	}

	public void testStoppingTickerStopsChangesBeingFired() {

		givenStartedTicker();

		whenTickerIsStopped();

		thenChangesArentFired();
	}

	public void testTickerCantBeStartedTwice() {

		givenStartedTicker();

		whenTickerIsStarted();

		thenErrorIsThrown();
	}

	public void testTickerCantBeStoppedUnlessStarted() {

		whenTickerIsStopped();

		thenErrorIsThrown();
	}

	private void givenStartedTicker() {
		startTicker();
	}

	private void whenTickerIsStarted() {
		try {
			startTicker();
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void whenTickerIsStopped() {
		try {
			stopTicker();
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void startTicker() {
		swingTicker.start();
	}

	private void stopTicker() {
		swingTicker.stop();
	}

	private void thenErrorIsThrown() {
		assertTrue(t instanceof Error);
	}

	private void thenChangeIsFiredOnSwingThread() {
		awaitTicks();
		assertTrue(isFiredOnSwingThread);
	}

	private void thenChangesArentFired() {
		waitForUnwantedTicks();
	}

	private void waitForUnwantedTicks() {
		setUpTickCounter();
		awaitUnwantedTicks();
	}

	private void setUpTickCounter() {
		latch = new CountDownLatch(3);
	}

	private void awaitTicks() {
		try {
			assertTrue(latch.await(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			throw new Error(e);
		}
	}

	private void awaitUnwantedTicks() {
		try {
			assertFalse(latch.await(1, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			//expected
		}
	}
}
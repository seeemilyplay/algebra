package seeemilyplay.core.listeners;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;


public class SwingListenerManagerTest extends TestCase {

	private ListenerManager listenerManager;
	private Listener listener;
	private boolean isNotificationReceived;
	private Throwable t;

	protected void setUp() throws Exception {
		listenerManager = new SwingListenerManager(new StrongRefListenerManager());
		listener = null;
		isNotificationReceived = false;
		t = null;
	}

	public void testAddingListenerOnSwingThreadWorks() {

		givenListenerAddedOnSwingThread();

		whenManagerFiresOnSwingThread();

		thenListenerReceivesNotification();
	}

	public void testAddingListenerOffSwingThreadFails() {

		whenListenerAddedOffSwingThread();

		thenExceptionIsThrown();
	}

	public void testRemovingListenerOnSwingThreadWorks() {

		givenListenerAddedOnSwingThread();

		whenListenerIsRemovedOnSwingThread();
		andManagerFiresOnSwingThread();

		thenListenerDoesntReceiveNotification();
	}

	public void testRemovingListenerOffSwingThreadFails() {

		givenListenerAddedOnSwingThread();

		whenListenerIsRemovedOffSwingThread();

		thenExceptionIsThrown();
	}

	public void testFiringListenerOnSwingThreadWorks() {

		givenListenerAddedOnSwingThread();

		whenManagerFiresOnSwingThread();

		thenListenerReceivesNotification();
	}

	public void testFiringListenerOffSwingThreadFails() {

		givenListenerAddedOnSwingThread();

		whenManagerFiresOffSwingThread();

		thenExceptionIsThrown();
	}

	private void givenListenerAddedOnSwingThread() {
		createListener();
		addListenerOnSwingThread();
	}

	private void whenListenerAddedOffSwingThread() {
		createListener();
		addListenerOffSwingThread();
	}

	private void createListener() {
		listener = new TestListener();
	}

	private void addListenerOnSwingThread() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					addListener();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void addListenerOffSwingThread() {
		try {
			addListener();
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void addListener() {
		listenerManager.addListener(listener);
	}

	private void whenListenerIsRemovedOnSwingThread() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					removeListener();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void whenListenerIsRemovedOffSwingThread() {
		try {
			removeListener();
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void removeListener() {
		listenerManager.removeListener(listener);
	}

	private void whenManagerFiresOnSwingThread() {
		fireOnSwingThread();
	}

	private void andManagerFiresOnSwingThread() {
		fireOnSwingThread();
	}

	private void whenManagerFiresOffSwingThread() {
		fireOffSwingThread();
	}

	private void fireOnSwingThread() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					fire();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void fireOffSwingThread() {
		try {
			fire();
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void fire() {
		listenerManager.fireChange();
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}

	private void thenListenerReceivesNotification() {
		assertTrue(isNotificationReceived);
	}

	private void thenListenerDoesntReceiveNotification() {
		assertFalse(isNotificationReceived);
	}

	private class TestListener implements Listener {

		public TestListener() {
			super();
		}

		public void onChange() {
			isNotificationReceived = true;
		}
	}
}

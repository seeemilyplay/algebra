package seeemilyplay.core.listeners;

import junit.framework.TestCase;


public class StrongRefListenerManagerTest extends TestCase {

	private ListenerManager listenerManager;
	private Listener listener;
	private boolean isNotificationReceived;

	protected void setUp() throws Exception {
		listenerManager = new StrongRefListenerManager();
		listener = null;
		isNotificationReceived = false;
	}

	public void testAddedListenersReceiveNotifications() {

		givenAddedListener();

		whenManagerFiresChange();

		thenListenerReceivesNotification();
	}

	public void testRemovedListenersDontReceiveNotification() {

		givenAddedListener();

		whenListenerIsRemoved();
		andManagerFiresChange();

		thenListenerDoesntReceiveNotification();
	}

	private void givenAddedListener() {
		createListener();
		addListener();
	}

	private void createListener() {
		listener = new TestListener();
	}

	private void addListener() {
		listenerManager.addListener(listener);
	}

	private void whenListenerIsRemoved() {
		listenerManager.removeListener(listener);
	}

	private void whenManagerFiresChange() {
		fireChange();
	}

	private void andManagerFiresChange() {
		fireChange();
	}

	private void fireChange() {
		listenerManager.fireChange();
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

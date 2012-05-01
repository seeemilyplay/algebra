package seeemilyplay.core.swing;

import junit.framework.TestCase;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.listeners.StrongRefListenerManager;


public class SwingModelTest extends TestCase {

	private SwingModel swingModel;
	private Listener listener;
	private boolean isFired;

	protected void setUp() throws Exception {
		setUpSwingModel();
		setUpListener();
		isFired = false;
	}

	private void setUpSwingModel() {
		swingModel = new SwingModel();
		swingModel.setListenerManager(new StrongRefListenerManager());
	}

	private void setUpListener() {
		listener = new TestListener();
	}

	public void testAddedListenersAreFired() {

		givenAddedListener();

		whenChangeFired();

		thenListenerIsFired();
	}

	public void testRemovedListenersArentFired() {

		givenAddedListener();

		whenListenerRemoved();
		andChangeFired();

		thenListenerIsntFired();
	}

	private class TestListener implements Listener {

		public TestListener() {
			super();
		}

		public void onChange() {
			isFired = true;
		}
	}

	private void givenAddedListener() {
		swingModel.addListener(listener);
	}

	private void whenListenerRemoved() {
		swingModel.removeListener(listener);
	}

	private void andChangeFired() {
		whenChangeFired();
	}

	private void whenChangeFired() {
		swingModel.fireChange();
	}

	private void thenListenerIsFired() {
		assertTrue(isFired);
	}

	private void thenListenerIsntFired() {
		assertTrue(!isFired);
	}
}

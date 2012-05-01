package seeemilyplay.core.swing;

import javax.swing.event.DocumentListener;

import junit.framework.TestCase;


public class DocumentAdapterTest extends TestCase {

	private DocumentListener listener;
	private boolean onChangeCalled;


	protected void setUp() throws Exception {
		super.setUp();
		listener = null;
		onChangeCalled = false;
	}

	public void testChangeUpdateEventsAdapted() {

		givenDocumentAdapter();

		whenChangedUpdateCalled();

		thenOnChangeCalled();
	}

	public void testInsertUpdateEventsAdapted() {

		givenDocumentAdapter();

		whenInsertUpdateCalled();

		thenOnChangeCalled();
	}

	public void testRemoveUpdateEventsAdapted() {

		givenDocumentAdapter();

		whenRemoveUpdateCalled();

		thenOnChangeCalled();
	}

	private void givenDocumentAdapter() {
		listener = new TestDocumentAdapter();
	}

	private class TestDocumentAdapter extends DocumentAdapter {

		public TestDocumentAdapter() {
			super();
		}

		public void onChange() {
			onChangeCalled = true;
		}
	}

	private void whenChangedUpdateCalled() {
		listener.changedUpdate(null);
	}

	private void whenInsertUpdateCalled() {
		listener.insertUpdate(null);
	}

	private void whenRemoveUpdateCalled() {
		listener.removeUpdate(null);
	}

	private void thenOnChangeCalled() {
		assertTrue(onChangeCalled);
	}
}

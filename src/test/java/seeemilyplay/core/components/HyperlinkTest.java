package seeemilyplay.core.components;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;


public class HyperlinkTest extends TestCase {

	private String actionName;
	private Action action;
	private FrameFixture window;
	private boolean isRan;

	protected void setUp() throws Exception {
		super.setUp();
		this.actionName = null;
		this.action = null;
		this.window = null;
		this.isRan = false;
	}

	public void tearDown() throws Exception {
		super.tearDown();
		window.cleanUp();
	}

	public void testDisplaysErrorText() {

		givenActionCalled("some action name");

		whenHyperlinkCreated();

		thenActionNameIsDisplayed();
	}

	public void testClickingExecutesTask() {

		givenHyperlink("some action name");

		whenClicked();

		thenActionIsRan();
	}

	private void givenHyperlink(String actionName) {
		createAction(actionName);
		createHyperlink();
	}

	private void givenActionCalled(String actionName) {
		createAction(actionName);
	}

	private void createAction(String actionName) {
		setActionName(actionName);
		createAction();
	}

	private void setActionName(String actionName) {
		this.actionName = actionName;
	}

	private void createAction() {
		action = new TestAction();
	}

	@SuppressWarnings("serial")
	private class TestAction extends AbstractAction {

		public TestAction() {
			super(actionName);
		}

		public void actionPerformed(ActionEvent e) {
			isRan = true;
		}
	}

	private void whenHyperlinkCreated() {
		createHyperlink();
	}

	private void createHyperlink() {
		setUpWindow();
	}

	private void setUpWindow() {
		JFrame frame = setUpFrame();
		window = new FrameFixture(frame);
		window.show();
	}

	private JFrame setUpFrame() {
		return GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				Hyperlink hyperlink = new Hyperlink(action);
				return createFrame(hyperlink);
			}
		});
	}

	private JFrame createFrame(Hyperlink hyperlink) {
		JFrame frame = new JFrame();
		frame.add(hyperlink);
		frame.pack();
		return frame;
	}

	private void thenActionNameIsDisplayed() {
		assertEquals(actionName, getDisplayedText());
	}

	private String getDisplayedText() {
		return window.label().text();
	}

	private void whenClicked() {
		window.label().click();
	}

	private void thenActionIsRan() {
		assertTrue(isRan);
	}
}

package seeemilyplay.core.components;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;


public class ErrorLabelTest extends TestCase {

	private String text;
	private FrameFixture window;

	protected void setUp() throws Exception {
		super.setUp();
		this.text = null;
		this.window = null;
	}

	public void tearDown() throws Exception {
		super.tearDown();
		window.cleanUp();
	}

	public void testDisplaysErrorText() {

		givenText("some error text");

		whenErrorLabelCreated();

		thenErrorTextIsDisplayed();
	}

	private JFrame createFrame(ErrorLabel label) {
		JFrame frame = new JFrame();
		frame.add(label);
		frame.pack();
		return frame;
	}

	private void givenText(String text) {
		this.text = text;
	}

	private void whenErrorLabelCreated() {
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
				ErrorLabel errorLabel = new ErrorLabel(text);
				return createFrame(errorLabel);
			}
		});
	}

	private void thenErrorTextIsDisplayed() {
		assertEquals(text, getDisplayedText());
	}

	private String getDisplayedText() {
		return window.label().text();
	}
}

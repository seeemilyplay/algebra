package seeemilyplay.core.components;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;

public class OkButtonTest extends TestCase {

	private FrameFixture window;
	private volatile boolean isRan;

	protected void setUp() throws Exception {
		super.setUp();
		setUpWindow();
		isRan = false;
	}

	private void setUpWindow() {
		JFrame frame = setUpFrame();
		window = new FrameFixture(frame);
		window.show();
	}

	private JFrame setUpFrame() {
		return GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				Runnable task = createTask();
				OkButton okButton = createOkButton(task);
				return createFrame(okButton);
			}
		});
	}

	public void tearDown() throws Exception {
		super.tearDown();
		window.cleanUp();
	}

	public void testTaskRanWhenButtonClicked() {

		whenClicked();

		thenIsRan();
	}

	public void testTestRanWhenEnterPressed() {

		whenEnterPressed();

		thenIsRan();
	}

	private Runnable createTask() {
		return new Task();
	}

	private class Task implements Runnable {

		public void run() {
			isRan = true;
		}
	}

	private OkButton createOkButton(Runnable task) {
		return new OkButton(task);
	}

	private JFrame createFrame(OkButton okButton) {
		JFrame frame = new JFrame();
		frame.add(okButton);
		frame.pack();
		return frame;
	}

	private void whenClicked() {
		window.button().click();
	}

	private void whenEnterPressed() {
		window.pressKey(KeyEvent.VK_ENTER);
	}

	private void thenIsRan() {
		assertTrue(isRan);
	}
}

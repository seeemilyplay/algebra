package seeemilyplay.core.components;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;


public class ExitButtonTest extends TestCase {

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
				ExitButton exitButton = createExitButton(task);
				return createFrame(exitButton);
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

	public void testTestRanWhenEscapePressed() {

		whenEscapePressed();

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

	private ExitButton createExitButton(Runnable task) {
		return new ExitButton(task);
	}

	private JFrame createFrame(ExitButton exitButton) {
		JFrame frame = new JFrame();
		frame.add(exitButton);
		frame.pack();
		return frame;
	}

	private void whenClicked() {
		window.button().click();
	}

	private void whenEscapePressed() {
		window.pressAndReleaseKeys(KeyEvent.VK_ESCAPE);
	}

	private void thenIsRan() {
		assertTrue(isRan);
	}
}

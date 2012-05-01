package seeemilyplay.core.components;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;

public class BackButtonTest extends TestCase {

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
				BackButton backButton = createBackButton(task);
				return createFrame(backButton);
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

	public void testTestRanWhenAltLeftArrowPressed() {

		whenAltLeftArrowPressed();

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

	private BackButton createBackButton(Runnable task) {
		return new BackButton(task);
	}

	private JFrame createFrame(BackButton backButton) {
		JFrame frame = new JFrame();
		frame.add(backButton);
		frame.pack();
		return frame;
	}

	private void whenClicked() {
		window.button().click();
	}

	private void whenAltLeftArrowPressed() {
		window.pressKey(KeyEvent.VK_ALT);
		window.pressAndReleaseKeys(KeyEvent.VK_LEFT);
		window.releaseKey(KeyEvent.VK_ALT);
	}

	private void thenIsRan() {
		assertTrue(isRan);
	}
}

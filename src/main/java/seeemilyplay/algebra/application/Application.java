package seeemilyplay.algebra.application;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import seeemilyplay.algebra.lookandfeel.AlgebraLookAndFeel;

public final class Application {

	private Application() {
		super();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initLookAndFeel();
				ApplicationContext appContext = new ApplicationContext();
				ApplicationFrame appFrame = new ApplicationFrame(appContext);
				appFrame.display();
			}
		});
	}

	private static void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new AlgebraLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			throw new Error(e);
		}
	}
}

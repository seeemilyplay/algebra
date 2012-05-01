package seeemilyplay.algebra.application;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import seeemilyplay.algebra.application.text.ApplicationPhrases;
import seeemilyplay.core.international.text.PhraseBook;



@SuppressWarnings("serial")
final class ApplicationFrame extends JFrame {

	private final ApplicationContext applicationContext;

	public ApplicationFrame(ApplicationContext applicationContext) {
		super(getGameName());

		this.applicationContext = applicationContext;

		initLayout();
		layoutApplication();

		pack();
	}

	private static String getGameName() {
		ApplicationPhrases phrases = getPhrases();
		return phrases.getGameName();
	}

	private static ApplicationPhrases getPhrases() {
		return PhraseBook.getPhrases(ApplicationPhrases.class);
	}

	private void initLayout() {
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
	}

	private void layoutApplication() {
		add(createApplicationComponent());
	}

	private ApplicationComponent createApplicationComponent() {
		ApplicationModel applicationModel = createApplicationModel();
		return new ApplicationComponent(applicationModel);
	}

	private ApplicationModel createApplicationModel() {
		return new ApplicationModel(applicationContext);
	}

	public void display() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
				setExtendedState(MAXIMIZED_BOTH);
			}
		});
	}
}

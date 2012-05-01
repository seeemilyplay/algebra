package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import seeemilyplay.algebra.games.text.GamePhrases;
import seeemilyplay.algebra.games.text.GamePhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

@SuppressWarnings("serial")
final class TutorialButton extends JComponent {

	private final BaseGameModel baseGameModel;

	public TutorialButton(BaseGameModel baseGameModel) {
		this.baseGameModel = baseGameModel;

		installGamePhrases();
		initLayout();
		layoutAll();
	}

	private void installGamePhrases() {
		PhraseBook.registerPhrases(
				GamePhrases.class,
				new GamePhrasesFactory());
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void layoutAll() {
		add(createTutorialButton());
	}

	private JComponent createTutorialButton() {
		return new JButton(new TutorialAction());
	}

	private class TutorialAction extends AbstractAction {

		public TutorialAction() {
			super(getTutorialLabel());
		}

		public void actionPerformed(ActionEvent e) {
			startTutorial();
		}
	}

	private String getTutorialLabel() {
		return getGamePhrases().getTutorial();
	}

	private void startTutorial() {
		baseGameModel.setPlayingTutorial();
	}

	private GamePhrases getGamePhrases() {
		return PhraseBook.getPhrases(GamePhrases.class);
	}
}

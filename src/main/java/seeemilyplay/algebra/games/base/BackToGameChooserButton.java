package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import seeemilyplay.core.components.BackButton;

@SuppressWarnings("serial")
final class BackToGameChooserButton extends JComponent {

	private final BaseGameModel baseGameModel;

	public BackToGameChooserButton(BaseGameModel baseGameModel) {
		this.baseGameModel = baseGameModel;

		initLayout();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void layoutAll() {
		add(createBackButton());
	}

	private JComponent createBackButton() {
		return new BackButton(new BackAction());
	}

	private class BackAction implements Runnable {

		public BackAction() {
			super();
		}

		public void run() {
			finishGame();
		}
	}

	private void finishGame() {
		baseGameModel.setFinished();
	}
}

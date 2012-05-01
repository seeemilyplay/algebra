package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import seeemilyplay.core.components.BackButton;

@SuppressWarnings("serial")
final class BackToInstructionsButton extends JComponent {

	private final BaseGameModel baseGameModel;

	public BackToInstructionsButton(BaseGameModel baseGameModel) {
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
		return new BackButton(new ViewInstructionsAction());
	}

	private class ViewInstructionsAction implements Runnable {

		public ViewInstructionsAction() {
			super();
		}

		public void run() {
			viewInstructions();
		}
	}

	private void viewInstructions() {
		baseGameModel.setViewingInstructions();
	}
}

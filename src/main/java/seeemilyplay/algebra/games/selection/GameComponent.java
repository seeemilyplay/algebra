package seeemilyplay.algebra.games.selection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class GameComponent extends JComponent {

	private final GameModel gameModel;

	private Listener gameModelListener;

	public GameComponent(GameModel gameModel) {
		this.gameModel = gameModel;

		initLayout();
		initGameModelListener();
		layoutNode();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void initGameModelListener() {
		gameModelListener = new Listener() {
			public void onChange() {
				layoutNode();
			}
		};
		gameModel.addListener(gameModelListener);
	}

	private void layoutNode() {
		removeAll();

		add(createButton());

		validate();
		repaint();
	}

	public boolean isEnabled() {
		return gameModel.isUnlocked();
	}

	private JButton createButton() {
		JButton button = new JButton(
				new SelectGameAction());
		button.setEnabled(isEnabled());
		return button;
	}

	private class SelectGameAction extends AbstractAction {

		public SelectGameAction() {
			super(getText(), getIcon());
		}

		public void actionPerformed(ActionEvent e) {
			selectGame();
		}
	}

	private String getText() {
		GameId game = gameModel.getGameId();
		return game.getName();
	}

	private Icon getIcon() {
		if (gameModel.isAward()) {
			Award award = gameModel.getAward();
			return award.getSmallIcon();
		}
		return null;
	}

	private void selectGame() {
		gameModel.selectGame();
	}
}

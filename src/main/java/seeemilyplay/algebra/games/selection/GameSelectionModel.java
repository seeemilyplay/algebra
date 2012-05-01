package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.core.swing.SwingModel;

public final class GameSelectionModel extends SwingModel {

	private GameId selectedGame;

	public GameSelectionModel() {
		super();
	}

	public GameId getSelectedGame() {
		return selectedGame;
	}

	public void setSelectedGame(GameId selectedGame) {
		this.selectedGame = selectedGame;
		fireChange();
	}

	public void clearSelection() {
		this.selectedGame = null;
		fireChange();
	}

	public boolean isGameSelected() {
		return selectedGame!=null;
	}
}

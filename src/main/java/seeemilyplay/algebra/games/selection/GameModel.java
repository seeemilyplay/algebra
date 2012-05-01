package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class GameModel extends SwingModel {

	private final GameId gameId;
	private final GamesModel gamesModel;

	private Listener gamesModelListener;

	public GameModel(GameId gameId, GamesModel gamesModel) {
		this.gameId = gameId;
		this.gamesModel = gamesModel;

		initGamesModelListener();
	}

	private void initGamesModelListener() {
		gamesModelListener = new Listener() {
			public void onChange() {
				fireChange();
			}
		};
		gamesModel.addListener(gamesModelListener);
	}

	public GameId getGameId() {
		return gameId;
	}

	public Award getAward() {
		return gamesModel.getAward(gameId);
	}

	public boolean isAward() {
		return gamesModel.isAwarded(gameId);
	}

	public boolean isUnlocked() {
		return gamesModel.isUnlocked(gameId);
	}

	public void selectGame() {
		gamesModel.setSelectedGame(gameId);
	}
}

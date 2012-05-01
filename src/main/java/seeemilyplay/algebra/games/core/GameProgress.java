package seeemilyplay.algebra.games.core;

import seeemilyplay.algebra.progress.Level;
import seeemilyplay.algebra.progress.ProgressModel;
import seeemilyplay.core.swing.SwingModel;

public final class GameProgress extends SwingModel {

	private final GameId gameId;
	private final ProgressModel progressModel;

	public GameProgress(GameId gameId, ProgressModel progressModel) {
		this.gameId = gameId;
		this.progressModel = progressModel;
	}

	public void setLevel(Level level) {
		progressModel.setLevel(getId(), level);
		fireChange();
	}

	public Level getLevel() {
		return progressModel.getLevel(getId());
	}

	private String getId() {
		return gameId.getId();
	}
}

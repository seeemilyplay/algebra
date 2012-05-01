package seeemilyplay.algebra.games;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.games.core.GameProgress;
import seeemilyplay.algebra.games.selection.ApplicationModel;
import seeemilyplay.algebra.games.selection.GameSelectionModel;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.AwardModel;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.algebra.progress.ProgressModel;
import seeemilyplay.core.listeners.Listener;

final class ApplicationModelImpl implements ApplicationModel {

	private final AwardModel awardModel;
	private final ProgressModel progressModel;
	private final GameSelectionModel gameSelectionModel;


	public ApplicationModelImpl(
			AwardModel awardModel,
			ProgressModel progressModel,
			GameSelectionModel gameSelectionModel) {
		this.awardModel = awardModel;
		this.progressModel = progressModel;
		this.gameSelectionModel = gameSelectionModel;
	}

	public void setSelectedGame(GameId selectedGame) {
		gameSelectionModel.setSelectedGame(selectedGame);
	}

	public Level getLevel(GameId game) {
		GameProgress gameProgress = new GameProgress(game, progressModel);
		return gameProgress.getLevel();
	}

	public Award getAward(Level level) {
		return awardModel.getAward(level);
	}

	public void addListener(Listener listener) {
		progressModel.addListener(listener);
		gameSelectionModel.addListener(listener);
	}
}

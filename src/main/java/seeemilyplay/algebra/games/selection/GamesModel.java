package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.games.core.Dependencies;
import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.games.core.GamesMap;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class GamesModel extends SwingModel {

	private final ApplicationModel applicationModel;
	private final GamesMap gamesMap;

	private StatusManager<GameId> statusManager;

	private Listener applicationListener;

	public GamesModel(
			ApplicationModel applicationModel,
			GamesMap gamesMap) {

		this.applicationModel = applicationModel;
		this.gamesMap = gamesMap;

		initStatusManager();
		initApplicationListener();
	}

	private void initStatusManager() {
		this.statusManager = new StatusManager<GameId>(
				applicationModel,
				createStatusCalculator(),
				createUnlockNoticeFactory());
	}

	private StatusCalculator<GameId> createStatusCalculator() {
		return new GamesStatusCalculator(
				applicationModel,
				getDependencies());
	}
	
	private UnlockNoticeFactory<GameId> createUnlockNoticeFactory() {
		return new UnlockedGameNoticeFactory();
	}

	private Dependencies getDependencies() {
		return gamesMap.getDependencies();
	}

	private void initApplicationListener() {
		applicationListener = new Listener() {
			public void onChange() {
				onProgressChange();
			}
		};
		applicationModel.addListener(applicationListener);
		onProgressChange();
	}

	private void onProgressChange() {
		statusManager.clear();
		fireChange();
	}

	public GamesMap getGamesMap() {
		return gamesMap;
	}

	public boolean isUnlocked(GameId gameId) {
		return statusManager.isUnlocked(gameId);
	}

	public Level getLevel(GameId gameId) {
		return statusManager.getLevel(gameId);
	}

	public boolean isAwarded(GameId gameId) {
		return statusManager.isAwarded(gameId);
	}

	public Award getAward(GameId gameId) {
		return statusManager.getAward(gameId);
	}

	public void setSelectedGame(GameId selectedGame) {
		applicationModel.setSelectedGame(selectedGame);
	}
}

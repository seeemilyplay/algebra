package seeemilyplay.algebra.games;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.games.core.GamePlay;
import seeemilyplay.algebra.games.core.GamePlayModel;
import seeemilyplay.algebra.games.core.GameProgress;
import seeemilyplay.algebra.games.core.GamesRegister;
import seeemilyplay.algebra.games.core.PlayId;
import seeemilyplay.algebra.games.core.PlayResources;
import seeemilyplay.algebra.games.selection.GameSelectionModel;
import seeemilyplay.algebra.goals.GoalModelFactory;
import seeemilyplay.algebra.progress.ProgressContext;
import seeemilyplay.algebra.progress.ProgressModel;
import seeemilyplay.algebra.users.LogInModel;
import seeemilyplay.algebra.users.UserContext;
import seeemilyplay.algebra.users.UserId;
import seeemilyplay.core.listeners.Listener;

final class GamePlayComponentFactory {

	private final LogInModel logInModel;
	private final UserProgressModelFactory userProgressModelFactory;
	private final GoalModelFactory goalModelFactory;
	private final GameSelectionModel gameSelectionModel;
	private final GamesRegister gamesRegister;


	private Listener lastestGameEndListener;

	public GamePlayComponentFactory(
			UserContext userContext,
			ProgressContext progressContext,
			GameSelectionModel gameSelectionModel,
			GamesRegister gamesRegister) {
		this.logInModel = userContext.getLogInModel();
		this.userProgressModelFactory = new UserProgressModelFactory(
				logInModel,
				progressContext);
		this.goalModelFactory = new GoalModelFactory(
				progressContext.getAwardModel());
		this.gameSelectionModel = gameSelectionModel;
		this.gamesRegister = gamesRegister;
	}

	public JComponent createGamePlayComponent(Listener gameEndListener) {
		PlayResources playResources = createPlayResources(gameEndListener);
		return getGamePlay().play(playResources);
	}
	
	private PlayResources createPlayResources(Listener gameEndListener) {
		return new PlayResources(
				getPlayId(), 
				createGamePlayModel(gameEndListener), 
				getGameProgress(), 
				goalModelFactory);
	}
	
	private PlayId getPlayId() {
		return new PlayId(getSelectedGame(), getLoggedInUser());
	}
	
	private UserId getLoggedInUser() {
		return logInModel.getLoggedInUser();
	}

	private GameProgress getGameProgress() {
		return new GameProgress(
				getSelectedGame(),
				getUserProgressModel());
	}

	private GameId getSelectedGame() {
		return gameSelectionModel.getSelectedGame();
	}

	private ProgressModel getUserProgressModel() {
		return userProgressModelFactory.createUserProgressModel();
	}

	private GamePlayModel createGamePlayModel(Listener gameEndListener) {
		GamePlayModel gamePlayModel = new GamePlayModel();
		installGameEndListener(gamePlayModel, gameEndListener);
		return gamePlayModel;
	}

	private void installGameEndListener(
			GamePlayModel gamePlayModel,
			Listener gameEndListener) {
		lastestGameEndListener = new GameEndListenerAdapter(
				gamePlayModel,
				gameEndListener);
		gamePlayModel.addListener(lastestGameEndListener);
	}

	private class GameEndListenerAdapter implements Listener {

		private final GamePlayModel gamePlayModel;
		private final Listener gameEndListener;
		private boolean isEnded;

		public GameEndListenerAdapter(
				GamePlayModel gamePlayModel,
				Listener gameEndListener) {
			this.gamePlayModel = gamePlayModel;
			this.gameEndListener = gameEndListener;
		}

		public void onChange() {
			if (isJustEnded()) {
				onEnd();
			}
		}

		private boolean isJustEnded() {
			return (!isEndedBefore() && isGameComplete());
		}

		private boolean isEndedBefore() {
			return isEnded;
		}

		private boolean isGameComplete() {
			return gamePlayModel.isComplete();
		}

		private void onEnd() {
			flagAsEnded();
			clearGameSelection();
			fireGameEndListener();
		}

		private void flagAsEnded() {
			isEnded = true;
		}

		private void clearGameSelection() {
			gameSelectionModel.clearSelection();
		}

		private void fireGameEndListener() {
			gameEndListener.onChange();
		}
	}

	private GamePlay getGamePlay() {
		return gamesRegister.getGamePlay(getSelectedGame());
	}
}

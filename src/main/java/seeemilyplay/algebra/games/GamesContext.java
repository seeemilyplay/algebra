package seeemilyplay.algebra.games;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.core.Categories;
import seeemilyplay.algebra.games.core.GamePlay;
import seeemilyplay.algebra.games.core.GameSettings;
import seeemilyplay.algebra.games.core.GamesRegister;
import seeemilyplay.algebra.games.selection.ApplicationModel;
import seeemilyplay.algebra.games.selection.GameChooserComponent;
import seeemilyplay.algebra.games.selection.GameChooserModel;
import seeemilyplay.algebra.games.selection.GameSelectionModel;
import seeemilyplay.algebra.progress.ProgressContext;
import seeemilyplay.algebra.users.UserContext;
import seeemilyplay.core.listeners.Listener;

public final class GamesContext {

	private final UserContext userContext;
	private final ProgressContext progressContext;
	private final GameSelectionModel gameSelectionModel;
	private final GamesRegister gamesRegister;

	private ApplicationModelFactory applicationModelFactory;
	private GamePlayComponentFactory gamePlayComponentFactory;

	public GamesContext(
			UserContext userContext,
			ProgressContext progressContext) {
		this.userContext = userContext;
		this.progressContext = progressContext;
		this.gameSelectionModel = new GameSelectionModel();
		this.gamesRegister = new GamesRegister();

		initApplicationModelFactory();
		initGamePlayComponentFactory();
	}

	private void initApplicationModelFactory() {
		applicationModelFactory = new ApplicationModelFactory(
				userContext,
				progressContext,
				gameSelectionModel);
	}

	private void initGamePlayComponentFactory() {
		gamePlayComponentFactory = new GamePlayComponentFactory(
				userContext,
				progressContext,
				gameSelectionModel,
				gamesRegister);
	}

	public void registerGame(GameSettings gameSettings, GamePlay gamePlay) {
		gamesRegister.registerGame(gameSettings, gamePlay);
	}

	public JComponent createGameChooserComponent() {
		GameChooserModel gameChooserModel = createGameChooserModel();
		return new GameChooserComponent(gameChooserModel);
	}

	private GameChooserModel createGameChooserModel() {
		return new GameChooserModel(
				createApplicationModel(),
				createCategories());
	}

	private ApplicationModel createApplicationModel() {
		return applicationModelFactory.createApplicationModel();
	}

	private Categories createCategories() {
		return gamesRegister.createCategories();
	}

	public JComponent createGamePlayComponent(
			Listener gameEndListener) {

		return gamePlayComponentFactory.createGamePlayComponent(
				gameEndListener);
	}

	public GameSelectionModel getGameSelectionModel() {
		return gameSelectionModel;
	}
}

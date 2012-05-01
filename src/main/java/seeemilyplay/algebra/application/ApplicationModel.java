package seeemilyplay.algebra.application;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.GamesContext;
import seeemilyplay.algebra.games.selection.GameSelectionModel;
import seeemilyplay.algebra.users.LogInModel;
import seeemilyplay.algebra.users.UserContext;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class ApplicationModel extends SwingModel {

	private final ApplicationContext applicationContext;

	private Listener contextListener;
	private Listener gameEndListener;

	private JComponent gameChooserComponent;

	public ApplicationModel(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;

		initContextListener();
		initGameEndListener();
	}

	private void initContextListener() {
		contextListener = new Listener() {
			public void onChange() {
				fireChange();
			}
		};
		getLogInModel().addListener(contextListener);
		getGameSelectionModel().addListener(contextListener);
	}

	private void initGameEndListener() {
		gameEndListener = new Listener() {
			public void onChange() {
				fireChange();
			}
		};
	}

	public boolean isLoggingIn() {
		return !getLogInModel().isLoggedIn();
	}

	public JComponent createLoggingInComponent() {
		return getUserContext().createLoggingInComponent();
	}

	private LogInModel getLogInModel() {
		return getUserContext().getLogInModel();
	}

	private UserContext getUserContext() {
		return applicationContext.getUserContext();
	}

	public boolean isChoosingGame() {
		return !getGameSelectionModel().isGameSelected();
	}

	public JComponent getGameChooserComponent() {
		createGameChooserComponentIfRequired();
		return gameChooserComponent;
	}

	private void createGameChooserComponentIfRequired() {
		if (gameChooserComponent==null) {
			createGameChooserComponent();
		}
	}

	private void createGameChooserComponent() {
		gameChooserComponent =
			getGamesContext().createGameChooserComponent();
	}

	public JComponent createGamePlayComponent() {
		return getGamesContext().createGamePlayComponent(
				gameEndListener);
	}

	private GameSelectionModel getGameSelectionModel() {
		return getGamesContext().getGameSelectionModel();
	}

	private GamesContext getGamesContext() {
		return applicationContext.getGamesContext();
	}

	public void shutdown() {
		applicationContext.shutdown();
	}
}

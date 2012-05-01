package seeemilyplay.algebra.games.base;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.core.GamePlayModel;
import seeemilyplay.algebra.games.core.GameProgress;
import seeemilyplay.algebra.games.core.PlayId;
import seeemilyplay.algebra.games.core.PlayResources;
import seeemilyplay.algebra.goals.GoalConfig;
import seeemilyplay.algebra.goals.GoalModel;
import seeemilyplay.algebra.goals.GoalModelFactory;
import seeemilyplay.core.swing.SwingModel;
import seeemilyplay.quizzer.Quizzer;

public final class BaseGameModel extends SwingModel {

	private final PlayResources playResources;
	private final BaseGameComponentsFactory componentsFactory;

	@SuppressWarnings("unused")
	private GameProgressUpdator updator;

	private State state = State.VIEWING_INSTRUCTIONS;

	public BaseGameModel(
			PlayResources playResources,
			BaseGameComponentsFactory componentsFactory) {
		this.playResources = playResources;
		this.componentsFactory = componentsFactory;
	}
	
	public PlayId getPlayId() {
		return playResources.getPlayId();
	}

	public GameProgress getGameProgress() {
		return playResources.getGameProgress();
	}

	public GoalModel createGoalModel(
			GoalConfig config,
			Quizzer<?> quizzer) {
		GoalModelFactory factory = getGoalModelFactory();
		GoalModel goalModel = factory.createGoalModel(config, quizzer);
		installGameProgressUpdator(goalModel);
		return goalModel;
	}

	private GoalModelFactory getGoalModelFactory() {
		return playResources.getGoalModelFactory();
	}

	private void installGameProgressUpdator(GoalModel goalModel) {
		updator = new GameProgressUpdator(
				goalModel,
				getGameProgress());
	}

	public void setViewingInstructions() {
		state = State.VIEWING_INSTRUCTIONS;
		fireChange();
	}

	public boolean isViewingInstructions() {
		return State.VIEWING_INSTRUCTIONS.equals(state);
	}

	public void setPlayingTutorial() {
		state = State.PLAYING_TUTORIAL;
		fireChange();
	}

	public boolean isPlayingTutorial() {
		return State.PLAYING_TUTORIAL.equals(state);
	}

	public void setPlayingGame() {
		state = State.PLAYING_GAME;
		fireChange();
	}

	public boolean isPlayingGame() {
		return State.PLAYING_GAME.equals(state);
	}

	public void setFinished() {
		getGamePlayModel().complete();
	}
	
	private GamePlayModel getGamePlayModel() {
		return playResources.getGamePlayModel();
	}

	public JComponent getComponent() {
		switch (state) {
			case VIEWING_INSTRUCTIONS: {
				return createInstructionsComponent();
			} case PLAYING_TUTORIAL: {
				return createTutorialComponent();
			} default: {
				return createPlayComponent();
			}
		}
	}

	private JComponent createInstructionsComponent() {
		return componentsFactory.createInstructionsComponent(this);
	}

	private JComponent createTutorialComponent() {
		return componentsFactory.createTutorialComponent(this);
	}

	private JComponent createPlayComponent() {
		return componentsFactory.createPlayComponent(this);
	}

	private static enum State {

		VIEWING_INSTRUCTIONS,
		PLAYING_TUTORIAL,
		PLAYING_GAME
	}
}

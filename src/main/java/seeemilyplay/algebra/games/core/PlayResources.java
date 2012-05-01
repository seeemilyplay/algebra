package seeemilyplay.algebra.games.core;

import seeemilyplay.algebra.goals.GoalModelFactory;

public final class PlayResources {

	private PlayId playId;
	private GamePlayModel gamePlayModel;
	private GameProgress gameProgress; 
	private GoalModelFactory goalModelFactory;
	
	public PlayResources(
			PlayId playId,
			GamePlayModel gamePlayModel,
			GameProgress gameProgress,
			GoalModelFactory goalModelFactory) {
		this.playId = playId;
		this.gamePlayModel = gamePlayModel;
		this.gameProgress = gameProgress;
		this.goalModelFactory = goalModelFactory;
	}
	
	public PlayId getPlayId() {
		return playId;
	}
	
	public GamePlayModel getGamePlayModel() {
		return gamePlayModel;
	}
	
	public GameProgress getGameProgress() {
		return gameProgress;
	}
	
	public GoalModelFactory getGoalModelFactory() {
		return goalModelFactory;
	}
}

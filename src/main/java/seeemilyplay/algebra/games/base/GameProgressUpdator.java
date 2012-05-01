package seeemilyplay.algebra.games.base;

import seeemilyplay.algebra.bulletin.BulletinBoard;
import seeemilyplay.algebra.bulletin.Notice;
import seeemilyplay.algebra.bulletin.notices.AwardNotice;
import seeemilyplay.algebra.games.core.GameProgress;
import seeemilyplay.algebra.goals.Goal;
import seeemilyplay.algebra.goals.GoalModel;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.core.listeners.Listener;

public final class GameProgressUpdator {

	private final GoalModel goalModel;
	private final GameProgress gameProgress;

	private Listener goalModelListener;

	public GameProgressUpdator(
			GoalModel goalModel,
			GameProgress gameProgress) {
		this.goalModel = goalModel;
		this.gameProgress = gameProgress;

		initGoalModelListener();
	}

	private void initGoalModelListener() {
		goalModelListener = new Listener() {
			public void onChange() {
				updateGameProgressIfRequired();
			}
		};
		goalModel.addListener(goalModelListener);
		updateGameProgressIfRequired();
	}

	private void updateGameProgressIfRequired() {
		if (isReachedGoal()) {
			updateGameProgress();
		}
	}

	private void updateGameProgress() {
		postAwardNoticeIfRequired();
		Level level = getReachedLevel();
		setProgressLevel(level);
	}
	
	private void postAwardNoticeIfRequired() {
		if (isHighestReachedLevel()) {
			postAwardNotice();
		}
	}
	
	private boolean isHighestReachedLevel() {
		return (getProgressLevel().compareTo(getReachedLevel())<0);
	}
	
	private void postAwardNotice() {
		Notice notice = createAwardNotice();
		getBulletinBoard().post(notice);
	}
	
	private Notice createAwardNotice() {
		return new AwardNotice(
				getReachedAward(),
				getTargetAwardOrNull());
	}
	
	public BulletinBoard getBulletinBoard() {
		return BulletinBoard.getInstance();
	}
	
	private Award getTargetAwardOrNull() {
		return (isTargetGoal() ? getTargetAward() : null);
	}
	
	private boolean isTargetGoal() {
		return goalModel.isTargetGoal();
	}

	private Award getTargetAward() {
		return getTargetGoal().getAward();
	}
	
	private Goal getTargetGoal() {
		return goalModel.getTargetGoal();
	}
	
	private boolean isReachedGoal() {
		return goalModel.isReachedGoal();
	}
	
	private Award getReachedAward() {
		return getReachedGoal().getAward();
	}

	private Level getReachedLevel() {
		return getReachedGoal().getLevel();
	}
	
	private Goal getReachedGoal() {
		return goalModel.getReachedGoal();
	} 

	private void setProgressLevel(Level level) {
		gameProgress.setLevel(level);
	}
	
	private Level getProgressLevel() {
		return gameProgress.getLevel();
	}
}

package seeemilyplay.algebra.goals;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;
import seeemilyplay.quizzer.StatisticsModel;
import seeemilyplay.quizzer.core.Probability;

final class GoalModelImpl extends SwingModel implements GoalModel {

	private final Goals goals;
	private final StatisticsModel<?> statisticsModel;

	private Listener listener;

	private Goal reachedGoal;
	private Goal targetGoal;

	public GoalModelImpl(
			Goals goals,
			StatisticsModel<?> statisticsModel) {
		this.goals = goals;
		this.statisticsModel = statisticsModel;

		initStatisticsModelListener();
		calculateGoals();
	}

	private void initStatisticsModelListener() {
		listener = new Listener() {
			public void onChange() {
				calculateGoals();
			}
		};
		statisticsModel.addListener(listener);
	}

	private void calculateGoals() {
		clearGoals();

		for (Goal goal : goals) {
			if (isReached(goal)) {
				reachedGoal = goal;
			} else {
				targetGoal = goal;
				break;
			}
		}
		fireChange();
	}

	private void clearGoals() {
		reachedGoal = null;
		targetGoal = null;
	}

	private boolean isReached(Goal goal) {
		return (getProgress()>=goal.getProgress());
	}

	public double getProgress() {
		return getOverallProbability();
	}

	private double getOverallProbability() {
		Probability probability = statisticsModel.getOverallProbability();
		return probability!=null ? probability.getValue() : 0;
	}

	public Goals getGoals() {
		return goals;
	}

	public boolean isReachedGoal() {
		return reachedGoal!=null;
	}

	public Goal getReachedGoal() {
		return reachedGoal;
	}
	
	public boolean isTargetGoal() {
		return targetGoal!=null;
	}
	
	public Goal getTargetGoal() {
		return targetGoal;
	}
}

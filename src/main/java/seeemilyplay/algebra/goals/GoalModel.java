package seeemilyplay.algebra.goals;

import seeemilyplay.core.listeners.Listener;


public interface GoalModel {

	public double getProgress();

	public Goals getGoals();

	public boolean isReachedGoal();

	public Goal getReachedGoal();
	
	public boolean isTargetGoal();
	
	public Goal getTargetGoal();

	public void addListener(Listener listener);
}

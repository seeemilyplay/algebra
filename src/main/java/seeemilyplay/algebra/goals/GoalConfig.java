package seeemilyplay.algebra.goals;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.progress.Level;

public final class GoalConfig {

	private static final double NOVICE_GOAL = 0.3;
	private static final double INTERMEDIATE_GOAL = 0.6;
	private static final double EXPERT_GOAL = 0.9;

	private final Map<Level,Double> goals = new HashMap<Level,Double>();

	public GoalConfig() {
		super();

		init();
	}

	private void init() {
		goals.put(Level.NOVICE, NOVICE_GOAL);
		goals.put(Level.INTERMEDIATE, INTERMEDIATE_GOAL);
		goals.put(Level.EXPERT, EXPERT_GOAL);
	}

	Map<Level,Double> getGoals() {
		return goals;
	}

	public void setGoal(Level level, double progress) {
		goals.put(level, progress);
	}

	public void removeGoal(Level level) {
		goals.remove(level);
	}
}

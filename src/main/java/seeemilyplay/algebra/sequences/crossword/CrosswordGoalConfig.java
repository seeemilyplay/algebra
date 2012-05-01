package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.algebra.goals.GoalConfig;

final class CrosswordGoalConfig {

	private CrosswordGoalConfig() {
		super();
	}

	public static GoalConfig getInstance() {
		GoalConfig goalConfig = new GoalConfig();
		return goalConfig;
	}
}

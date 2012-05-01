package seeemilyplay.algebra.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.AwardModel;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.StatisticsModel;

public final class GoalModelFactory {

	private final AwardModel awardModel;

	private final List<Goal> goals = new ArrayList<Goal>();
	private GoalConfig config;
	private Quizzer<?> quizzer;


	public GoalModelFactory(AwardModel awardModel) {
		this.awardModel = awardModel;
	}

	public GoalModel createGoalModel(
			GoalConfig config,
			Quizzer<?> quizzer) {
		init(config, quizzer);

		return new GoalModelImpl(
				createGoals(),
				getStatisticsModel());
	}

	private void init(
			GoalConfig config,
			Quizzer<?> quizzer) {
		this.config = config;
		this.quizzer = quizzer;
		this.goals.clear();
	}

	private StatisticsModel<?> getStatisticsModel() {
		return quizzer.getStatisticsModel();
	}

	private Goals createGoals() {
		saveGoals();
		return new Goals(goals);
	}

	private void saveGoals() {
		for (Map.Entry<Level,Double> entry : config.getGoals().entrySet()) {
			Level level = entry.getKey();
			double progress = entry.getValue();
			saveGoal(level, progress);
		}
	}

	private void saveGoal(Level level, double progress) {
		Goal goal = createGoal(level, progress);
		goals.add(goal);
	}

	private Goal createGoal(Level level, double progress) {
		Award award = awardModel.getAward(level);
		return new Goal(level, award, progress);
	}
}

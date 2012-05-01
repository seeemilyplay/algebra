package seeemilyplay.quizzer;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.network.Graph;

final class ProbabilityUpdater<Q extends Question> {

	private final QuizzerConfig config;

	private Graph<Q> graph;

	public ProbabilityUpdater(QuizzerConfig config) {
		super();
		this.config = config;
	}

	public void onCorrectAnswerFor(QuestionInfo<Q> questionInfo) {
		initialise(questionInfo);
		adjustProbabilityBy(getAward());
	}

	private double getAward() {
		return config.getAward();
	}

	public void onIncorrectAnswerFor(QuestionInfo<Q> questionInfo) {
		initialise(questionInfo);
		adjustProbabilityBy(getPenalty());
	}

	private double getPenalty() {
		return config.getPenalty() * -1;
	}

	private void initialise(QuestionInfo<Q> questionInfo) {
		graph = questionInfo.getGraph();
	}

	private void adjustProbabilityBy(double delta) {
		Probability adjustedProbability = getAdjustedProbability(delta);
		setProbability(adjustedProbability);
	}

	private Probability getAdjustedProbability(double delta) {
		return Probability.createProbability(
				getOriginalProbability().getValue() + delta);
	}

	private Probability getOriginalProbability() {
		return graph.getProbability();
	}

	private void setProbability(Probability probability) {
		graph.setProbabilityAndRipple(probability);
	}
}

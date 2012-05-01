package seeemilyplay.quizzer;

import seeemilyplay.core.swing.SwingModel;
import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.network.Graph;
import seeemilyplay.quizzer.network.Network;

final class StatisticsModelImpl<Q extends Question> extends SwingModel implements StatisticsModel<Q> {

	private final Network<Q> network;

	private int questionCount;
	private Probability overallProbability;

	public StatisticsModelImpl(Network<Q> network) {
		this.network = network;
		countQuestions();
		recalculate();
	}

	public void recalculate() {
		workoutOverallProbability();
		fireChange();
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public Probability getOverallProbability() {
		return overallProbability;
	}

	private void countQuestions() {
		questionCount = 0;
		for (Graph<Q> graph : network) {
			questionCount += countQuestionsForGraph(graph);
		}
	}

	private int countQuestionsForGraph(Graph<Q> graph) {
		return graph.getQuestionSpace().getQuestionCount();
	}

	private void workoutOverallProbability() {

		double sum = sumProbabilities();
		overallProbability = Probability.createProbability(
				sum / questionCount);
	}

	private double sumProbabilities() {
		double sum = 0;
		System.out.println("**");
		for (Graph<Q> graph : network) {
			System.out.println(graph.getId() + " " + graph.getProbability().getValue());
			sum += sumProbabilitiesForGraph(graph);
		}

		System.out.println("**");
		return sum;
	}

	private double sumProbabilitiesForGraph(Graph<Q> graph) {
		return countQuestionsForGraph(graph) * graph.getProbability().getValue();
	}
}

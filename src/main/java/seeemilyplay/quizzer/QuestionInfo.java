package seeemilyplay.quizzer;

import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.network.Graph;

final class QuestionInfo<Q extends Question> {

	private final Graph<Q> graph;
	private final Q question;

	public QuestionInfo(
			Graph<Q> graph,
			Q question) {
		this.graph = graph;
		this.question = question;
	}

	public Graph<Q> getGraph() {
		return graph;
	}

	public Probability getProbability() {
		return graph.getProbability();
	}

	public Q getQuestion() {
		return question;
	}
}

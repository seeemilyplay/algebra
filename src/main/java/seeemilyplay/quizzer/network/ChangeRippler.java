package seeemilyplay.quizzer.network;

import java.util.HashSet;
import java.util.Set;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;

final class ChangeRippler<Q extends Question> {

	private static final double ACCURACY = 0.0001;

	private final QuizzerConfig config;

	private final Set<Graph<Q>> donePile = new HashSet<Graph<Q>>();

	private Graph<Q> graph;
	private Probability probability;

	public ChangeRippler(QuizzerConfig config) {
		this.config = config;
	}

	public void setProbability(Graph<Q> graph, Probability probability) {
		initialise(graph, probability);
		makeChanges();
	}

	private void initialise(Graph<Q> graph, Probability probability) {
		this.graph = graph;
		this.probability = probability;

		initialiseDonePile();
	}

	private void initialiseDonePile() {
		donePile.clear();
	}

	private void markAsChanged(Graph<Q> graph) {
		donePile.add(graph);
	}

	private boolean isChanged(Graph<Q> graph) {
		return donePile.contains(graph);
	}

	private Graph<Q> getFirstGraph() {
		return graph;
	}

	private double getFirstChange() {
		Probability originalProbability = graph.getProbability();
		return probability.getValue() - originalProbability.getValue();
	}

	private void makeChanges() {
		change(getFirstGraph(), getFirstChange());
	}

	private void change(Graph<Q> graph, double change) {
		if (isChangeRequired(change) && !isChanged(graph)) {
			markAsChanged(graph);
			changeGraph(graph, change);
			changeParents(graph, calculateChangeForParents(change));
			changeChildren(graph, calculateChangeForChildren(change));
		}
	}

	private boolean isChangeRequired(double change) {
		return (Math.abs(change)>ACCURACY);
	}

	private void changeGraph(Graph<Q> graph, double change) {
		Probability originalProbability = graph.getProbability();
		Probability newProbability = Probability.createProbability(
				originalProbability.getValue() + change);
		graph.setProbability(newProbability);
	}

	private double calculateChangeForParents(double change) {
		return decayChange(change, getDecayForParentChange());
	}

	private double getDecayForParentChange() {
		return config.getUpRippleDecay();
	}

	private void changeParents(Graph<Q> graph, double change) {
		for (Graph<Q> parent : graph.getParents()) {
			change(parent, change);
		}
	}

	private double calculateChangeForChildren(double change) {
		return decayChange(change, getDecayForChildChange());
	}

	private double getDecayForChildChange() {
		return config.getDownRippleDecay();
	}

	private void changeChildren(Graph<Q> graph, double change) {
		for (Graph<Q> child : graph.getChildren()) {
			change(child, change);
		}
	}

	private double decayChange(double change, double decay) {
		double signedDecay = Math.copySign(decay, change);
		if (Math.abs(signedDecay)>=Math.abs(change)) {
			return 0;
		}
		return change - signedDecay;
	}
}

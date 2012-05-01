package seeemilyplay.quizzer.network;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;


final class GraphImpl<Q extends Question> implements Graph<Q> {

	private final String id;
	private final QuestionSpace<Q> questionSpace;
	private Probability probability = Probability.getImpossibility();

	private final ChangeRippler<Q> rippler;

	private final Set<Graph<Q>> children = new HashSet<Graph<Q>>();
	private final Set<Graph<Q>> parents = new HashSet<Graph<Q>>();

	public GraphImpl(
			QuizzerConfig config,
			String id,
			QuestionSpace<Q> questionSpace) {
		this.id = id;
		this.questionSpace = questionSpace;
		this.rippler = new ChangeRippler<Q>(config);
	}

	public boolean isRoot() {
		return parents.isEmpty();
	}

	public String getId() {
		return id;
	}

	public QuestionSpace<Q> getQuestionSpace() {
		return questionSpace;
	}

	public Probability getProbability() {
		return probability;
	}

	public void setProbabilityAndRipple(Probability probability) {
		rippler.setProbability(this, probability);
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

	public void addChild(GraphImpl<Q> child) {
		linkChild(child);
		child.linkParent(this);
	}

	public Set<Graph<Q>> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	public Set<Graph<Q>> getParents() {
		return Collections.unmodifiableSet(parents);
	}

	private void linkChild(Graph<Q> child) {
		children.add(child);
	}

	private void linkParent(Graph<Q> parent) {
		parents.add(parent);
	}

	public int hashCode() {
		return id.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final GraphImpl<?> other = (GraphImpl<?>)obj;
		return id.equals(other.id);
	}
}

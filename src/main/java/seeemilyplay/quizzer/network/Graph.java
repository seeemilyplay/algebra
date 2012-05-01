package seeemilyplay.quizzer.network;

import java.util.Set;

import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;

public interface Graph<Q extends Question> {

	public boolean isRoot();

	public String getId();

	public QuestionSpace<Q> getQuestionSpace();

	public Probability getProbability();

	public void setProbabilityAndRipple(Probability probability);

	public void setProbability(Probability probability);

	public Set<Graph<Q>> getChildren();

	public Set<Graph<Q>> getParents();
}
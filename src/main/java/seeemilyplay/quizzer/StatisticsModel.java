package seeemilyplay.quizzer;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.core.Question;

public interface StatisticsModel<Q extends Question> {

	public int getQuestionCount();

	public Probability getOverallProbability();

	public void addListener(Listener listener);
}

package seeemilyplay.quizzer;

import seeemilyplay.quizzer.core.Question;

public interface Quizzer<Q extends Question> {

	public QuizQuestion<Q> next();

	public StatisticsModel<Q> getStatisticsModel();
}

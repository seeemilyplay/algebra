package seeemilyplay.quizzer;

import seeemilyplay.quizzer.core.Question;

public interface QuizQuestion<Q extends Question> {

	public Q getQuestion();

	public void answerCorrectly();

	public void answerIncorrectly();
}

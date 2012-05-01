package seeemilyplay.quizzer.core;

/**
 * Creates <code>QuestionSpaces</code>.
 *
 * @param <Q> the question type
 */
public interface QuestionSpaceFactory<Q extends Question> {

	public QuestionSpace<Q> createQuestionSpace();
}

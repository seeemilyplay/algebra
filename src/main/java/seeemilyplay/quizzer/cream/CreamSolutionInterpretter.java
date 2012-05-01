package seeemilyplay.quizzer.cream;

import jp.ac.kobe_u.cs.cream.Solution;

/**
 * This converts Cream solutions to <code>Questions</code>.
 *
 * @param <Q> the type of the Questions
 */
public interface CreamSolutionInterpretter<Q> {

	public Q convertToQuestion(Solution solution);
}

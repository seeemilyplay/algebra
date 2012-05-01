package seeemilyplay.quizzer.cream;

import jp.ac.kobe_u.cs.cream.Network;

import seeemilyplay.quizzer.core.Question;

/**
 * Defines a question space using the Cream constraint solver
 * package.
 *
 * @param <Q> the type of the questions
 */
public interface CreamSpaceDefinition<Q extends Question> {


	public CreamSolutionInterpretter<Q> installConstraints(Network network);
}

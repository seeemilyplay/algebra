package seeemilyplay.quizzer.network;

import seeemilyplay.quizzer.core.Question;



public interface Network<Q extends Question> extends Iterable<Graph<Q>>{

	public int getRootCount();

	public Graph<Q> getRoot(int index);
}

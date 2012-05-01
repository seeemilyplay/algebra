package seeemilyplay.quizzer.network;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.definition.NetworkDefinition;

public interface NetworkFactory<Q extends Question> {

	public Network<Q> createNetwork(
			QuizzerConfig config,
			NetworkDefinition<Q> definition);
}

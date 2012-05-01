package seeemilyplay.quizzer;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.definition.NetworkDefinition;
import seeemilyplay.quizzer.network.Network;
import seeemilyplay.quizzer.network.NetworkFactory;

public final class QuizzerFactory<Q extends Question> {

	private final NetworkFactory<Q> networkFactory;

	public QuizzerFactory(NetworkFactory<Q> networkFactory) {
		this.networkFactory = networkFactory;
	}

	public Quizzer<Q> createQuizzer(
			String id,
			QuizzerConfig config,
			NetworkDefinition<Q> definition) {
		Network<Q> network = networkFactory.createNetwork(config, definition);
		return new QuizzerImpl<Q>(
				id,
				config,
				network);
	}
}

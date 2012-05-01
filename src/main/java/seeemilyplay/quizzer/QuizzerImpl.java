package seeemilyplay.quizzer;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.db.NetworkPersister;
import seeemilyplay.quizzer.db.NetworkPersisterFactory;
import seeemilyplay.quizzer.network.Network;

final class QuizzerImpl<Q extends Question> implements Quizzer<Q> {

	private final QuestionChooser<Q> questionChooser;
	private final ProbabilityUpdater<Q> probabilityUpdator;
	private final StatisticsModelImpl<Q> statisticsModel;
	private NetworkPersister persister;

	public QuizzerImpl(
			String id,
			QuizzerConfig config,
			Network<Q> network) {
		this.questionChooser = new QuestionChooser<Q>(config, network);
		this.probabilityUpdator = new ProbabilityUpdater<Q>(config);
		this.statisticsModel = new StatisticsModelImpl<Q>(network);
		initPersister(id, network);
		loadState();
	}

	private void initPersister(String id, Network<Q> network) {
		NetworkPersisterFactory factory = new NetworkPersisterFactory();
		persister = factory.createNetworkPersister(id, network);
	}

	private void loadState() {
		persister.load();
		recalculateStatistics();
	}

	private void saveState() {
		persister.save();
	}

	private void recalculateStatistics() {
		statisticsModel.recalculate();
	}

	public QuizQuestion<Q> next() {
		QuestionInfo<Q> question = questionChooser.next();
		return new QuizQuestionImpl(question);
	}

	public StatisticsModel<Q> getStatisticsModel() {
		return statisticsModel;
	}

	private class QuizQuestionImpl implements QuizQuestion<Q> {

		private final QuestionInfo<Q> questionInfo;

		public QuizQuestionImpl(QuestionInfo<Q> questionInfo) {
			this.questionInfo = questionInfo;
		}

		public Q getQuestion() {
			return questionInfo.getQuestion();
		}

		public void answerCorrectly() {
			probabilityUpdator.onCorrectAnswerFor(questionInfo);
			saveState();
			recalculateStatistics();
		}

		public void answerIncorrectly() {
			probabilityUpdator.onIncorrectAnswerFor(questionInfo);
			saveState();
			recalculateStatistics();
		}
	}
}

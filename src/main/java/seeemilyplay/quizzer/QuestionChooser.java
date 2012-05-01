package seeemilyplay.quizzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.network.Graph;
import seeemilyplay.quizzer.network.Network;

final class QuestionChooser<Q extends Question> {

	private final Random random = new Random();

	private final QuizzerConfig config;
	private final Network<Q> network;

	private ShortTermMemory<Q> shortTermMemory;
	private CandidateSelector<Q> candidateSelector;

	private List<Graph<Q>> candidates;


	public QuestionChooser(
			QuizzerConfig config,
			Network<Q> network) {
		this.config = config;
		this.network = network;
		setupShortTermMemory();
		setupCandidateSelector();
	}

	private void setupShortTermMemory() {
		this.shortTermMemory = new ShortTermMemory<Q>(
				getNoQuestionsWithoutReps());
	}

	private int getNoQuestionsWithoutReps() {
		return config.getNoQuestionsWithoutReps();
	}

	private void setupCandidateSelector() {
		candidateSelector = new CandidateSelector<Q>(
				config,
				shortTermMemory,
				network);
	}

	public QuestionInfo<Q> next() {
		selectCandidates();
		QuestionInfo<Q> questionInfo = pick();
		markAsRecent(questionInfo);
		return questionInfo;
	}

	private void selectCandidates() {
		candidates = candidateSelector.selectCandidates();
	}

	private QuestionInfo<Q> pick() {
		Graph<Q> graph = pickCandidate();
		Q question = pickQuestion(graph);
		return new QuestionInfo<Q>(graph, question);
	}

	private Graph<Q> pickCandidate() {
		int index = pickCandidateIndex();
		return candidates.get(index);
	}

	private int pickCandidateIndex() {
		int number = candidates.size();
		double guassian = Math.abs(random.nextGaussian());
		double scaledGaussian = guassian * (number-1);
		int index = (int)Math.round(Math.min(Math.max(0, scaledGaussian), number-1));
		return index;
	}

	private Q pickQuestion(Graph<Q> graph) {
		List<Q> questions = new ArrayList<Q>(getQuestionsFor(graph));
		Collections.shuffle(questions);
		return questions.get(0);
	}

	private Set<Q> getQuestionsFor(Graph<Q> graph) {
		return shortTermMemory.getNonRecentQuestions(graph);
	}

	private void markAsRecent(QuestionInfo<Q> questionInfo) {
		shortTermMemory.save(questionInfo.getQuestion());
	}
}

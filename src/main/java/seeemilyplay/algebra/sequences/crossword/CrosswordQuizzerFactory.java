package seeemilyplay.algebra.sequences.crossword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seeemilyplay.algebra.games.core.PlayId;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.QuizzerFactory;
import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.QuestionSpace;
import seeemilyplay.quizzer.cream.CreamQuestionSpaceFactory;
import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.definition.Edge;
import seeemilyplay.quizzer.definition.NetworkDefinition;
import seeemilyplay.quizzer.definition.Value;
import seeemilyplay.quizzer.network.BasicNetworkFactory;
import seeemilyplay.quizzer.network.NetworkFactory;

final class CrosswordQuizzerFactory {

	private final QuizzerConfig config;
	private final CrosswordRepository crosswordRepository;
	private final NetworkDefinition<Crossword> definition;

	public CrosswordQuizzerFactory(
			QuizzerConfig config,
			CrosswordRepository crosswordRepository) {
		this.config = config;
		this.crosswordRepository = crosswordRepository;
		this.definition = new NetworkDefinition<Crossword>();
	}

	public Quizzer<Crossword> createCrosswordQuizzer(PlayId playId) {

		defineValues();
		defineEdges();

		return createQuizzer(playId);
	}

	private Quizzer<Crossword> createQuizzer(PlayId playId) {
		QuizzerFactory<Crossword> factory = createQuizzerFactory();
		return factory.createQuizzer(
				playId.getId(),
				config,
				definition);
	}

	private QuizzerFactory<Crossword> createQuizzerFactory() {
		NetworkFactory<Crossword> networkFactory = new BasicNetworkFactory<Crossword>();
		return new QuizzerFactory<Crossword>(networkFactory);
	}

	private void defineValues() {
		for (Level level : getIncludedLevels()) {
			defineValue(level);
		}
	}

	private List<Level> getIncludedLevels() {
		List<Level> includedLevels = new ArrayList<Level>();
		for (Level level : Level.values()) {
			if (isIncluded(level)) {
				includedLevels.add(level);
			}
		}
		return Collections.unmodifiableList(includedLevels);
	}

	private boolean isIncluded(Level level) {
		return crosswordRepository.getCrosswordCount(level)>0;
	}

	private void defineValue(Level level) {
		Value<Crossword> value = createValue(level);
		definition.addValue(value);
	}

	private Value<Crossword> createValue(Level level) {
		return new Value<Crossword>(
				createId(level),
				createQuestionSpace(level));
	}

	private void defineEdges() {
		List<Level> level = getIncludedLevels();
		for (int i=1; i<level.size(); i++) {
			Level levelA = Level.values()[i-1];
			Level levelB = Level.values()[i];
			defineEdge(levelA, levelB);
		}
	}

	private void defineEdge(Level levelA, Level levelB) {
		Edge edge = createEdge(levelA, levelB);
		definition.addEdge(edge);
	}

	private Edge createEdge(Level levelA, Level levelB) {
		return new Edge(
				createId(levelA),
				createId(levelB));
	}

	private String createId(Level level) {
		return level.toString();
	}

	private QuestionSpace<Crossword> createQuestionSpace(Level level) {

		CreamQuestionSpaceFactory<Crossword> factory = createSpaceFactory(level);
		return factory.createQuestionSpace();
	}

	private CreamQuestionSpaceFactory<Crossword> createSpaceFactory(Level level) {
		CreamSpaceDefinition<Crossword> spaceDefinition =
			createSpaceDefinition(level);
		return new CreamQuestionSpaceFactory<Crossword>(spaceDefinition);
	}

	private CreamSpaceDefinition<Crossword> createSpaceDefinition(
			Level level) {
		return new CrosswordSpaceDefinition(
				crosswordRepository,
				level);
	}
}

package seeemilyplay.quizzer.cream;

import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.QuizzerFactory;
import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;
import seeemilyplay.quizzer.core.QuestionSpaceFactory;
import seeemilyplay.quizzer.definition.Edge;
import seeemilyplay.quizzer.definition.NetworkDefinition;
import seeemilyplay.quizzer.definition.Value;
import seeemilyplay.quizzer.network.BasicNetworkFactory;
import seeemilyplay.quizzer.network.NetworkFactory;

public final class CreamQuizzerFactory<Q extends Question> {

	private final String id;
	private final QuizzerConfig config;
	private final NetworkDefinition<Q> definition;
	private final DescriptionParser<Q> descriptionParser;

	public CreamQuizzerFactory(
			String id,
			QuizzerConfig config,
			DescriptionParser<Q> descriptionParser) {
		this.id = id;
		this.config = config;
		this.definition = new NetworkDefinition<Q>();
		this.descriptionParser = descriptionParser;
	}

	public Quizzer<Q> createQuizzer() {
		QuizzerFactory<Q> factory = createQuizzerFactory();
		return factory.createQuizzer(id, config, definition);
	}

	private QuizzerFactory<Q> createQuizzerFactory() {
		NetworkFactory<Q> networkFactory = new BasicNetworkFactory<Q>();
		return new QuizzerFactory<Q>(networkFactory);
	}

	public void add(String description) {
		Value<Q> value = createValue(description);
		includeInDefinition(value);
	}

	private Value<Q> createValue(String description) {
		CreamSpaceDefinition<Q> definition = createSpaceDefinition(description);
		return createValue(description, definition);
	}

	private CreamSpaceDefinition<Q> createSpaceDefinition(String description) {
		return descriptionParser.createSpaceDefinition(description);
	}

	private Value<Q> createValue(
			String description,
			CreamSpaceDefinition<Q> definition) {
		QuestionSpace<Q> questionSpace = createQuestionSpace(definition);
		return createValue(description, questionSpace);
	}

	private QuestionSpace<Q> createQuestionSpace(
			CreamSpaceDefinition<Q> definition) {
		QuestionSpaceFactory<Q> factory =
			createQuestionSpaceFactory(definition);
		return factory.createQuestionSpace();
	}

	private QuestionSpaceFactory<Q> createQuestionSpaceFactory(
			CreamSpaceDefinition<Q> definition) {
		return new CreamQuestionSpaceFactory<Q>(definition);
	}

	private Value<Q> createValue(
			String description,
			QuestionSpace<Q> questionSpace) {
		return new Value<Q>(description, questionSpace);
	}

	private void includeInDefinition(Value<Q> value) {
		definition.addValue(value);
	}

	public void link(String parent, String... children) {
		for (String child : children) {
			defineEdge(parent, child);
		}
	}

	private void defineEdge(String parent, String child) {
		Edge edge = createEdge(parent, child);
		includeInDefinition(edge);
	}

	private Edge createEdge(String parent, String child) {
		return new Edge(parent, child);
	}

	private void includeInDefinition(Edge edge) {
		definition.addEdge(edge);
	}
}

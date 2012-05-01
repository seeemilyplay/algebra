package seeemilyplay.quizzer.network;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;
import seeemilyplay.quizzer.definition.Value;


final class GraphManager<Q extends Question> {

	private final QuizzerConfig config;
	private final Map<Value<Q>,GraphImpl<Q>> graphs =
		new HashMap<Value<Q>,GraphImpl<Q>>();

	public GraphManager(QuizzerConfig config) {
		this.config = config;
	}

	public Set<Graph<Q>> getAllGraphs() {
		return Collections.unmodifiableSet(
				new HashSet<Graph<Q>>(graphs.values()));
	}

	public GraphImpl<Q> getGraph(Value<Q> value) {
		if (!isGraphForValue(value)) {
			createAndSaveGraphForValue(value);
		}
		return loadGraphForValue(value);
	}

	private boolean isGraphForValue(Value<Q> value) {
		return graphs.containsKey(value);
	}

	private void createAndSaveGraphForValue(Value<Q> value) {
		GraphImpl<Q> graph = createGraph(value);
		saveGraphForValue(value, graph);
	}

	private GraphImpl<Q> createGraph(Value<Q> value) {
		String id = value.getId();
		QuestionSpace<Q> questionSpace = value.getQuestionSpace();
		return createGraph(id, questionSpace);
	}

	private GraphImpl<Q> createGraph(String id, QuestionSpace<Q> questionSpace) {
		return new GraphImpl<Q>(config, id, questionSpace);
	}

	private void saveGraphForValue(Value<Q> value, GraphImpl<Q> graph) {
		graphs.put(value, graph);
	}

	private GraphImpl<Q> loadGraphForValue(Value<Q> value) {
		return graphs.get(value);
	}
}

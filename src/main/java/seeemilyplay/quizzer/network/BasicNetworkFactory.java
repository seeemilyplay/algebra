package seeemilyplay.quizzer.network;


import seeemilyplay.quizzer.config.QuizzerConfig;
import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.definition.NetworkDefinition;
import seeemilyplay.quizzer.definition.Value;

public final class BasicNetworkFactory<Q extends Question> implements NetworkFactory<Q> {

	private QuizzerConfig config;
	private GraphManager<Q> graphManager;
	private NetworkImpl<Q> network;

	public BasicNetworkFactory() {
		super();
	}

	public Network<Q> createNetwork(
			QuizzerConfig config,
			NetworkDefinition<Q> definition) {
		setup(config);
		createEdges(definition);
		return gatherRootsIntoNetwork();
	}

	private void setup(QuizzerConfig config) {
		setupConfig(config);
		setupGraphDirectory();
		setupNetwork();
	}

	private void setupConfig(QuizzerConfig config) {
		this.config = config;
	}

	private void setupGraphDirectory() {
		graphManager = new GraphManager<Q>(config);
	}

	private void setupNetwork() {
		network = new NetworkImpl<Q>();
	}

	private void createEdges(NetworkDefinition<Q> definition) {
		for (int i=0; i<definition.getEdgeCount(); i++) {
			Value<Q> a = definition.getA(i);
			Value<Q> b = definition.getB(i);

			createEdge(a, b);
		}
	}

	private void createEdge(Value<Q> a, Value<Q> b) {

		GraphImpl<Q> parent = getGraph(a);
		GraphImpl<Q> child = getGraph(b);

		parent.addChild(child);
	}

	private GraphImpl<Q> getGraph(Value<Q> value) {
		return graphManager.getGraph(value);
	}

	private Network<Q> gatherRootsIntoNetwork() {
		for (Graph<Q> graph : graphManager.getAllGraphs()) {
			if (graph.isRoot()) {
				addRoot(graph);
			}
		}
		return network;
	}

	private void addRoot(Graph<Q> graph) {
		network.addRoot(graph);
	}
}


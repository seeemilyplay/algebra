package seeemilyplay.quizzer.db;

import seeemilyplay.quizzer.core.Probability;
import seeemilyplay.quizzer.network.Graph;
import seeemilyplay.quizzer.network.Network;

/**
 * Persists networks.
 */
final class NetworkPersisterImpl implements NetworkPersister {

	private final NetworkDAO networkDao;
	private final String id;
	private final Network<?> network;

	private NetworkState networkState;

	public NetworkPersisterImpl(
			NetworkDAO networkDao,
			String id,
			Network<?> network) {
		this.networkDao = networkDao;
		this.id = id;
		this.network = network;
	}

	public void save() {
		updateStateFromNetwork();
		saveAllState();
	}

	private void updateStateFromNetwork() {
		for(Graph<?> graph : network) {
			GraphState graphState = getGraphState(graph);
			updateStateFromGraph(graph, graphState);
		}
	}

	private void updateStateFromGraph(
			Graph<?> graph,
			GraphState graphState) {
		double probability = getProbability(graph);
		graphState.setProbability(probability);
	}

	public void load() {
		loadOrCreateNetworkState();
		setProbabilitiesFromNetworkState();
		saveAllState();
	}

	private void loadOrCreateNetworkState() {
		tryToLoadNetworkState();
		if (!isNetworkStateLoaded()) {
			createNetworkState();
		}
	}

	private void tryToLoadNetworkState() {
		networkState = networkDao.loadNetwork(id);
	}

	private boolean isNetworkStateLoaded() {
		return networkState!=null;
	}

	private void createNetworkState() {
		networkState = new NetworkState(id);
	}

	private void saveAllState() {
		networkDao.saveNetwork(networkState);
	}

	private void setProbabilitiesFromNetworkState() {
		for (Graph<?> graph : network) {
			GraphState graphState = getOrCreateGraphState(graph);
			setProbabilityFromGraphState(graphState, graph);
		}
	}

	private GraphState getOrCreateGraphState(Graph<?> graph) {
		GraphState graphState = getGraphState(graph);
		if (graphState==null) {
			graphState = createGraphState(graph);
		}
		return graphState;
	}

	private GraphState getGraphState(Graph<?> graph) {
		String id = getId(graph);
		for (GraphState graphState : networkState.getGraphStates()) {
			if (id.equals(graphState.getId())) {
				return graphState;
			}
		}
		return null;
	}

	private GraphState createGraphState(Graph<?> graph) {
		GraphState graphState = new GraphState(
				getId(graph),
				getProbability(graph));
		networkState.getGraphStates().add(graphState);
		return graphState;
	}

	private String getId(Graph<?> graph) {
		return graph.getId();
	}

	private double getProbability(Graph<?> graph) {
		Probability probability = graph.getProbability();
		return probability.getValue();
	}

	private void setProbabilityFromGraphState(
			GraphState graphState,
			Graph<?> graph) {
		Probability stateProbability = new Probability(
				graphState.getProbability());
		graph.setProbability(stateProbability);
	}
}
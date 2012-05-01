package seeemilyplay.quizzer.network;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import seeemilyplay.quizzer.core.Question;

final class NetworkTraverser<Q extends Question> implements Iterator<Graph<Q>> {

	private final Queue<Graph<Q>> queue =
		new LinkedList<Graph<Q>>();

	private final Set<Graph<Q>> knownGraphs =
		new HashSet<Graph<Q>>();

	public NetworkTraverser(Network<Q> network) {
		queue(network);
	}

	private void queue(Network<Q> network) {
		for (int i=0; i<network.getRootCount(); i++) {
			Graph<Q> root = network.getRoot(i);
			queue(root);
		}
	}

	private void queue(Graph<Q> graph) {
		if (!isKnown(graph)) {
			markAsKnown(graph);
			queue.add(graph);
		}
	}

	private boolean isKnown(Graph<Q> graph) {
		return knownGraphs.contains(graph);
	}

	private void markAsKnown(Graph<Q> graph) {
		knownGraphs.add(graph);
	}

	public boolean hasNext() {
		return (!queue.isEmpty());
	}

	public Graph<Q> next() {
		Graph<Q> nextGraph = queue.remove();
		queueChildren(nextGraph);
		return nextGraph;
	}

	private void queueChildren(Graph<Q> graph) {
		for (Graph<Q> child : graph.getChildren()) {
			queue(child);
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}

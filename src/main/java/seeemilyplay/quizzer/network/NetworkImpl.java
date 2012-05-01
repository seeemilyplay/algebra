package seeemilyplay.quizzer.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import seeemilyplay.quizzer.core.Question;


final class NetworkImpl<Q extends Question> implements Network<Q> {

	private final List<Graph<Q>> roots = new ArrayList<Graph<Q>>();

	public NetworkImpl() {
		super();
	}

	public void addRoot(Graph<Q> root) {
		validateRoot(root);
		roots.add(root);
	}

	private void validateRoot(Graph<Q> root) {
		if (containsRoot(root)) {
			throw new IllegalArgumentException();
		}
	}

	private boolean containsRoot(Graph<Q> root) {
		return roots.contains(root);
	}

	public int getRootCount() {
		return roots.size();
	}

	public Graph<Q> getRoot(int index) {
		return roots.get(index);
	}

	public Iterator<Graph<Q>> iterator() {
		return new NetworkTraverser<Q>(this);
	}
}

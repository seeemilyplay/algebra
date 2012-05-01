package seeemilyplay.quizzer.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores <code>Edges</code> on behalf of
 * the <code>NetworkDefinition</code>.
 */
final class Edges {

	private final List<Edge> edges =
		new ArrayList<Edge>();

	public Edges() {
		super();
	}

	public void add(Edge edge) {
		check(edge);
		save(edge);
	}

	private void check(Edge edge) {
		if (contains(edge)){
			throw new IllegalArgumentException();
		}
	}

	private boolean contains(Edge edge) {
		return edges.contains(edge);
	}

	private void save(Edge edge) {
		edges.add(edge);
	}

	public int getCount() {
		return edges.size();
	}

	public Edge getEdge(int index) {
		return edges.get(index);
	}
}

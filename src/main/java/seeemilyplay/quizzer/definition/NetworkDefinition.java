package seeemilyplay.quizzer.definition;

import seeemilyplay.quizzer.core.Question;


public final class NetworkDefinition<Q extends Question> {

	private final Values<Q> values = new Values<Q>();
	private final Edges edges = new Edges();


	public NetworkDefinition() {
		super();
	}

	public void addValue(Value<Q> value) {
		values.add(value);
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public int getEdgeCount() {
		return edges.getCount();
	}

	private Edge getEdge(int index) {
		return edges.getEdge(index);
	}

	public Value<Q> getA(int index) {
		Edge edge = getEdge(index);
		return getA(edge);
	}

	public Value<Q> getB(int index) {
		Edge edge = getEdge(index);
		return getB(edge);
	}

	private Value<Q> getA(Edge edge) {
		String a = edge.getA();
		return getValueForId(a);
	}

	private Value<Q> getB(Edge edge) {
		String b = edge.getB();
		return getValueForId(b);
	}

	private Value<Q> getValueForId(String id) {
		return values.getValueForId(id);
	}
}

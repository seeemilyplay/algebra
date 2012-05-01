package seeemilyplay.quizzer.definition;

import java.util.StringTokenizer;

import junit.framework.TestCase;

public class EdgesTest extends TestCase {

	private Edges edges;
	private Throwable t;

	protected void setUp() throws Exception {
		super.setUp();
		edges = new Edges();
		t = null;
	}

	public void testAddingEdgesStoresThem() {

		whenEdgeAdded("a", "b");
		andEdgeAdded("b", "c");

		thenHasEdges("a b", "b c");
	}

	public void testAddingARepeatEdgeThrowsAnException() {

		whenEdgeAdded("a", "b");
		andEdgeAdded("a", "b");

		thenExceptionIsThrown();
	}

	private void whenEdgeAdded(String a, String b) {
		addEdge(a, b);
	}

	private void andEdgeAdded(String a, String b) {
		addEdge(a, b);
	}

	private void addEdge(String a, String b) {
		try {
			edges.add(createEdge(a, b));
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void thenHasEdges(String...expectedEdges) {
		assertEquals(expectedEdges.length, edges.getCount());
		for (int i=0; i<expectedEdges.length; i++) {
			Edge actualEdge = edges.getEdge(i);
			Edge expectedEdge = parseEdge(expectedEdges[i]);
			assertEquals(actualEdge, expectedEdge);
		}
	}

	private Edge parseEdge(String edgeString) {
		StringTokenizer tokenizer = new StringTokenizer(edgeString, ", ");
		String a = tokenizer.nextToken();
		String b = tokenizer.nextToken();
		return createEdge(a, b);
	}

	private Edge createEdge(String a, String b) {
		return new Edge(a, b);
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}
}

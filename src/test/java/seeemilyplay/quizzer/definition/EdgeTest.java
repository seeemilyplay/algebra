package seeemilyplay.quizzer.definition;

import junit.framework.TestCase;

public class EdgeTest extends TestCase {

	private Edge edge;
	private Throwable t;
	private Object comparableObject;

	protected void setUp() throws Exception {
		super.setUp();
		edge = null;
		t = null;
		comparableObject = null;
	}

	public void testConstructionValuesAreUsedInEdge() {

		whenConstructedWith("a", "b");

		thenAandBAre("a", "b");
	}

	public void testConstructionFailsIfAIsNull() {

		whenConstructedWithNullA();

		thenExceptionIsThrown();
	}

	public void testConstructionFailsIfBIsNull() {

		whenConstructedWithNullB();

		thenExceptionIsThrown();
	}

	public void testEdgesWithSameValuesAreEqual() {

		givenEdge("a", "b");

		whenComparedToEdge("a", "b");

		thenTheyAreEqual();
	}

	public void testEdgesWithDifferentAValuesAreNotEqual() {

		givenEdge("a", "b");

		whenComparedToEdge("not a", "b");

		thenTheyAreNotEqual();
	}

	public void testEdgesWithDifferentBValuesAreNotEqual() {

		givenEdge("a", "b");

		whenComparedToEdge("a", "not b");

		thenTheyAreNotEqual();
	}

	public void testEdgesAreNotEqualToNull() {

		givenEdge("a", "b");

		whenComparedToNull();

		thenTheyAreNotEqual();
	}

	public void testEdgesAreNotEqualToOtherObjects() {

		givenEdge("a", "b");

		whenComparedToObject("not an edge");

		thenTheyAreNotEqual();
	}

	private void givenEdge(String a, String b) {
		createEdge(a, b);
	}

	private void whenConstructedWith(String a, String b) {
		createEdge(a, b);
	}

	private void whenConstructedWithNullA() {
		createEdge(null, "b");
	}

	private void whenConstructedWithNullB() {
		createEdge("a", null);
	}

	private void createEdge(String a, String b) {
		try {
			edge = new Edge(a, b);
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void whenComparedToEdge(String a, String b) {
		comparableObject = new Edge(a, b);
	}

	private void whenComparedToNull() {
		comparableObject = null;
	}

	private void whenComparedToObject(Object object) {
		comparableObject = object;
	}

	private void thenAandBAre(String a, String b) {
		assertEquals(edge.getA(), a);
		assertEquals(edge.getB(), b);
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}

	private void thenTheyAreEqual() {
		assertTrue(edge.equals(comparableObject));
		assertEquals(edge.hashCode(), comparableObject.hashCode());
	}

	private void thenTheyAreNotEqual() {
		assertFalse(edge.equals(comparableObject));
	}
}

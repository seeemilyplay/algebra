package seeemilyplay.quizzer.definition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import junit.framework.TestCase;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;

public class NetworkDefinitionTest extends TestCase {

	private NetworkDefinition<TestQuestion> networkDefinition;

	protected void setUp() throws Exception {
		networkDefinition = new NetworkDefinition<TestQuestion>();
	}


	public void testAddingValuesAndEdges() {

		whenAdd("a b", "b c");

		thenHas("a b", "b c");
	}

	private void whenAdd(String ... edgeStrings) {
		addValues(edgeStrings);
		addEdges(edgeStrings);
	}

	private void addValues(String ... edgeStrings) {
		Set<String> ids = getIds(edgeStrings);
		for (String id : ids) {
			Value<TestQuestion> value = createValue(id);
			networkDefinition.addValue(value);
		}
	}

	private Set<String> getIds(String ... edgeStrings) {
		Set<String> ids = new HashSet<String>();
		for (String edgeString : edgeStrings) {
			Edge edge = parseEdge(edgeString);
			ids.add(edge.getA());
			ids.add(edge.getB());
		}
		return Collections.unmodifiableSet(ids);
	}

	private Value<TestQuestion> createValue(String id) {
		SortedSet<TestQuestion> emptyQuestionSet = new TreeSet<TestQuestion>();
		QuestionSpace<TestQuestion> emptyQuestionSpace =
			new QuestionSpace<TestQuestion>(emptyQuestionSet);
		return new Value<TestQuestion>(
				id,
				emptyQuestionSpace);
	}

	private void addEdges(String ... edgeStrings) {
		for (String edgeString : edgeStrings) {
			Edge edge = parseEdge(edgeString);
			networkDefinition.addEdge(edge);
		}
	}

	private void thenHas(String ... edgeStrings) {
		assertEquals(edgeStrings.length, networkDefinition.getEdgeCount());
		for (int i=0; i<edgeStrings.length; i++) {
			Edge edge = parseEdge(edgeStrings[i]);
			assertEquals(edge.getA(), getA(i));
			assertEquals(edge.getB(), getB(i));
		}
	}

	private String getA(int index) {
		return networkDefinition.getA(index).getId();
	}

	private String getB(int index) {
		return networkDefinition.getB(index).getId();
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

	private static class TestQuestion implements Question {

		private String value;

		@SuppressWarnings("unused")
		public TestQuestion(String value) {
			this.value = value;
		}

		@SuppressWarnings("unused")
		public String getValue() {
			return value;
		}
	}
}

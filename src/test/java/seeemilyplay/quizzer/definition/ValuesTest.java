package seeemilyplay.quizzer.definition;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.TestCase;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;

public class ValuesTest extends TestCase {

	private Values<TestQuestion> values;
	private Throwable t;

	protected void setUp() throws Exception {
		values = new Values<TestQuestion>();
		t = null;
	}

	public void testAddingValues() {

		whenAddValue("a");

		thenContainsValue("a");
	}

	public void testAddingRepeatValuesThrowsException() {

		givenAddedValue("a");

		whenAddValue("a");

		thenExceptionIsThrown();
	}

	private void givenAddedValue(String id) {
		addValue(id);
	}

	private void whenAddValue(String id) {
		addValue(id);
	}

	private void addValue(String id) {
		try {
			Value<TestQuestion> value = createValue(id);
			values.add(value);
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private Value<TestQuestion> createValue(String id) {
		SortedSet<TestQuestion> emptyQuestionSet = new TreeSet<TestQuestion>();
		QuestionSpace<TestQuestion> emptyQuestionSpace =
			new QuestionSpace<TestQuestion>(emptyQuestionSet);
		return new Value<TestQuestion>(
				id,
				emptyQuestionSpace);
	}

	private void thenContainsValue(String id) {
		assertNotNull(values.getValueForId(id));
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}

	private static class TestQuestion implements Question {

	}
}

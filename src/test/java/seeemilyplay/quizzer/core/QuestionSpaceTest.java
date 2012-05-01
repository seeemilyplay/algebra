package seeemilyplay.quizzer.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

public class QuestionSpaceTest extends TestCase {

	private Set<TestQuestion> questions;
	private QuestionSpace<TestQuestion> questionSpace;

	protected void setUp() throws Exception {
		questions = new HashSet<TestQuestion>();
	}

	public void testQuestionSpaceIsOrderedAndWithoutRepetitions() {

		givenQuestions(3,1,3,2,2,1);

		whenQuestionSpaceCreated();

		thenValuesAre(1,2,3);
	}

	private void givenQuestions(int... values) {
		for (int value : values) {
			addQuestionFor(value);
		}
	}

	private void addQuestionFor(int value) {
		TestQuestion question = createQuestion(value);
		questions.add(question);
	}

	private TestQuestion createQuestion(int value) {
		return new TestQuestion(value);
	}

	private void whenQuestionSpaceCreated() {
		questionSpace = new QuestionSpace<TestQuestion>(questions);
	}

	private void thenValuesAre(int... values) {
		assertEquals(values.length, questionSpace.getQuestionCount());
		Iterator<TestQuestion> iterator = questionSpace.iterator();
		for (int i=0; i<values.length; i++) {
			int expectedValue = values[i];
			TestQuestion question = iterator.next();
			int actualValue = question.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}

	private static class TestQuestion implements Question {

		private int value;

		public TestQuestion(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public int hashCode() {
			return value;
		}

		public boolean equals(Object obj) {
			if (obj==null) {
				return false;
			}
			if (getClass()!=obj.getClass()) {
				return false;
			}
			TestQuestion other = (TestQuestion)obj;
			return (value==other.value);
		}
	}
}

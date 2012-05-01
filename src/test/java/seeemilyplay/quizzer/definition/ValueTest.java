package seeemilyplay.quizzer.definition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;

public class ValueTest extends TestCase {

	private String id;
	private QuestionSpace<TestQuestion> questionSpace;
	private Value<TestQuestion> value;
	private Throwable t;
	private Object comparableObject;

	protected void setUp() throws Exception {
		super.setUp();
		id = null;
		questionSpace = null;
		value = null;
		t = null;
		comparableObject = null;
	}

	public void testQuestionSpaceIsUsedFromConstruction() {

		givenId("id");

		whenConstructedWithQuestions("q1", "q2");

		thenQuestionsAre("q1", "q2");
	}

	public void testIdIsUsedFromConstruction() {

		givenQuestions("q1", "q2");

		whenConstructedWithId("id");

		thenIdIs("id");
	}

	public void testCantBeConstructedWithNullId() {

		givenQuestions("q1", "q2");

		whenConstructedWithNullId();

		thenExceptionIsThrown();
	}

	public void testCantBeConstructedWithNullQuestionSpace() {

		givenId("id");

		whenConstructedWithNullQuestionSpace();

		thenExceptionIsThrown();
	}

	public void testValuesWithSameIdAreEqual() {

		givenValue("a", "q1", "q2");

		whenComparedToValueWithId("a");

		thenTheyAreEqual();
	}

	public void testValuesWithDifferentIdsAreNotEqual() {

		givenValue("a", "q1", "q2");

		whenComparedToValueWithId("b");

		thenTheyAreNotEqual();
	}

	public void testValuesAreNotEqualToNull() {

		givenValue("a", "q1", "q2");

		whenComparedToNull();

		thenTheyAreNotEqual();
	}

	public void testValuesAreNotEqualToOtherObjects() {

		givenValue("a", "q1", "q2");

		whenComparedToObject("not a value");

		thenTheyAreNotEqual();
	}

	private void givenValue(String id, String ... questions) {
		setId(id);
		setQuestions(questions);
		createValue();
	}

	private void givenId(String id) {
		setId(id);
	}

	private void whenConstructedWithId(String id) {
		setId(id);
		createValue();
	}

	private void whenConstructedWithNullId() {
		setId(null);
		createValue();
	}

	private void setId(String id) {
		this.id = id;
	}

	private void givenQuestions(String ... questions) {
		setQuestions(questions);
	}

	private void whenConstructedWithQuestions(String ... questions) {
		setQuestions(questions);
		createValue();
	}

	private void whenConstructedWithNullQuestionSpace() {
		questionSpace = null;
		createValue();
	}

	private void setQuestions(String ... questions) {
		Set<TestQuestion> questionSet = createQuestionSet(questions);
		questionSpace = new QuestionSpace<TestQuestion>(questionSet);
	}

	private Set<TestQuestion> createQuestionSet(String ... questions) {
		Set<TestQuestion> questionSet = new HashSet<TestQuestion>();
		for (String question : questions) {
			questionSet.add(createTestQuestion(question));
		}
		return Collections.unmodifiableSet(questionSet);
	}

	private void createValue() {
		try {
			value = new Value<TestQuestion>(id, questionSpace);
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void whenComparedToValueWithId(String id) {
		Set<TestQuestion> emptyQuestionSet = createQuestionSet();
		QuestionSpace<TestQuestion> emptySpace = new QuestionSpace<TestQuestion>(emptyQuestionSet);
		comparableObject = new Value<TestQuestion>(id, emptySpace);
	}

	private void whenComparedToNull() {
		comparableObject = null;
	}

	private void whenComparedToObject(Object object) {
		comparableObject = object;
	}

	private void thenIdIs(String id) {
		assertEquals(value.getId(), id);
	}

	private void thenQuestionsAre(String ... questions) {
		QuestionSpace<TestQuestion> questionSpace = value.getQuestionSpace();
		assertEquals(questionSpace.getQuestionCount(), questions.length);
		Iterator<TestQuestion> iterator = questionSpace.iterator();
		for (int i=0; i<questions.length; i++) {
			TestQuestion question = iterator.next();
			String actual = question.getValue();
			String expected = questions[i];
			assertEquals(expected, actual);
		}
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}

	private void thenTheyAreEqual() {
		assertTrue(value.equals(comparableObject));
		assertEquals(value.hashCode(), comparableObject.hashCode());
	}

	private void thenTheyAreNotEqual() {
		assertFalse(value.equals(comparableObject));
	}

	private TestQuestion createTestQuestion(String question) {
		return new TestQuestion(question);
	}

	private static class TestQuestion implements Question {

		private String value;

		public TestQuestion(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}

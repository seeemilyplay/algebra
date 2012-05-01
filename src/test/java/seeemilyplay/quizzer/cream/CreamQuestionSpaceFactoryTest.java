package seeemilyplay.quizzer.cream;

import java.util.Iterator;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import jp.ac.kobe_u.cs.cream.Solution;
import junit.framework.TestCase;

import seeemilyplay.quizzer.core.Question;
import seeemilyplay.quizzer.core.QuestionSpace;

public class CreamQuestionSpaceFactoryTest extends TestCase {

	private int lowerBound;
	private int upperBound;
	private CreamSpaceDefinition<TestQuestion> definition;
	private CreamQuestionSpaceFactory<TestQuestion> factory;
	private QuestionSpace<TestQuestion> questionSpace;

	protected void setUp() throws Exception {
		definition = new TestSpaceDefinition();
		questionSpace = null;
	}

	public void testQuestionSpaceGeneratesExpectedQuestions() {

		givenBounds(1, 3);

		whenQuestionSpaceIsCreated();

		thenValuesAre(1,2,3);
	}

	private void givenBounds(int lowerBound, int upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	private void whenQuestionSpaceIsCreated() {
		createFactory();
		createQuestionSpace();
	}

	private void createFactory() {
		factory = new CreamQuestionSpaceFactory<TestQuestion>(definition);
	}

	private void createQuestionSpace() {
		questionSpace = factory.createQuestionSpace();
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

	private static class TestSolutionInterpretter implements CreamSolutionInterpretter<TestQuestion> {

		private final IntVariable var;

		public TestSolutionInterpretter(IntVariable var) {
			this.var = var;
		}

		public TestQuestion convertToQuestion(Solution solution) {
			int value = getValue(solution);
			return new TestQuestion(value);
		}

		private int getValue(Solution solution) {
			return solution.getIntValue(var);
		}
	}

	private class TestSpaceDefinition implements CreamSpaceDefinition<TestQuestion> {

		public CreamSolutionInterpretter<TestQuestion> installConstraints(
				Network network) {
			IntVariable var = new IntVariable(network);
			var.ge(lowerBound);
			var.le(upperBound);
			return new TestSolutionInterpretter(var);
		}
	}
}

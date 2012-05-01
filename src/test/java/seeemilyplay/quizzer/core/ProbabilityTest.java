package seeemilyplay.quizzer.core;

import junit.framework.TestCase;

public class ProbabilityTest extends TestCase {

	private Probability probability;
	private Object comparisonObject;
	private Throwable t;
	private String toString;


	protected void setUp() throws Exception {
		super.setUp();
		probability = null;
		comparisonObject = null;
		t = null;
		toString = null;
	}


	public void testConstructionFailsWithNumberGreaterThan1() {

		whenConstructedWith(1.1);

		thenExceptionIsThrown();
	}

	public void testConstructionFailsWithNumberLessThan0() {

		whenConstructedWith(-0.1);

		thenExceptionIsThrown();
	}

	public void testProbabilityHasValueIsWasConstructedWith() {

		whenConstructedWith(0.5);

		thenProbabilityHasValue(0.5);
	}


	public void testStaticCreatorLimitsNumbersGreaterThan1To1() {

		whenStaticCreatedCalledWith(1.1);

		thenProbabilityHasValue(1.0);
	}

	public void testStaticCreatorLimitsNumbersLessThan0To0() {

		whenStaticCreatedCalledWith(-0.1);

		thenProbabilityHasValue(0.0);
	}

	public void testStaticCreatorPreservesNumbersBetween0And1() {

		whenStaticCreatedCalledWith(0.5);

		thenProbabilityHasValue(0.5);
	}

	public void testCertaintyHasProbability1() {

		whenCertainty();

		thenProbabilityHasValue(1.0);
	}

	public void testImpossibilityHasProbability0() {

		whenImpossibility();

		thenProbabilityHasValue(0.0);
	}

	public void testProbabilitiesAreNotEqualTo0() {

		givenProbability(0.5);

		whenComparedToNull();

		thenTheyAreNotEqual();
	}

	public void testProbabilitiesWithDifferentValuesAreNotEqual() {

		givenProbability(0.5);

		whenComparedToProbability(0.6);

		thenTheyAreNotEqual();
	}

	public void testProbabilitiesWithSameValuesAreEqual() {

		givenProbability(0.5);

		whenComparedToProbability(0.5);

		thenTheyAreEqual();
	}

	public void testProbabilitiesAreNotEqualToOtherObjects() {

		givenProbability(0.5);

		whenComparedTo("not a probability");

		thenTheyAreNotEqual();
	}

	public void testToStringReturnsValue() {

		givenProbability(0.5);

		whenToStringCalled();

		thenResultIs("0.5");
	}

	private void givenProbability(double value) {
		this.probability = new Probability(value);
	}

	private void whenConstructedWith(double value) {
		try {
			probability = new Probability(value);
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void whenStaticCreatedCalledWith(double value) {
		probability = Probability.createProbability(value);
	}

	private void whenCertainty() {
		probability = Probability.getCertainty();
	}

	private void whenImpossibility() {
		probability = Probability.getImpossibility();
	}

	private void whenComparedToNull() {
		comparisonObject = null;
	}

	private void whenComparedToProbability(double value) {
		comparisonObject = new Probability(value);
	}

	private void whenComparedTo(Object object) {
		comparisonObject = object;
	}

	private void whenToStringCalled() {
		toString = probability.toString();
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}

	private void thenProbabilityHasValue(double value) {
		assertEquals(probability.getValue(), value);
	}

	private void thenTheyAreEqual() {
		assertTrue(probability.equals(comparisonObject));
		assertEquals(probability.hashCode(), comparisonObject.hashCode());
	}

	private void thenTheyAreNotEqual() {
		assertFalse(probability.equals(comparisonObject));
	}

	private void thenResultIs(String toStringResult) {
		assertEquals(toString, toStringResult);
	}
}

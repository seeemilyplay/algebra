package seeemilyplay.quizzer.example;

import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.DescriptionParser;
import seeemilyplay.quizzer.cream.IntegerConstraints;
import seeemilyplay.quizzer.cream.IntegerRange;
import seeemilyplay.quizzer.cream.SingleInteger;

final class LinearEqParser implements DescriptionParser<LinearEquation> {

	private static final int MIN_M = -5;
	private static final int MAX_M = 5;
	private static final int MIN_C = -5;
	private static final int MAX_C = 5;

	private String description;

	public LinearEqParser() {
		super();
	}

	public CreamSpaceDefinition<LinearEquation> createSpaceDefinition(
			String description) {
		setDescription(description);
		return createDefinition();
	}

	private void setDescription(String description) {
		this.description = stripSpaces(description);
	}

	private String stripSpaces(String string) {
		return string.replace(" ", "");
	}

	private CreamSpaceDefinition<LinearEquation> createDefinition() {
		return new LinearEqSpaceDefinition(
				getConstraintsOnM(),
				getConstraintsOnC());
	}

	private IntegerConstraints getConstraintsOnM() {
		if (isMOne()) {
			return createConstraintsForMOne();
		} else if (isMMinusOne()) {
			return createConstraintsForMMinusOne();
		} else if (isMNegative()) {
			return createConstraintsForNegativeM();
		} else {
			return createConstraintsForPositiveM();
		}
	}

	private boolean isMOne() {
		return description.startsWith("x");
	}

	private boolean isMMinusOne() {
		return description.startsWith("-x");
	}

	private boolean isMNegative() {
		return description.startsWith("-");
	}

	private IntegerConstraints createConstraintsForMOne() {
		return new SingleInteger(1);
	}

	private IntegerConstraints createConstraintsForMMinusOne() {
		return new SingleInteger(1);
	}

	private IntegerConstraints createConstraintsForNegativeM() {
		return new IntegerRange(MIN_M, -1);
	}

	private IntegerConstraints createConstraintsForPositiveM() {
		return new IntegerRange(2, MAX_M);
	}

	private IntegerConstraints getConstraintsOnC() {
		if (isCZero()) {
			return createConstraintsForZeroC();
		} else if (isNegativeC()) {
			return createConstraintsForNegativeC();
		} else {
			return createConstraintsForPositiveC();
		}
	}

	private boolean isCZero() {
		return !description.contains("c");
	}

	private boolean isNegativeC() {
		return description.contains("-c");
	}

	private IntegerConstraints createConstraintsForZeroC() {
		return new SingleInteger(0);
	}

	private IntegerConstraints createConstraintsForNegativeC() {
		return new IntegerRange(MIN_C, -1);
	}

	private IntegerConstraints createConstraintsForPositiveC() {
		return new IntegerRange(1, MAX_C);
	}
}

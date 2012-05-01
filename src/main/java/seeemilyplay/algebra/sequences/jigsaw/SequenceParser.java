package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.DescriptionParser;
import seeemilyplay.quizzer.cream.IntegerConstraints;
import seeemilyplay.quizzer.cream.SingleInteger;

final class SequenceParser implements DescriptionParser<Sequence> {

	private static final String ARITHMETIC_SEQUENCE_PREFIX = "ap:";
	private static final String GEOMETRIC_SEQUENCE_PREFIX = "gp:";
	private static final String SQUARES_SEQUENCE_PREFIX = "sq:";
	
	private static final String FIRST_TERM_VAR_NAME = "1st";
	private static final String DIFFERENCE_VAR_NAME = "d";
	
	private String description;
	private VariableLineParser variableLineParser;
	
	
	public SequenceParser() {
		super();
	}
	
	public CreamSpaceDefinition<Sequence> createSpaceDefinition(
			String description) {
		init(description);

		return parseSpaceDefinition();
	}
	
	private void init(String description) {
		this.description = description;
		this.variableLineParser = new VariableLineParser(description);
	}
	
	private CreamSpaceDefinition<Sequence> parseSpaceDefinition() {
		return new SequenceSpaceDefinition(
				parseTypeConstraints(), 
				parseFirstTermConstraints(), 
				parseDifferenceConstraints());
	}
	
	private IntegerConstraints parseTypeConstraints() {
		return new SingleInteger(parseType().ordinal());
	}
	
	private PuzzleType parseType() {
		if (isArithmeticSequence()) {
			return PuzzleType.ARITHMETIC;
		} else if (isGeometricSequence()) {
			return PuzzleType.GEOMETRIC;
		} else if (isSquaresSequence()) {
			return PuzzleType.SQUARES;
		}
		throw new Error();
	}
	
	private boolean isArithmeticSequence() {
		return isPrefixedBy(ARITHMETIC_SEQUENCE_PREFIX);
	}
	
	private boolean isGeometricSequence() {
		return isPrefixedBy(GEOMETRIC_SEQUENCE_PREFIX);
	}
	
	private boolean isSquaresSequence() {
		return isPrefixedBy(SQUARES_SEQUENCE_PREFIX);
	}
	
	private boolean isPrefixedBy(String prefix) {
		return description.startsWith(prefix);
	}
	
	private IntegerConstraints parseFirstTermConstraints() {
		return parseVariableConstraints(FIRST_TERM_VAR_NAME);
	}
	
	private IntegerConstraints parseDifferenceConstraints() {
		if (isSquaresSequence()) {
			return createEmptyConstraints();
		}
		return parseVariableConstraints(DIFFERENCE_VAR_NAME);
	}
	
	private IntegerConstraints createEmptyConstraints() {
		return new SingleInteger(0);
	}
	
	private IntegerConstraints parseVariableConstraints(
			String variableName) {
		return variableLineParser.parseVariableConstraints(
				variableName);
	}
}

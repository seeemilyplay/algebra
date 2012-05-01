package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.DescriptionParser;
import seeemilyplay.quizzer.cream.IntegerConstraints;
import seeemilyplay.quizzer.cream.SingleInteger;

final class PuzzleParser implements DescriptionParser<Puzzle> {
	
	private static final String HIDDEN_PIECES_COUNT = "h";
	
	private final SequenceParser sequenceParser;
	
	private String description;
	private VariableLineParser variableLineParser;
	
	
	public PuzzleParser() {
		this.sequenceParser = new SequenceParser();
	}
	
	public CreamSpaceDefinition<Puzzle> createSpaceDefinition(
			String description) {
		init(description);
	
		return createPuzzleSpaceDefinition();
	}
	
	private void init(String description) {
		this.description = description;
		this.variableLineParser = new VariableLineParser(description);
	}
	
	private CreamSpaceDefinition<Puzzle> createPuzzleSpaceDefinition() {
		return new PuzzleSpaceDefinition(
				createSequenceSpaceDefinition(), 
				parseHiddenPiecesCountConstraints(), 
				parseIsDefinedUsingNConstraints());
	}

	private CreamSpaceDefinition<Sequence> createSequenceSpaceDefinition() {
		return sequenceParser.createSpaceDefinition(description);
	}
	
	private IntegerConstraints parseHiddenPiecesCountConstraints() {
		return variableLineParser.parseVariableConstraints(
				HIDDEN_PIECES_COUNT,
				new SingleInteger(1));
	}
	
	private IntegerConstraints parseIsDefinedUsingNConstraints() {
		return new SingleInteger(isDefinedUsingN() ? 1 : 0);
	}
	
	private boolean isDefinedUsingN() {
		return contains(Boolean.TRUE.toString());
	}
	
	private boolean contains(String string) {
		return description.contains(string);
	}
}


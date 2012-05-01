package seeemilyplay.algebra.sequences.jigsaw;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;
import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.IntegerConstraints;

final class PuzzleSpaceDefinition implements CreamSpaceDefinition<Puzzle> {

	private final CreamSpaceDefinition<Sequence> sequenceSpaceDefinition;
	private final IntegerConstraints constraintsOnHiddenPieceCount;
	private final IntegerConstraints constraintsOnIsDefinedUsingN;

	public PuzzleSpaceDefinition(
			CreamSpaceDefinition<Sequence> sequenceSpaceDefinition,
			IntegerConstraints constraintsOnHiddenPieceCount,
			IntegerConstraints constraintsOnIsDefinedUsingN) {
		this.sequenceSpaceDefinition = sequenceSpaceDefinition;
		this.constraintsOnHiddenPieceCount = constraintsOnHiddenPieceCount;
		this.constraintsOnIsDefinedUsingN = constraintsOnIsDefinedUsingN;
	}

	public CreamSolutionInterpretter<Puzzle> installConstraints(
			Network network) {

		IntVariable hiddenPieceCount = new IntVariable(network);
		IntVariable isDefinedUsingN = new IntVariable(network);

		constraintsOnHiddenPieceCount.install(hiddenPieceCount);
		constraintsOnIsDefinedUsingN.install(isDefinedUsingN);

		
		return new PuzzleFactory(
				createSequenceFactory(network),
				hiddenPieceCount,
				isDefinedUsingN);
	}
	
	private CreamSolutionInterpretter<Sequence> createSequenceFactory(Network network) {
		return sequenceSpaceDefinition.installConstraints(network);
	}
}

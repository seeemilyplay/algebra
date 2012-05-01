package seeemilyplay.algebra.sequences.jigsaw;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Solution;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;

final class PuzzleFactory implements CreamSolutionInterpretter<Puzzle> {

	private final CreamSolutionInterpretter<Sequence> sequenceFactory;
	private final IntVariable hiddenPieceCount;
	private final IntVariable isDefinedUsingN;

	public PuzzleFactory(
			CreamSolutionInterpretter<Sequence> sequenceFactory,
			IntVariable hiddenPieceCount,
			IntVariable isDefinedUsingN) {
		this.sequenceFactory = sequenceFactory;
		this.hiddenPieceCount = hiddenPieceCount;
		this.isDefinedUsingN = isDefinedUsingN;
	}

	public Puzzle convertToQuestion(Solution solution) {
		return new Puzzle(
				createSequence(solution), 
				getHiddenPieceCount(solution),
				isDefinedUsingN(solution));
	}
	
	private Sequence createSequence(Solution solution) {
		return sequenceFactory.convertToQuestion(solution);
	}

	private int getHiddenPieceCount(Solution solution) {
		return solution.getIntValue(hiddenPieceCount);
	}
	
	private boolean isDefinedUsingN(Solution solution) {
		int value = solution.getIntValue(isDefinedUsingN);
		return (value>0);
	}
}

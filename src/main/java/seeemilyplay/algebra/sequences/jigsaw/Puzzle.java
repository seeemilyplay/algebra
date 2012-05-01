package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.quizzer.core.Question;

final class Puzzle implements Question {

	private final Sequence sequence;
	private final int hiddenPieceCount;
	private final boolean isDefinedUsingN;
	
	public Puzzle(
			Sequence sequence, 
			int hiddenPieceCount,
			boolean isDefinedUsingN) {
		this.sequence = sequence;
		this.hiddenPieceCount = hiddenPieceCount;
		this.isDefinedUsingN = isDefinedUsingN;
	}
	
	public Sequence getSequence() {
		return sequence;
	}
	
	public int getHiddenPieceCount() {
		return hiddenPieceCount;
	}
	
	public boolean isDefinedUsingN() {
		return isDefinedUsingN;
	}
}

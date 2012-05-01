package seeemilyplay.algebra.sequences.jigsaw;

final class ArithmeticSequence implements Sequence {

	private final int firstTerm;
	private final int difference;
	
	public ArithmeticSequence(int firstTerm, int difference) {
		this.firstTerm = firstTerm;
		this.difference = difference;
	}
	
	public int getNthTerm(int n) {
		return firstTerm * n + difference;
	}
}

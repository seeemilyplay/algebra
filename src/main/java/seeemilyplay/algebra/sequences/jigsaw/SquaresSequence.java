package seeemilyplay.algebra.sequences.jigsaw;

final class SquaresSequence implements Sequence {
	
	private final int firstN;
	
	public SquaresSequence(int firstN) {
		this.firstN = firstN;
	}

	public int getNthTerm(int n) {
		return (int)Math.pow(firstN + n - 1, 2);
	}
}

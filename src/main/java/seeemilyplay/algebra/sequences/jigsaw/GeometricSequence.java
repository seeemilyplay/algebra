package seeemilyplay.algebra.sequences.jigsaw;

final class GeometricSequence implements Sequence {
	
	private final int firstTerm;
	private final int difference;
	
	public GeometricSequence(int firstTerm, int difference) {
		this.firstTerm = firstTerm;
		this.difference = difference;
	}
	
	public int getNthTerm(int n) {
		return firstTerm * (int)Math.pow(difference, n-1);
	}
}

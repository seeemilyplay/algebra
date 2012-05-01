package seeemilyplay.algebra.sequences.crossword;

final class Square {

	private boolean isEmpty;
	private int value;
	private boolean isGiven;

	private Square(
			boolean isEmpty,
			int value,
			boolean isGiven) {
		this.isEmpty = isEmpty;
		this.value = value;
		this.isGiven = isGiven;
	}

	public static Square createEmptySquare() {
		return new Square(true, 0, false);
	}

	public static Square createGivenSquare(int value) {
		return new Square(false, value, true);
	}

	public static Square createQuestionSquare(int value) {
		return new Square(false, value, false);
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public int getValue() {
		return value;
	}

	public boolean isGiven() {
		return isGiven;
	}

	public boolean isQuestion() {
		return !isEmpty && !isGiven;
	}
}

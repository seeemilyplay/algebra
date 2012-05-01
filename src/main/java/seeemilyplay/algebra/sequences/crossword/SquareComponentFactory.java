package seeemilyplay.algebra.sequences.crossword;

import javax.swing.JComponent;

final class SquareComponentFactory {

	private final SquareModel squareModel;

	public SquareComponentFactory(SquareModel squareModel) {
		this.squareModel = squareModel;
	}

	public JComponent createSquareComponent() {
		if (isEmpty()) {
			return createEmptySquareComponent();
		} else if (isGiven()) {
			return createGivenSquareComponent();
		} else {
			return createQuestionSquareComponent();
		}
	}

	private boolean isEmpty() {
		return getSquare().isEmpty();
	}

	private JComponent createEmptySquareComponent() {
		return new EmptySquareComponent();
	}

	private boolean isGiven() {
		return getSquare().isGiven();
	}

	private JComponent createGivenSquareComponent() {
		return new GivenSquareComponent(squareModel);
	}

	private Square getSquare() {
		return squareModel.getSquare();
	}

	private JComponent createQuestionSquareComponent() {
		return new QuestionSquareComponent(squareModel);
	}
}

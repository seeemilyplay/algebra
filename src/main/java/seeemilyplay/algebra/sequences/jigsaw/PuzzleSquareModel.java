package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.core.swing.SwingModel;

final class PuzzleSquareModel extends SwingModel {

	private final int value;
	
	private Integer answerValue;
	
	public PuzzleSquareModel(
			int value,
			boolean isAnsweredCorrectly) {
		this.value = value;
		this.answerValue = (isAnsweredCorrectly ? value : null);
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isAnsweredCorrectly() {
		return (isAnswered() && value==answerValue);
	}
	
	public boolean isAnsweredIncorrectly() {
		return (isAnswered() && value!=answerValue);
	}
	
	private boolean isAnswered() {
		return answerValue!=null;
	}
	
	public void clearAnswer() {
		answerValue = null;
		fireChange();
	}
	
	public void setAnswerValue(int answerValue) {
		this.answerValue = answerValue;
		fireChange();
	}
}

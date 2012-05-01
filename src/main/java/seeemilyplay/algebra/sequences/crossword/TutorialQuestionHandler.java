package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.algebra.components.arrow.ArrowDirection;
import seeemilyplay.algebra.components.floating.FloatPosition;

interface TutorialQuestionHandler {

	public String getHintText();

	public String getCorrectionText();

	public FloatPosition getInfoPosition();

	public ArrowDirection getArrowDirection();
}

package seeemilyplay.algebra.sequences.crossword;

import java.util.ArrayList;
import java.util.List;

import seeemilyplay.algebra.sequences.crossword.text.CrosswordPhrases;
import seeemilyplay.algebra.components.arrow.ArrowDirection;
import seeemilyplay.algebra.components.floating.FloatPosition;
import seeemilyplay.core.international.text.PhraseBook;

final class TutorialQuestionHandlers {

	private final List<TutorialQuestionHandler> tutorialQuestionHandlers =
		new ArrayList<TutorialQuestionHandler>();

	public TutorialQuestionHandlers() {
		super();
		init();
	}

	private void init() {
		tutorialQuestionHandlers.add(new FirstQuestion());
		tutorialQuestionHandlers.add(new SecondQuestion());
		tutorialQuestionHandlers.add(new ThirdQuestion());
		tutorialQuestionHandlers.add(new FourthQuestion());
	}

	public TutorialQuestionHandler get(int questionIndex) {
		return tutorialQuestionHandlers.get(questionIndex);
	}

	private class FirstQuestion implements TutorialQuestionHandler {

		public String getHintText() {
			return getPhrases().getTutorialQ1Hint();
		}

		public String getCorrectionText() {
			return getPhrases().getTutorialQ1Correction();
		}

		public FloatPosition getInfoPosition() {
			return FloatPosition.NORTH_WEST;
		}

		public ArrowDirection getArrowDirection() {
			return ArrowDirection.NORTH_WEST;
		}
	}

	private class SecondQuestion implements TutorialQuestionHandler {

		public String getHintText() {
			return getPhrases().getTutorialQ2Hint();
		}

		public String getCorrectionText() {
			return getPhrases().getTutorialQ2Correction();
		}

		public FloatPosition getInfoPosition() {
			return FloatPosition.WEST;
		}

		public ArrowDirection getArrowDirection() {
			return ArrowDirection.NORTH_WEST;
		}
	}

	private class ThirdQuestion implements TutorialQuestionHandler {

		public String getHintText() {
			return getPhrases().getTutorialQ3Hint();
		}

		public String getCorrectionText() {
			return getPhrases().getTutorialQ3Correction();
		}

		public FloatPosition getInfoPosition() {
			return FloatPosition.EAST;
		}

		public ArrowDirection getArrowDirection() {
			return ArrowDirection.SOUTH_EAST;
		}
	}

	private class FourthQuestion implements TutorialQuestionHandler {

		public String getHintText() {
			return getPhrases().getTutorialQ4Hint();
		}

		public String getCorrectionText() {
			return getPhrases().getTutorialQ4Correction();
		}

		public FloatPosition getInfoPosition() {
			return FloatPosition.SOUTH;
		}

		public ArrowDirection getArrowDirection() {
			return ArrowDirection.NORTH;
		}
	}

	private CrosswordPhrases getPhrases() {
		return PhraseBook.getPhrases(CrosswordPhrases.class);
	}
}

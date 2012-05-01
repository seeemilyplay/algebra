package seeemilyplay.algebra.sequences.crossword;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.base.PlayButton;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class TutorialModel extends SwingModel {

	private final BaseGameModel baseGameModel;
	private final Crossword crossword;
	private final CrosswordModel crosswordModel;

	private final List<SquareModel> questions  =
		new ArrayList<SquareModel>();

	private Set<SquareModel> pastQuestionsWithWarnings = new HashSet<SquareModel>();
	
	private boolean isSeenIntro;

	private Listener crosswordListener;

	public TutorialModel(BaseGameModel baseGameModel) {
		this.baseGameModel = baseGameModel;
		this.crossword = TutorialCrossword.getInstance();
		this.crosswordModel = new CrosswordModel(crossword);

		initQuestions();
		initCrosswordListener();
	}

	private void initQuestions() {
		initQuestion(0, 2);
		initQuestion(3, 4);
		initQuestion(3, 7);
		initQuestion(5, 4);
	}

	private void initQuestion(int row, int col) {
		Square square = crossword.getSquare(row, col);
		SquareModel squareModel = crosswordModel.getSquareModel(square);
		questions.add(squareModel);
	}

	private void initCrosswordListener() {
		crosswordListener = new Listener() {
			public void onChange() {
				fireChangeAndRefocus();
			}
		};
		crosswordModel.addListener(crosswordListener);
	}
	
	public JComponent createPlayButton() {
		return new PlayButton(baseGameModel);
	}

	public CrosswordModel getCrosswordModel() {
		return crosswordModel;
	}

	public boolean isSeenIntro() {
		return isSeenIntro;
	}

	public void setSeenIntro() {
		isSeenIntro = true;
		fireChangeAndRefocus();
	}

	public boolean isFinished() {
		return (crosswordModel.getCorrectAnswerCount()
				==crosswordModel.getQuestionCount());
	}

	public SquareModel getQuestion() {
		SquareModel warningQuestion = getFirstQuestionWithWarning();
		if (warningQuestion!=null) {
			return warningQuestion;
		}
		return getFirstQuestionNotCorrectlyAnswered();
	}

	private SquareModel getFirstQuestionWithWarning() {
		for (SquareModel question : questions) {
			if (question.isWarningDisplayed()) {
				return question;
			}
		}
		return null;
	}
	
	public boolean isWarningDisplayed() {
		if (isCurrentWarningDisplayed()) {
			flagAsWarningDisplayedInPast();
			return true;
		}
		return isWarningDisplayedInPast();
	}
	
	private boolean isCurrentWarningDisplayed() {
		return getQuestion().isWarningDisplayed();
	}
	
	private void flagAsWarningDisplayedInPast() {
		pastQuestionsWithWarnings.add(getQuestion());
	}
	
	private boolean isWarningDisplayedInPast() {
		return pastQuestionsWithWarnings.contains(getQuestion());
	}

	private SquareModel getFirstQuestionNotCorrectlyAnswered() {
		for (SquareModel question : questions) {
			if (!question.isCorrectlyAnswered()) {
				return question;
			}
		}
		return null;
	}
	
	private void fireChangeAndRefocus() {
		fireChange();
		refocus();
	}
	
	private void refocus() {
		if (isSeenIntro() && !isFinished()) {
			requestFocusOnQuestion();
		}
	}
	
	private void requestFocusOnQuestion() {
		crosswordModel.requestFocus(getQuestion().getSquare());
	}

	public int getQuestionIndex() {
		return getIndex(getQuestion());
	}

	private int getIndex(SquareModel squareModel) {
		return questions.indexOf(squareModel);
	}

	public void playGame() {
		baseGameModel.setPlayingGame();
	}
}

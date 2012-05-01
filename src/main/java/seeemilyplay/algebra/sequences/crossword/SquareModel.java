package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.DelayedListener;
import seeemilyplay.core.swing.SwingModel;

final class SquareModel extends SwingModel {

	private static final long DELAY = 2000;

	private final Square square;
	private final SquareFocusManager focusManager;

	private DelayedChangeFirer delayedChangeFirer =
		new DelayedChangeFirer();

	private Integer answer;
	private boolean isMustDisplayAnyWarnings;
	private long lastUpdateTime;


	public SquareModel(
			Square square,
			SquareFocusManager focusManager) {
		this.square = square;
		this.focusManager = focusManager;
	}

	public Square getSquare() {
		return square;
	}

	public SquareFocusManager getFocusManager() {
		return focusManager;
	}

	public void clearAnswer() {
		answer = null;
		onAnswerChange();
		fireChange();
	}

	public void setAnswer(int answer) {
		this.answer = answer;
		onAnswerChange();
		fireChange();
	}

	private void onAnswerChange() {
		isMustDisplayAnyWarnings = false;
		lastUpdateTime = System.currentTimeMillis();
		fireChange();
		fireDelayedChange();
	}

	public boolean isCorrectlyAnswered() {
		return (isAnswered()
				&& answer==square.getValue());
	}

	public boolean isIncorrectlyAnswered() {
		return (isAnswered()
				&& answer!=square.getValue());
	}

	private boolean isAnswered() {
		return answer!=null;
	}

	public int getAnswer() {
		return answer;
	}

	public void setMustDisplayAnyWarnings() {
		isMustDisplayAnyWarnings = true;
		fireChange();
	}

	public boolean isWarningDisplayed() {
		return isWarning() && shouldDisplayAnyWarnings();
	}

	private boolean isWarning() {
		return isQuestion() && isIncorrectlyAnswered();
	}

	private boolean isQuestion() {
		return square.isQuestion();
	}

	private boolean shouldDisplayAnyWarnings() {
		return (isMustDisplayAnyWarnings
				|| isSomeTimeSinceLastUpdate());
	}

	private boolean isSomeTimeSinceLastUpdate() {
		return getTimeSinceLastUpdate()>=getSomeTime();
	}

	private long getTimeSinceLastUpdate() {
		if (lastUpdateTime==0) {
			return 0;
		} else {
			long now = System.currentTimeMillis();
			return now - lastUpdateTime;
		}
	}

	private long getSomeTime() {
		return DELAY;
	}

	private void fireDelayedChange() {
		delayedChangeFirer.fire();
	}

	private class DelayedChangeFirer {

		private Listener delayedListener;

		public DelayedChangeFirer() {
			super();
			initDelayedListener();
		}

		private void initDelayedListener() {
			delayedListener = new DelayedListener(DELAY) {
				public void afterDelay() {
					fireChange();
				}
			};
		}

		public void fire() {
			delayedListener.onChange();
		}
	}
}

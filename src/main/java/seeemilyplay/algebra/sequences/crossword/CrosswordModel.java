package seeemilyplay.algebra.sequences.crossword;

import java.util.HashMap;
import java.util.Map;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import seeemilyplay.algebra.components.fading.FadingModel;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

public final class CrosswordModel extends SwingModel implements FadingModel {

	private static final String OPACITY = "opacity";
	private static final int FADE_DURATION = 1000;
	private static final float MIN_OPACITY = 0.1f;
	private static final float MAX_OPACITY = 1f;
	private static final float ACCELERATION = 0.25f;
	private static final float DECELERATION = 0.25f;
	
	private final Crossword crossword;
	private final CrosswordFocusCoordinator focusCoordinator;
	private final Map<Square,SquareModel> squareModels =
		new HashMap<Square,SquareModel>();

	private int questionCount;
	private Listener squareModelListener;
	
	private float opacity = MIN_OPACITY;
	private boolean isFading;


	public CrosswordModel(
			Crossword crossword) {
		this.crossword = crossword;
		focusCoordinator = new CrosswordFocusCoordinator(crossword);

		initQuestionCount();
		initSquareModelListener();
		initSquareModels();
		fadeIn();
	}

	private void initQuestionCount() {
		for (Square square : crossword) {
			if (square.isQuestion()) {
				questionCount++;
			}
		}
	}

	private void initSquareModelListener() {
		squareModelListener = new Listener() {
			public void onChange() {
				fadeOutIfRequired();
				fireChange();
			}
		};
	}

	private void initSquareModels() {
		for (Square square : crossword) {
			initSquareModel(square);
		}
	}

	private void initSquareModel(Square square) {
		SquareFocusManager focusManager = new SquareFocusManager(
				square,
				focusCoordinator);
		SquareModel squareModel = new SquareModel(square, focusManager);
		squareModels.put(square, squareModel);
		squareModel.addListener(squareModelListener);
	}
	
	private void fadeOutIfRequired() {
		if (!isFading && isCompletelyAnswered()) {
			fadeOut();
		}
	}
	
	private void fadeOut() {
		animateOpacityChange(MIN_OPACITY);
	}
	
	private void fadeIn() {
		animateOpacityChange(MAX_OPACITY);
	}
	
	private void animateOpacityChange(float targetOpacity) {
		isFading = true;
		Animator animator = PropertySetter.createAnimator(
				FADE_DURATION,
				this,
				OPACITY,
				new Float[] {getOpacity(), targetOpacity});
		animator.setAcceleration(ACCELERATION);
		animator.setDeceleration(DECELERATION);
		animator.addTarget(new TimingTargetAdapter() {
			public void end() {
				isFading = false;
				fadeOutIfRequired();
			}
		});
		animator.start();
	}
	
	public float getOpacity() {
		return opacity;
	}
	
	public void setOpacity(float opacity) {
		this.opacity = opacity;
		fireChange();
	}
	
	public boolean isFaded() {
		return (opacity<MAX_OPACITY);
	}

	public Crossword getCrossword() {
		return crossword;
	}
	
	public SquareModel getSquareModel(Square square) {
		return squareModels.get(square);
	}
	
	public void requestFocus(Square square) {
		focusCoordinator.setDesiredFocusHolder(square);
	}
	
	public boolean isComplete() {
		return (isCompletelyAnswered() && isFadedOut());
	}
	
	private boolean isCompletelyAnswered() {
		return (getCorrectAnswerCount()
				==getQuestionCount());
	}
	
	private boolean isFadedOut() {
		return opacity==MIN_OPACITY;
	}

	public int getCorrectAnswerCount() {
		int answerCount = 0;
		for (Square square : crossword) {
			SquareModel squareModel = getSquareModel(square);
			if (squareModel.isCorrectlyAnswered()) {
				answerCount++;
			}
		}
		return answerCount;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public boolean isWarningDisplayed() {
		for (Square square : crossword) {
			SquareModel squareModel = getSquareModel(square);
			if (squareModel.isWarningDisplayed()) {
				return true;
			}
		}
		return false;
	}
}

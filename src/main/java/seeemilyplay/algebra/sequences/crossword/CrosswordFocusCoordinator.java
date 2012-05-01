package seeemilyplay.algebra.sequences.crossword;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import seeemilyplay.core.swing.SwingModel;

final class CrosswordFocusCoordinator extends SwingModel {

	private final Crossword crossword;
	private final NearestSquareFinder nearestSquareFinder;

	private Square focusHolder;
	private Square desiredFocusHolder;

	public CrosswordFocusCoordinator(Crossword crossword) {
		this.crossword = crossword;
		this.nearestSquareFinder =
			new NearestSquareFinder(crossword);
		
		focusOnFirstQuestion();
	}

	public void setFocusHolder(Square focusHolder) {
		this.focusHolder = focusHolder;
		desiredFocusHolder = null;
		fireChangeUntilFocusIsAsDesired();
	}
	
	public void setDesiredFocusHolder(Square desiredFocusHolder) {
		this.desiredFocusHolder = desiredFocusHolder;
		fireChangeUntilFocusIsAsDesired();
	}

	public Square getDesiredFocusHolder() {
		return desiredFocusHolder;
	}

	public void moveFocusRight() {
		moveFocus(SwingConstants.EAST);
	}

	public void moveFocusLeft() {
		moveFocus(SwingConstants.WEST);
	}

	public void moveFocusUp() {
		moveFocus(SwingConstants.NORTH);
	}

	public void moveFocusDown() {
		moveFocus(SwingConstants.SOUTH);
	}
	
	private void focusOnFirstQuestion() {
		setDesiredFocusHolder(
				getFirstQuestion());
	}
	
	private Square getFirstQuestion() {
		for (Square square : crossword) {
			if (square.isQuestion()) {
				return square;
			}
		}
		throw new IllegalStateException();
	}

	private void moveFocus(int direction) {
		setDesiredFocusHolder(nearestSquareFinder.getNearest(
				focusHolder,
				direction));
	}
	
	private void fireChangeUntilFocusIsAsDesired() {
		fireChange();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!isFocusAsDesired()) {
					fireChangeUntilFocusIsAsDesired();
				}
			}
		});
	}
	
	private boolean isFocusAsDesired() {
		return (desiredFocusHolder==null 
				|| desiredFocusHolder.equals(focusHolder));
	}
}

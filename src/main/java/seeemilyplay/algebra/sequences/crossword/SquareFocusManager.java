package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class SquareFocusManager extends SwingModel {

	private final Square square;
	private final CrosswordFocusCoordinator focusCoordinator;

	private Listener coordinatorListener;

	public SquareFocusManager(
			Square square,
			CrosswordFocusCoordinator focusCoordinator) {
		this.square = square;
		this.focusCoordinator = focusCoordinator;

		initCoordinatorListener();
	}

	private void initCoordinatorListener() {
		coordinatorListener = new Listener() {
			public void onChange() {
				fireChange();
			}
		};
		focusCoordinator.addListener(coordinatorListener);
		fireChange();
	}

	public void setFocusHolder() {
		focusCoordinator.setFocusHolder(square);
	}

	public boolean isDesiredFocusHolder() {
		return square.equals(getDesiredFocusHolder());
	}

	private Square getDesiredFocusHolder() {
		return focusCoordinator.getDesiredFocusHolder();
	}

	public void moveFocusRight() {
		focusCoordinator.moveFocusRight();
	}

	public void moveFocusLeft() {
		focusCoordinator.moveFocusLeft();
	}

	public void moveFocusUp() {
		focusCoordinator.moveFocusUp();
	}

	public void moveFocusDown() {
		focusCoordinator.moveFocusDown();
	}
}

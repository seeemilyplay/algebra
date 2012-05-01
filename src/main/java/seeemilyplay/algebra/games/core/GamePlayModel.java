package seeemilyplay.algebra.games.core;

import seeemilyplay.core.swing.SwingModel;

public final class GamePlayModel extends SwingModel {

	private boolean isComplete;

	public GamePlayModel() {
		super();
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void complete() {
		this.isComplete = true;
		fireChange();
	}
}

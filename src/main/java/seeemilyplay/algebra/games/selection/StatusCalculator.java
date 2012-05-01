package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.progress.Level;

interface StatusCalculator<T> {

	public boolean isUnlocked(T id);

	public Level getLevel(T id);
}

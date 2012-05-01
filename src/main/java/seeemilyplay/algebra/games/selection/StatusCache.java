package seeemilyplay.algebra.games.selection;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.progress.Level;

final class StatusCache<T> {

	private final StatusCalculator<T> statusCalculator;

	private final Map<T,Level> levels =
		new HashMap<T,Level>();
	private final Map<T,Boolean> unlockedFlags =
		new HashMap<T,Boolean>();

	public StatusCache(
			StatusCalculator<T> statusCalculator) {
		this.statusCalculator = statusCalculator;
	}

	public void clear() {
		clearLevels();
		clearUnlockedFlags();
	}

	private void clearLevels() {
		levels.clear();
	}

	private void clearUnlockedFlags() {
		unlockedFlags.clear();
	}

	private boolean isLevelCalculationRequired(T id) {
		return !levels.containsKey(id);
	}

	private boolean isUnlockedCalculationRequired(T id) {
		return !unlockedFlags.containsKey(id);
	}

	private void calculateLevelIfRequired(T id) {
		if (isLevelCalculationRequired(id)) {
			calculateLevel(id);
		}
	}

	private void calculateUnlockedIfRequired(T id) {
		if (isUnlockedCalculationRequired(id)) {
			calculateUnlocked(id);
		}
	}

	private void calculateLevel(T id) {
		Level level = statusCalculator.getLevel(id);
		levels.put(id, level);
	}

	private void calculateUnlocked(T id) {
		boolean isUnlocked = statusCalculator.isUnlocked(id);
		unlockedFlags.put(id, isUnlocked);
	}

	public boolean isUnlocked(T id) {
		calculateUnlockedIfRequired(id);
		return unlockedFlags.get(id);
	}

	public Level getLevel(T id) {
		calculateLevelIfRequired(id);
		return levels.get(id);
	}
}

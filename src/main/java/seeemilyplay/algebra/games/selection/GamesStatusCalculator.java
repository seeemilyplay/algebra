package seeemilyplay.algebra.games.selection;

import java.util.Set;

import seeemilyplay.algebra.games.core.Dependencies;
import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.progress.Level;

final class GamesStatusCalculator implements StatusCalculator<GameId> {

	private static final int PASS_LEVEL = Level.BEGINNER.ordinal();

	private final ApplicationModel applicationModel;
	private final Dependencies dependencies;

	public GamesStatusCalculator(
			ApplicationModel applicationModel,
			Dependencies dependencies) {
		this.applicationModel = applicationModel;
		this.dependencies = dependencies;
	}

	public boolean isUnlocked(GameId gameId) {
		return (isPassed(gameId)
				|| areAllParentsPassed(gameId));
	}

	private boolean isPassed(GameId gameId) {
		Level level = getLevel(gameId);
		return level.ordinal()>PASS_LEVEL;
	}

	public Level getLevel(GameId gameId) {
		return applicationModel.getLevel(gameId);
	}

	private boolean areAllParentsPassed(GameId gameId) {
		for (GameId parent : getParents(gameId)) {
			if (!isPassed(parent)) {
				return false;
			}
		}
		return true;
	}

	private Set<GameId> getParents(GameId gameId) {
		return dependencies.getParents(gameId);
	}
}

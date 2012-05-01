package seeemilyplay.algebra.games.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GameSettings {

	private final CategoryId categoryId;
	private final GameId gameId;
	private final List<GameId> dependencies;

	public GameSettings(
			CategoryId categoryId,
			GameId gameId,
			List<GameId> dependencies) {
		this.categoryId = categoryId;
		this.gameId = gameId;
		this.dependencies = Collections.unmodifiableList(
				new ArrayList<GameId>(dependencies));
	}

	public CategoryId getCategoryId() {
		return categoryId;
	}

	public GameId getGameId() {
		return gameId;
	}

	public List<GameId> getDependencies() {
		return dependencies;
	}
}

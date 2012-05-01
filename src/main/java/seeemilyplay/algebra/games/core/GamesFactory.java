package seeemilyplay.algebra.games.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class GamesFactory {

	private final Set<GameId> games = new HashSet<GameId>();

	private Dependencies dependencies;

	public GamesFactory() {
		super();
	}

	public void addGame(GameId game) {
		games.add(game);
	}

	public Games createGames(Dependencies dependencies) {
		init(dependencies);

		return new Games(getGamesByLevel());
	}

	private void init(Dependencies dependencies) {
		this.dependencies = dependencies;
	}

	private List<List<GameId>> getGamesByLevel() {
		List<List<GameId>> gamesByLevel = new ArrayList<List<GameId>>();
		for (int level=0; isGamesForLevel(level); level++) {
			List<GameId> gamesForLevel = getGamesForLevel(level);
			gamesByLevel.add(gamesForLevel);
		}
		return Collections.unmodifiableList(gamesByLevel);
	}

	private boolean isGamesForLevel(int level) {
		return !getGamesForLevel(level).isEmpty();
	}

	private List<GameId> getGamesForLevel(int level) {
		List<GameId> gamesForLevel = new ArrayList<GameId>();
		for (GameId gameId : games) {
			if (isLevel(gameId, level)) {
				gamesForLevel.add(gameId);
			}
		}
		return Collections.unmodifiableList(gamesForLevel);
	}

	private boolean isLevel(GameId gameId, int level) {
		return getLevel(gameId)==level;
	}

	private int getLevel(GameId gameId) {
		int level = 0;
		for (GameId parent : getParents(gameId)) {
			int parentLevel = getLevel(parent);
			level = Math.max(level, parentLevel+1);
		}
		return level;
	}

	private Set<GameId> getParents(GameId gameId) {
		return dependencies.getParents(gameId);
	}
}

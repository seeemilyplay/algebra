package seeemilyplay.algebra.games.core;

public final class GamesMap {

	private final Games games;
	private final Dependencies dependencies;

	GamesMap(Games games, Dependencies dependencies) {
		this.games = games;
		this.dependencies = dependencies;
	}

	public Games getGames() {
		return games;
	}

	public Dependencies getDependencies() {
		return dependencies;
	}
}

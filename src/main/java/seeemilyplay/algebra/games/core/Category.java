package seeemilyplay.algebra.games.core;

public final class Category {

	private final CategoryId id;
	private final GamesMap gamesMap;

	Category(CategoryId id, GamesMap gamesMap) {
		this.id = id;
		this.gamesMap = gamesMap;
	}

	public CategoryId getId() {
		return id;
	}

	public GamesMap getGamesMap() {
		return gamesMap;
	}
}

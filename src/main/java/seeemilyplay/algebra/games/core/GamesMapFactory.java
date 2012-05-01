package seeemilyplay.algebra.games.core;



final class GamesMapFactory {

	private final GamesFactory gamesFactory =
		new GamesFactory();
	private final DependenciesFactory dependenciesFactory =
		new DependenciesFactory();

	public GamesMapFactory() {
		super();
	}

	public void addGame(GameSettings gameSettings) {
		GameId gameId = gameSettings.getGameId();
		gamesFactory.addGame(gameId);
		addDependencies(gameSettings);
	}

	private void addDependencies(GameSettings gameSettings) {
		GameId to = gameSettings.getGameId();
		for (GameId from : gameSettings.getDependencies()) {
			dependenciesFactory.addDependency(from, to);
		}
	}

	public GamesMap createGamesMap() {
		Dependencies dependencies = dependenciesFactory.createDependencies();
		Games games = gamesFactory.createGames(dependencies);
		return new GamesMap(games, dependencies);
	}
}

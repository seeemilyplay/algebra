package seeemilyplay.algebra.games.core;

import java.util.HashMap;
import java.util.Map;




public final class GamesRegister {

	private CategoriesFactory categoriesFactory = new CategoriesFactory();

	private Map<GameId,GamePlay> gamePlays = new HashMap<GameId,GamePlay>();

	public void registerGame(GameSettings gameSettings, GamePlay gamePlay) {
		storeSettings(gameSettings);
		storeGamePlay(gameSettings.getGameId(), gamePlay);
	}

	private void storeSettings(GameSettings gameSettings) {
		categoriesFactory.addGame(gameSettings);
	}

	private void storeGamePlay(GameId gameId, GamePlay gamePlay) {
		gamePlays.put(gameId, gamePlay);
	}

	public GamePlay getGamePlay(GameId gameId) {
		return gamePlays.get(gameId);
	}

	public Categories createCategories() {
		return categoriesFactory.createCategories();
	}
}

package seeemilyplay.algebra.games.selection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.games.core.CategoryId;

final class GamesModelManager {

	private final Map<CategoryId,GamesModel> gamesModels;

	public GamesModelManager(
			Map<CategoryId,GamesModel> gamesModels) {
		this.gamesModels = Collections.unmodifiableMap(
				new HashMap<CategoryId,GamesModel>(gamesModels));
	}

	public GamesModel getGamesModel(CategoryId categoryId) {
		return gamesModels.get(categoryId);
	}
}

package seeemilyplay.algebra.games.selection;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.games.core.Categories;
import seeemilyplay.algebra.games.core.Category;
import seeemilyplay.algebra.games.core.CategoryId;

final class GamesComponentsManager {

	private final ApplicationModel applicationModel;
	private final Categories categories;

	private Map<CategoryId, GamesComponent> gamesComponents =
		new HashMap<CategoryId, GamesComponent>();

	public GamesComponentsManager(
			ApplicationModel applicationModel,
			Categories categories) {
		this.applicationModel = applicationModel;
		this.categories = categories;

		createAndCacheGamesComponents();
	}

	private void createAndCacheGamesComponents() {
		for (Category category : categories) {
			createAndCacheGamesComponent(category);
		}
	}

	private void createAndCacheGamesComponent(Category category) {
		GamesComponent gamesComponent = createGamesComponent(category);
		gamesComponents.put(
				category.getId(),
				gamesComponent);
	}

	private GamesComponent createGamesComponent(Category category) {
		GamesModel gamesModel = createGamesModel(category);
		return new GamesComponent(gamesModel);
	}

	private GamesModel createGamesModel(Category category) {
		return new GamesModel(applicationModel, category.getGamesMap());
	}

	public GamesComponent getGamesComponent(CategoryId category) {
		return gamesComponents.get(category);
	}
}

package seeemilyplay.algebra.games.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class CategoriesFactory {

	private final List<CategoryId> categoryIds = new ArrayList<CategoryId>();
	private final Map<CategoryId,GamesMapFactory> gamesMapFactories =
		new HashMap<CategoryId,GamesMapFactory>();

	public CategoriesFactory() {
		super();
	}

	public void addGame(GameSettings gameSettings) {
		CategoryId categoryId = gameSettings.getCategoryId();
		addCategoryIfRequired(categoryId);
		GamesMapFactory gamesMapFactory = getOrCreateGamesMapFactory(categoryId);
		gamesMapFactory.addGame(gameSettings);
	}

	private void addCategoryIfRequired(CategoryId categoryId) {
		if (!isExistingCategory(categoryId)) {
			addCategory(categoryId);
		}
	}

	private boolean isExistingCategory(CategoryId categoryId) {
		return categoryIds.contains(categoryId);
	}

	private void addCategory(CategoryId categoryId) {
		categoryIds.add(categoryId);
	}

	private GamesMapFactory getOrCreateGamesMapFactory(CategoryId categoryId) {
		if (!isGamesMapFactoryFor(categoryId)) {
			createGamesMapFactory(categoryId);
		}
		return getGamesMapFactory(categoryId);
	}

	private boolean isGamesMapFactoryFor(CategoryId categoryId) {
		return gamesMapFactories.containsKey(categoryId);
	}

	private void createGamesMapFactory(CategoryId categoryId) {
		gamesMapFactories.put(categoryId, new GamesMapFactory());
	}

	private GamesMapFactory getGamesMapFactory(CategoryId categoryId) {
		return gamesMapFactories.get(categoryId);
	}

	public Categories createCategories() {
		return new Categories(createCategoryList());
	}

	private List<Category> createCategoryList() {
		List<Category> categoryList = new ArrayList<Category>();
		for (CategoryId categoryId : categoryIds) {
			categoryList.add(createCategory(categoryId));
		}
		return Collections.unmodifiableList(categoryList);
	}

	private Category createCategory(CategoryId categoryId) {
		GamesMap gamesMap = createGamesMap(categoryId);
		return new Category(categoryId, gamesMap);
	}

	private GamesMap createGamesMap(CategoryId categoryId) {
		GamesMapFactory gamesMapFactory = getGamesMapFactory(categoryId);
		return gamesMapFactory.createGamesMap();
	}
}

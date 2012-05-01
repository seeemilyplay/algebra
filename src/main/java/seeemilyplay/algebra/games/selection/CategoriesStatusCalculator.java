package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.games.core.Categories;
import seeemilyplay.algebra.games.core.Category;
import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.games.core.Games;
import seeemilyplay.algebra.games.core.GamesMap;
import seeemilyplay.algebra.progress.Level;

final class CategoriesStatusCalculator implements StatusCalculator<CategoryId> {

	private static final int PASS_LEVEL = Level.BEGINNER.ordinal();
	private static final int UNLOCKED_COUNT_ADJUSTMENT = 2;

	private Categories categories;
	private GamesModelManager gamesModelManager;

	public CategoriesStatusCalculator(
			Categories categories,
			GamesModelManager gamesModelManager) {
		this.categories = categories;
		this.gamesModelManager = gamesModelManager;
	}

	public Level getLevel(CategoryId categoryId) {
		Level minLevel = Level.BEGINNER;
		for (GameId gameId : getGames(categoryId)) {
			Level level = getLevel(categoryId, gameId);
			minLevel = getMin(minLevel, level);
		}
		return minLevel;
	}

	private Level getMin(Level a, Level b) {
		return a.compareTo(b)<0 ? a : b;
	}

	private Level getLevel(CategoryId categoryId, GameId gameId) {
		return getGamesModel(categoryId).getLevel(gameId);
	}

	private Games getGames(CategoryId categoryId) {
		return getGamesMap(categoryId).getGames();
	}
	private GamesMap getGamesMap(CategoryId categoryId) {
		return getGamesModel(categoryId).getGamesMap();
	}

	private GamesModel getGamesModel(CategoryId categoryId) {
		return gamesModelManager.getGamesModel(categoryId);
	}

	public boolean isUnlocked(CategoryId categoryId) {
		return (getIndex(categoryId)<getUnlockedCount());
	}

	private int getIndex(CategoryId categoryId) {
		int i=0;
		for (Category category : categories) {
			if (categoryId.equals(category.getId())) {
				return i;
			}
			i++;
		}
		throw new IllegalStateException();
	}

	private int getUnlockedCount() {
		return getPassedCount() + UNLOCKED_COUNT_ADJUSTMENT;
	}

	private int getPassedCount() {
		int awardedCount = 0;
		for (Category category : categories) {
			if (isPassed(category)) {
				awardedCount++;
			}
		}
		return awardedCount;
	}

	private boolean isPassed(Category category) {
		Level level = getLevel(category.getId());
		return level.ordinal()>PASS_LEVEL;
	}
}

package seeemilyplay.algebra.games.selection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.games.core.Categories;
import seeemilyplay.algebra.games.core.Category;
import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.Level;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

public final class GameChooserModel extends SwingModel {

	private final ApplicationModel applicationModel;
	private final Categories categories;

	private GamesModelManager gamesModelManager;
	private StatusManager<CategoryId> statusManager;

	private Listener applicationListener;

	private CategoryId selectedCategory;

	public GameChooserModel(
			ApplicationModel applicationModel,
			Categories categories) {
		this.applicationModel = applicationModel;
		this.categories = categories;
		this.selectedCategory = getFirstCategory();

		initGamesModels();
		initStatusManager();
		initApplicationListener();
	}

	private CategoryId getFirstCategory() {
		return categories.iterator().next().getId();
	}

	private void initGamesModels() {
		gamesModelManager = new GamesModelManager(
				createGamesModels());
	}

	private Map<CategoryId,GamesModel> createGamesModels() {
		Map<CategoryId,GamesModel> gamesModels = new HashMap<CategoryId,GamesModel>();
		for (Category category : categories) {
			gamesModels.put(
					category.getId(),
					createGamesModel(category));
		}
		return Collections.unmodifiableMap(gamesModels);
	}

	private GamesModel createGamesModel(Category category) {
		return new GamesModel(
				applicationModel,
				category.getGamesMap());
	}

	private void initStatusManager() {
		this.statusManager = new StatusManager<CategoryId>(
				applicationModel,
				createStatusCalculator(),
				createUnlockNoticeFactory());
	}

	private StatusCalculator<CategoryId> createStatusCalculator() {
		return new CategoriesStatusCalculator(
				categories,
				gamesModelManager);
	}
	
	private UnlockNoticeFactory<CategoryId> createUnlockNoticeFactory() {
		return new UnlockedCategoryNoticeFactory();
	}

	private void initApplicationListener() {
		applicationListener = new Listener() {
			public void onChange() {
				onApplicationChange();
			}
		};
		applicationModel.addListener(applicationListener);
		onApplicationChange();
	}

	private void onApplicationChange() {
		statusManager.clear();
		fireChange();
	}

	public Categories getCategories() {
		return categories;
	}

	public GamesModel getGamesModel(CategoryId categoryId) {
		return gamesModelManager.getGamesModel(categoryId);
	}

	public boolean isUnlocked(CategoryId categoryId) {
		return statusManager.isUnlocked(categoryId);
	}

	public Level getLevel(CategoryId categoryId) {
		return statusManager.getLevel(categoryId);
	}

	public boolean isAwarded(CategoryId categoryId) {
		return statusManager.isAwarded(categoryId);
	}

	public Award getAward(CategoryId categoryId) {
		return statusManager.getAward(categoryId);
	}

	public CategoryId getSelectedCategory() {
		return selectedCategory;
	}

	public boolean isSelectedCategory(CategoryId categoryId) {
		return selectedCategory.equals(categoryId);
	}

	public void setSelectedCategory(CategoryId categoryId) {
		this.selectedCategory = categoryId;
		fireChange();
	}
}

package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

final class CategoryModel extends SwingModel {

	private final CategoryId categoryId;
	private final GameChooserModel gameChooserModel;

	private Listener categoriesModelListener;

	public CategoryModel(CategoryId categoryId, GameChooserModel gameChooserModel) {
		this.categoryId = categoryId;
		this.gameChooserModel = gameChooserModel;

		initCategoriesModelListener();
	}

	private void initCategoriesModelListener() {
		categoriesModelListener = new Listener() {
			public void onChange() {
				fireChange();
			}
		};
		gameChooserModel.addListener(categoriesModelListener);
	}

	public CategoryId getCategoryId() {
		return categoryId;
	}

	public Award getAward() {
		return gameChooserModel.getAward(categoryId);
	}

	public boolean isAward() {
		return gameChooserModel.isAwarded(categoryId);
	}

	public boolean isUnlocked() {
		return gameChooserModel.isUnlocked(categoryId);
	}

	public void selectCategory() {
		gameChooserModel.setSelectedCategory(categoryId);
	}

	public boolean isSelectedCategory() {
		return gameChooserModel.isSelectedCategory(categoryId);
	}
}

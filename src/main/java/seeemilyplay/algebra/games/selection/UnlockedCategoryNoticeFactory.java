package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.bulletin.Notice;
import seeemilyplay.algebra.bulletin.notices.UnlockedCategoryNotice;
import seeemilyplay.algebra.games.core.CategoryId;

public class UnlockedCategoryNoticeFactory implements UnlockNoticeFactory<CategoryId> {

	public UnlockedCategoryNoticeFactory() {
		super();
	}
	
	public Notice createUnlockNotice(CategoryId categoryId) {
		String category = getCategoryName(categoryId);
		return new UnlockedCategoryNotice(category);
	}

	private String getCategoryName(CategoryId categoryId) {
		return categoryId.getName();
	}
}

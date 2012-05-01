package seeemilyplay.algebra.bulletin.notices;

import seeemilyplay.algebra.bulletin.Notice;

public final class UnlockedCategoryNotice implements Notice {

	private final String category;
	
	public UnlockedCategoryNotice(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}

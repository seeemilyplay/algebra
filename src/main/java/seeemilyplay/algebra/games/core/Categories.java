package seeemilyplay.algebra.games.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Categories implements Iterable<Category> {

	private final List<Category> categories;

	Categories(List<Category> categories) {
		this.categories = Collections.unmodifiableList(
				new ArrayList<Category>(categories));
	}

	public Iterator<Category> iterator() {
		return categories.iterator();
	}

	public int indexOf(String category) {
		return categories.indexOf(category);
	}

	public int getCount() {
		return categories.size();
	}
}

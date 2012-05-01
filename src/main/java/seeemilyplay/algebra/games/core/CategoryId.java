package seeemilyplay.algebra.games.core;

public final class CategoryId {

	private final String id;
	private final String name;

	public CategoryId(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int hashCode() {
		return id.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		CategoryId other = (CategoryId)obj;
		return id.equals(other.id);
	}
}

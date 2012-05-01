package seeemilyplay.algebra.games.core;

public final class GameId {

	private final String id;
	private final String name;

	public GameId(String id, String name) {
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
		GameId other = (GameId)obj;
		return id.equals(other.id);
	}
}

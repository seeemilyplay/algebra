package seeemilyplay.algebra.games.core;

public final class Dependency {

	private final GameId from;
	private final GameId to;

	Dependency(GameId from, GameId to) {
		this.from = from;
		this.to = to;

		validate();
	}

	private void validate() {
		validateFrom();
		validateTo();
	}

	private void validateFrom() {
		checkNotNull(from);
	}

	private void validateTo() {
		checkNotNull(to);
	}

	private void checkNotNull(GameId value) {
		if (value==null) {
			throw new NullPointerException();
		}
	}

	public GameId getFrom() {
		return from;
	}

	public GameId getTo() {
		return to;
	}

	public int hashCode() {
		return from.hashCode() + to.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final Dependency other = (Dependency)obj;
		return (to.equals(other.to) && from.equals(other.from));
	}
}

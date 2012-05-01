package seeemilyplay.algebra.games.core;

import java.util.Iterator;


final class GamesIterator implements Iterator<GameId> {

	private final Games games;

	private int level;
	private int index = -1;

	public GamesIterator(Games games) {
		this.games = games;
	}

	public boolean hasNext() {
		return (!isLastLevel() || !isLastGame());
	}

	private boolean isLastLevel() {
		return (level>=getLastLevel());
	}

	private int getLastLevel() {
		return getLevelCount()-1;
	}

	public int getLevelCount() {
		return games.getLevelCount();
	}

	private boolean isLastGame() {
		return (index>=getLastGame());
	}

	private int getLastGame() {
		return getGameCount()-1;
	}

	public int getGameCount() {
		return games.getGameCount(level);
	}

	public GameId next() {
		incrementIndexes();
		return getCurrent();
	}

	private void incrementIndexes() {
		if (isLastGame()) {
			level++;
			index = 0;
		} else {
			index++;
		}
	}

	private GameId getCurrent() {
		return getGame(level, index);
	}

	public GameId getGame(int level, int index) {
		return games.getGame(level, index);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}

package seeemilyplay.algebra.games.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Games implements Iterable<GameId> {

	private final List<List<GameId>> games;

	Games(List<List<GameId>> games) {
		this.games = Collections.unmodifiableList(
				new ArrayList<List<GameId>>(games));
	}

	public int getLevelCount() {
		return games.size();
	}

	public int getGameCount(int level) {
		return games.get(level).size();
	}

	public GameId getGame(int level, int index) {
		return games.get(level).get(index);
	}

	public Iterator<GameId> iterator() {
		return new GamesIterator(this);
	}
}

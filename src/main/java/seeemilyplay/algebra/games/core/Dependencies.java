package seeemilyplay.algebra.games.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class Dependencies implements Iterable<Dependency> {

	private final Collection<Dependency> dependencies;

	Dependencies(Collection<Dependency> dependencies) {
		this.dependencies = Collections.unmodifiableCollection(
				new ArrayList<Dependency>(dependencies));
	}

	public Iterator<Dependency> iterator() {
		return dependencies.iterator();
	}

	public Set<GameId> getParents(GameId id) {
		Set<GameId> parents = new HashSet<GameId>();
		for (Dependency depedency : dependencies) {
			if (isChild(depedency, id)) {
				parents.add(depedency.getFrom());
			}
		}
		return Collections.unmodifiableSet(parents);
	}

	private boolean isChild(Dependency depedency, GameId gameId) {
		return gameId.equals(depedency.getTo());
	}
}

package seeemilyplay.algebra.games.core;

import java.util.HashSet;
import java.util.Set;

final class DependenciesFactory {

	private Set<Dependency> dependencies = new HashSet<Dependency>();

	public DependenciesFactory() {
		super();
	}

	public void addDependency(GameId from, GameId to) {
		Dependency dependency = new Dependency(from, to);
		dependencies.add(dependency);
	}

	public Dependencies createDependencies() {
		return new Dependencies(dependencies);
	}
}

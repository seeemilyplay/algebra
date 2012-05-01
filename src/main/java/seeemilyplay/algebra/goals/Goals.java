package seeemilyplay.algebra.goals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Goals implements Iterable<Goal> {

	private final List<Goal> goals;

	Goals(Collection<Goal> goals) {
		List<Goal> goalsCopy = new ArrayList<Goal>(goals);
		Collections.sort(goalsCopy);
		this.goals = Collections.unmodifiableList(goalsCopy);
	}

	public Iterator<Goal> iterator() {
		return goals.iterator();
	}
}

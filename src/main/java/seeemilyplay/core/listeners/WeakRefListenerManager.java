package seeemilyplay.core.listeners;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Stores <code>Listeners</code> using weak references.
 */
public class WeakRefListenerManager implements ListenerManager {

	private final Map<Listener, Listener> listeners =
		new WeakHashMap<Listener, Listener>();

	public WeakRefListenerManager() {
		super();
	}

	public void addListener(Listener listener) {
		listeners.put(listener, null);
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	public void fireChange() {
		for (Listener listener : getCurrentListeners()) {
			listener.onChange();
		}
	}

	private Set<Listener> getCurrentListeners() {
		Set<Listener> currentListeners =
			new HashSet<Listener>(listeners.keySet());
		return Collections.unmodifiableSet(currentListeners);
	}
}

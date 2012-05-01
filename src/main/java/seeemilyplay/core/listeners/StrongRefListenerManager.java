package seeemilyplay.core.listeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores <code>Listeners</code> using strong references.
 */
public class StrongRefListenerManager implements ListenerManager {

	private final List<Listener> listeners = new ArrayList<Listener>();

	public StrongRefListenerManager() {
		super();
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	public void fireChange() {
		for (Listener listener : listeners) {
			listener.onChange();
		}
	}
}

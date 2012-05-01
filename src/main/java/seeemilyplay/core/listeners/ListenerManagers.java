package seeemilyplay.core.listeners;

/**
 * Contains static creation methods for various
 * implementations of <code>ListenerManager</code>.
 */
public final class ListenerManagers {

	private ListenerManagers() {
		super();
	}

	public static ListenerManager createWeakRefSwingListenerManager() throws Error {
		return new SwingListenerManager(new WeakRefListenerManager());
	}
}

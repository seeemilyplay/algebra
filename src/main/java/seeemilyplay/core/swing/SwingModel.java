package seeemilyplay.core.swing;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.listeners.ListenerManager;
import seeemilyplay.core.listeners.ListenerManagers;

/**
 * The purpose of this class is to act as a base for most of the standard
 * Swing model implementations.
 * It takes care of managing the listener adding, removing and firing.
 */
public class SwingModel {

	private ListenerManager listenerManager;

	protected SwingModel() {
		super();
		setup();
	}

	private void setup() {
		setupListenerManager();
	}

	private void setupListenerManager() {
		setListenerManager(getDefaultListenerManager());
	}

	private ListenerManager getDefaultListenerManager() {
		return ListenerManagers.createWeakRefSwingListenerManager();
	}

	public void setListenerManager(ListenerManager listenerManager) {
		this.listenerManager = listenerManager;
	}

	public void addListener(Listener listener) {
		this.listenerManager.addListener(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerManager.removeListener(listener);
	}

	protected void fireChange() {
		this.listenerManager.fireChange();
	}
}

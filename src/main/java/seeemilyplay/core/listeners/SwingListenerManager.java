package seeemilyplay.core.listeners;

import javax.swing.SwingUtilities;

/**
 * Ensures use of listeners is contained on the swing thread.
 */
public class SwingListenerManager implements ListenerManager {

	private final ListenerManager delegate;

	public SwingListenerManager(ListenerManager delegate) {
		this.delegate = delegate;
	}

	public void addListener(Listener listener) {
		assertSwingThread();
		delegate.addListener(listener);
	}

	public void removeListener(Listener listener){
		assertSwingThread();
		delegate.removeListener(listener);
	}

	public void fireChange() {
		assertSwingThread();
		delegate.fireChange();
	}

	private void assertSwingThread() {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new Error("Method call not on Swing Thread as expected");
		}
	}
}

package seeemilyplay.core.listeners;

/**
 * Stores and fires listeners.
 */
public interface ListenerManager {


    public void addListener(Listener listener);

    public void removeListener(Listener listener);

    public void fireChange();
}

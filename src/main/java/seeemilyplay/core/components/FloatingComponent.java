package seeemilyplay.core.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.listeners.ListenerManager;
import seeemilyplay.core.listeners.ListenerManagers;

/**
 * This is a component that can be floated over
 * a background component using the <code>FloatingPane</code>.
 * It includes a system for firing changes which will resize
 * the component as it floats.
 *
 * @deprecated
 */
@SuppressWarnings("serial")
public class FloatingComponent extends JPanel {

	private static final String BORDER_KEY = "FloatingComponent.border";

	private ListenerManager listenerManager;

	private Dimension initialPreferredSize;

	public FloatingComponent() {
		super();
		setLayout(new BorderLayout());
	}

	public Border getBorder() {
		return UIManager.getBorder(BORDER_KEY);
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (initialPreferredSize==null) {
			initialPreferredSize = getPreferredSize();
		}
	}

	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		if (initialPreferredSize==null) {
			return preferredSize;
		}
		return new Dimension(
				Math.max(preferredSize.width, initialPreferredSize.width),
				Math.max(preferredSize.height, initialPreferredSize.height));
	}

	private void initListenerManagerIfNull() {
		if (listenerManager==null) {
			listenerManager = ListenerManagers.createWeakRefSwingListenerManager();
		}
	}

	/**
	 * Sets the <code>ListenerManager</code> that this model should
	 * use for storing any added listeners.
	 * By default this is initialised with a weak referenced swing
	 * listener manager.
	 * This method exists mainly because it makes unit testing a lot
	 * easier.
	 *
	 * @param listenerManager	the listener manager the model should use
	 */
	public void setListenerManager(ListenerManager listenerManager) {
		this.listenerManager = listenerManager;
	}

	/**
	 * Adds a listener to this model so that it receives
	 * notification of future changes.
	 *
	 * @param	listener	the listener to be added
	 */
	public void addListener(Listener listener) {
		initListenerManagerIfNull();
		this.listenerManager.addListener(listener);
	}

	/**
     * Removes a listener from this manager so that it no
     * longer receives notification of any future changes.
     *
     * @param	listener	the listener to be removed
     */
	public void removeListener(Listener listener) {
		initListenerManagerIfNull();
		this.listenerManager.removeListener(listener);
	}

	/**
     * Fires a change to all of the currently
     * managed listeners.
     */
	public void fireChange() {
		initListenerManagerIfNull();
		this.listenerManager.fireChange();
	}
}

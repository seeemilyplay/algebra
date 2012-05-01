package seeemilyplay.core.swing;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A base <code>ListDataListener</code> implementation that
 * can be conveniently extended.
 */
public abstract class ListDataAdapter implements ListDataListener {

	public void contentsChanged(ListDataEvent e) {
	}

	public void intervalAdded(ListDataEvent e) {
	}

	public void intervalRemoved(ListDataEvent e) {
	}	
}

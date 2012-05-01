package seeemilyplay.core.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A convenience class that reduces the implementation of
 * a <code>DocumentListener</code> down to just one method.
 */
public abstract class DocumentAdapter implements DocumentListener {
	
	public abstract void onChange();

	public void changedUpdate(DocumentEvent e) {
		onChange();
	}

	public void insertUpdate(DocumentEvent e) {
		onChange();
	}

	public void removeUpdate(DocumentEvent e) {
		onChange();
	}
}

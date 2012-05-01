package seeemilyplay.core.components;

import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import seeemilyplay.core.components.text.ComponentPhrases;
import seeemilyplay.core.components.text.ComponentPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * OK button with shortcut ENTER.
 */
@SuppressWarnings("serial")
public final class OkButton extends ShortcutButton {

	private static final String ACTION_KEY = "ok";


	public OkButton(Runnable task) {
		super(task);
	}

	protected void registerPhraseBook() {
		ComponentPhrasesFactory factory = new ComponentPhrasesFactory();
		PhraseBook.registerPhrases(ComponentPhrases.class, factory);
	}

	protected String getLabel() {
		return PhraseBook.getPhrases(ComponentPhrases.class).getOK();
	}

	protected Icon getIcon() {
		return null;
	}

	protected String getActionKey() {
		return ACTION_KEY;
	}

	protected KeyStroke getShortcutKey() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	}
}

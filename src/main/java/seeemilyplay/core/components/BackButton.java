package seeemilyplay.core.components;

import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import seeemilyplay.core.components.text.ComponentPhrases;
import seeemilyplay.core.components.text.ComponentPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * A back button with shortcut ALT-LEFT_ARROW.
 */
@SuppressWarnings("serial")
public final class BackButton extends ShortcutButton {

	private static final String ICON_KEY = "Icon.back";

	private static final String ACTION_KEY = "back";

	public BackButton(Runnable task) {
		super(task);
	}

	protected void registerPhraseBook() {
		ComponentPhrasesFactory factory = new ComponentPhrasesFactory();
		PhraseBook.registerPhrases(ComponentPhrases.class, factory);
	}

	protected String getLabel() {
		return PhraseBook.getPhrases(ComponentPhrases.class).getBack();
	}

	protected Icon getIcon() {
		return getBackIcon();
	}

	protected String getActionKey() {
		return ACTION_KEY;
	}

	protected KeyStroke getShortcutKey() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.ALT_MASK);
	}

	private Icon getBackIcon() {
		return UIManager.getIcon(ICON_KEY);
	}
}

package seeemilyplay.core.components;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import seeemilyplay.core.components.text.ComponentPhrases;
import seeemilyplay.core.components.text.ComponentPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * Abstract exit button with shortcut ESCAPE.
 */
@SuppressWarnings("serial")
public abstract class AbstractExitButton extends ShortcutButton {

	private static final String ICON_KEY = "Icon.exit";
	private static final String BACKGROUND_KEY = "Button.warningBackround";

	private static final String ACTION_KEY = "exit";


	public AbstractExitButton(Runnable task) {
		super(task);

		setWarningBackground();
	}

	private void setWarningBackground() {
		setBackground(getWarningBackground());
	}

	private Color getWarningBackground() {
		return UIManager.getColor(BACKGROUND_KEY);
	}

	protected void registerPhraseBook() {
		ComponentPhrasesFactory factory = new ComponentPhrasesFactory();
		PhraseBook.registerPhrases(ComponentPhrases.class, factory);
	}

	protected abstract String getLabel();

	protected Icon getIcon() {
		return UIManager.getIcon(ICON_KEY);
	}

	protected String getActionKey() {
		return ACTION_KEY;
	}

	protected KeyStroke getShortcutKey() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
	}
}

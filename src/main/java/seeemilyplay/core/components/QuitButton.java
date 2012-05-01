package seeemilyplay.core.components;

import seeemilyplay.core.components.text.ComponentPhrases;
import seeemilyplay.core.international.text.PhraseBook;


/**
 * Quit button with shortcut ESCAPE.
 */
@SuppressWarnings("serial")
public final class QuitButton extends AbstractExitButton {


	public QuitButton(Runnable task) {
		super(task);
	}

	protected String getLabel() {
		return PhraseBook.getPhrases(ComponentPhrases.class).getQuit();
	}
}

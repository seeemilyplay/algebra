package seeemilyplay.core.components;

import seeemilyplay.core.components.text.ComponentPhrases;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * Exit button with shortcut ESCAPE.
 */
@SuppressWarnings("serial")
public final class ExitButton extends AbstractExitButton {


	public ExitButton(Runnable task) {
		super(task);
	}

	protected String getLabel() {
		return PhraseBook.getPhrases(ComponentPhrases.class).getExit();
	}
}

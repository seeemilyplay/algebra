package seeemilyplay.core.components;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class PhraseBookAwareComponent extends JComponent {

	public PhraseBookAwareComponent() {
		super();
		registerPhraseBook();
	}

	protected abstract void registerPhraseBook();
}

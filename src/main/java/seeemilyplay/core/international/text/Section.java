package seeemilyplay.core.international.text;

import seeemilyplay.core.international.InternationalFactory;
import seeemilyplay.core.international.LocalFactory;

/**
 * A particular section in the <code>PhraseBook</code> that lazily
 * creates the phrase objects as they're required.
 *
 * @param <P>	the phrase definition class
 */
final class Section<P> {

	private final LocalFactory<P> localFactory;
	private P phrases;

	public Section(LocalFactory<P> localFactory) {
		this.localFactory = localFactory;
	}

	public P getPhrases() {
		createPhrasesIfRequired();
		return phrases;
	}

	private void createPhrasesIfRequired() {
		if (!isPhrasesCreated()) {
			createPhrases();
		}
	}

	private boolean isPhrasesCreated() {
		return (phrases!=null);
	}

	private void createPhrases() {
		phrases = InternationalFactory.create(localFactory);
	}
}

package seeemilyplay.core.text;


/**
 * This is a static class that returns
 * the correct <code>Phrases</code> to use for the
 * <code>Locale</code>.
 *
 * @deprecated
 */
public class PhraseBook {

	private static final Phrases phrases = new PhrasesUK();

	private PhraseBook() {
		super();
	}

	public static Phrases getPhrases() {
		return phrases;
	}
}

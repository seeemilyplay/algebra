package seeemilyplay.algebra.sequences;

import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.algebra.sequences.text.SequencesPhrases;
import seeemilyplay.algebra.sequences.text.SequencesPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

public final class SequencesId {

	private static final String CATEGORY_ID = "Sequences";

	public static CategoryId getInstance() {
		registerPhrases();

		CategoryId categoryId = new CategoryId(
				getCategoryId(),
				getCategoryName());
		return categoryId;
	}

	private static void registerPhrases() {
		PhraseBook.registerPhrases(
				SequencesPhrases.class,
				new SequencesPhrasesFactory());
	}

	private static String getCategoryId() {
		return CATEGORY_ID;
	}

	private static String getCategoryName() {
		return getPhrases().getCategoryName();
	}

	private static SequencesPhrases getPhrases() {
		return PhraseBook.getPhrases(SequencesPhrases.class);
	}
}

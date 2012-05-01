package seeemilyplay.algebra.sequences.crossword.text;

import seeemilyplay.core.international.LocalFactory;

public final class CrosswordPhrasesFactory implements LocalFactory<CrosswordPhrases> {

	public CrosswordPhrases createUK() {
		return new CrosswordPhrasesUK();
	}
}

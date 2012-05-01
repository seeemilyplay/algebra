package seeemilyplay.algebra.sequences.text;

import seeemilyplay.core.international.LocalFactory;

public final class SequencesPhrasesFactory implements LocalFactory<SequencesPhrases> {

	public SequencesPhrases createUK() {
		return new SequencesPhrasesUK();
	}
}

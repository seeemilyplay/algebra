package seeemilyplay.algebra.sequences.jigsaw.text;

import seeemilyplay.core.international.LocalFactory;

public final class JigsawPhrasesFactory implements LocalFactory<JigsawPhrases> {

	public JigsawPhrases createUK() {
		return new JigsawPhrasesUK();
	}
}

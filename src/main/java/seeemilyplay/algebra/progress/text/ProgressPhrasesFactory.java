package seeemilyplay.algebra.progress.text;

import seeemilyplay.core.international.LocalFactory;

public final class ProgressPhrasesFactory implements LocalFactory<ProgressPhrases> {

	public ProgressPhrases createUK() {
		return new ProgressPhrasesUK();
	}
}

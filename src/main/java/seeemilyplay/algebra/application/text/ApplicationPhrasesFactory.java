package seeemilyplay.algebra.application.text;

import seeemilyplay.core.international.LocalFactory;

public final class ApplicationPhrasesFactory implements LocalFactory<ApplicationPhrases> {

	public ApplicationPhrases createUK() {
		return new ApplicationPhrasesUK();
	}
}

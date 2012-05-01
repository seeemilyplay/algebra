package seeemilyplay.core.components.text;

import seeemilyplay.core.international.LocalFactory;

public final class ComponentPhrasesFactory implements LocalFactory<ComponentPhrases> {

	public ComponentPhrases createUK() {
		return new ComponentPhrasesUK();
	}
}

package seeemilyplay.algebra.users.text;

import seeemilyplay.core.international.LocalFactory;

public final class UserPhrasesFactory implements LocalFactory<UserPhrases> {


	public UserPhrasesFactory() {
		super();
	}

	public UserPhrases createUK() {
		return new UserPhrasesUK();
	}
}

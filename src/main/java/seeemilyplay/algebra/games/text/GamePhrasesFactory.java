package seeemilyplay.algebra.games.text;

import seeemilyplay.core.international.LocalFactory;

public final class GamePhrasesFactory implements LocalFactory<GamePhrases> {

	public GamePhrases createUK() {
		return new GamePhrasesUK();
	}
}

package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.sequences.crossword.text.CrosswordPhrases;
import seeemilyplay.algebra.sequences.crossword.text.CrosswordPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

public final class CrosswordId {

	private static final String GAME_ID = "Sequences_Crossword";

	private CrosswordId() {
		super();
	}

	public static GameId getInstance() {
		registerPhrases();

		GameId gameId = new GameId(
				getGameId(),
				getGameName());
		return gameId;
	}

	private static void registerPhrases() {
		PhraseBook.registerPhrases(
				CrosswordPhrases.class,
				new CrosswordPhrasesFactory());
	}

	private static String getGameId() {
		return GAME_ID;
	}

	private static String getGameName() {
		return getPhrases().getGameName();
	}

	private static CrosswordPhrases getPhrases() {
		return PhraseBook.getPhrases(CrosswordPhrases.class);
	}
}

package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.sequences.jigsaw.text.JigsawPhrases;
import seeemilyplay.algebra.sequences.jigsaw.text.JigsawPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

public final class JigsawId {

	private static final String GAME_ID = "Sequences_Jigsaw";

	private JigsawId() {
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
				JigsawPhrases.class,
				new JigsawPhrasesFactory());
	}

	private static String getGameId() {
		return GAME_ID;
	}

	private static String getGameName() {
		return getPhrases().getGameName();
	}

	private static JigsawPhrases getPhrases() {
		return PhraseBook.getPhrases(JigsawPhrases.class);
	}
}

package seeemilyplay.algebra.games.install;

import java.util.Arrays;

import seeemilyplay.algebra.games.GamesContext;
import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.games.core.GamePlay;
import seeemilyplay.algebra.games.core.GameSettings;
import seeemilyplay.algebra.sequences.SequencesId;
import seeemilyplay.algebra.sequences.crossword.CrosswordGamePlay;
import seeemilyplay.algebra.sequences.crossword.CrosswordId;
import seeemilyplay.algebra.sequences.jigsaw.JigsawGamePlay;
import seeemilyplay.algebra.sequences.jigsaw.JigsawId;

final class InstallerImpl implements Installer {

	private GamesContext gamesContext;

	public InstallerImpl() {
		super();
	}

	public void install(GamesContext gamesContext) {
		init(gamesContext);
		installSequences();
	}

	private void init(GamesContext gamesContext) {
		this.gamesContext = gamesContext;
	}

	private void installSequences() {
		installCrossword();
		installJigsaw();
	}

	private void installCrossword() {
		GameSettings gameSettings = createGameSettings(
				getSequencesCategoryId(),
				getCrosswordGameId());
		GamePlay gamePlay = getCrosswordGamePlay();
		install(gameSettings, gamePlay);
	}

	private GameId getCrosswordGameId() {
		return CrosswordId.getInstance();
	}

	private GamePlay getCrosswordGamePlay() {
		return new CrosswordGamePlay();
	}
	
	private void installJigsaw() {
		GameSettings gameSettings = createGameSettings(
				getSequencesCategoryId(),
				getJigsawGameId());
		GamePlay gamePlay = getJigsawGamePlay();
		install(gameSettings, gamePlay);
	}
	
	private GameId getJigsawGameId() {
		return JigsawId.getInstance();
	}
	
	private GamePlay getJigsawGamePlay() {
		return new JigsawGamePlay();
	}
	
	private CategoryId getSequencesCategoryId() {
		return SequencesId.getInstance();
	}

	private GameSettings createGameSettings(
			CategoryId categoryId,
			GameId gameId,
			GameId ... dependencies) {
		return new GameSettings(
				categoryId,
				gameId,
				Arrays.asList(dependencies));
	}

	private void install(
			GameSettings gameSettings,
			GamePlay gamePlay) {
		gamesContext.registerGame(gameSettings, gamePlay);
	}
}

package seeemilyplay.algebra.sequences.jigsaw;

import seeemilyplay.quizzer.config.QuizzerConfig;

final class JigsawQuizzerConfig {

	private static final int PIECE_COUNT = 4;
	
	private JigsawQuizzerConfig() {
		super();
	}

	public static QuizzerConfig getInstance() {
		QuizzerConfig config = new QuizzerConfig();
		return config;
	}
	
	public static int getPieceCount() {
		return PIECE_COUNT;
	}
}

package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.quizzer.config.QuizzerConfig;

final class CrosswordQuizzerConfig {

	private static final double AWARD = 0.3;
	private static final double RIPPLE_DECAY = 0.25;
	private static final int CROSSWORDS_IN_LEVEL = 2;
	private static final int NO_QUESTIONS_WITHOUT_REPS = 5;

	private CrosswordQuizzerConfig() {
		super();
	}

	public static QuizzerConfig getInstance() {
		QuizzerConfig config = new QuizzerConfig();
		config.setAward(AWARD);
		config.setUpRippleDecay(RIPPLE_DECAY);
		config.setTargetProbability(AWARD * CROSSWORDS_IN_LEVEL);
		config.setNoQuestionsWithoutReps(NO_QUESTIONS_WITHOUT_REPS);
		return config;
	}
}

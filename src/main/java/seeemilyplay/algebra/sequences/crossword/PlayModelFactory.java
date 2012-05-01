package seeemilyplay.algebra.sequences.crossword;

import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.core.PlayId;
import seeemilyplay.algebra.goals.GoalConfig;
import seeemilyplay.algebra.goals.GoalModel;
import seeemilyplay.quizzer.Quizzer;
import seeemilyplay.quizzer.config.QuizzerConfig;

final class PlayModelFactory {

	private final QuizzerConfig quizzerConfig =
		CrosswordQuizzerConfig.getInstance();
	private final GoalConfig goalConfig =
		CrosswordGoalConfig.getInstance();
	private final CrosswordRepository crosswordRepository =
		new CrosswordRepository();
	private final BaseGameModel baseGameModel;


	public PlayModelFactory(BaseGameModel baseGameModel) {
		super();
		this.baseGameModel = baseGameModel;
	}

	public PlayModel createPlayModel() {
		Quizzer<Crossword> quizzer = createQuizzer();
		GoalModel goalModel = createGoalModel(quizzer);
		return new PlayModel(
				baseGameModel,
				quizzer,
				goalModel);
	}

	private GoalModel createGoalModel(Quizzer<Crossword> quizzer) {
		return baseGameModel.createGoalModel(goalConfig, quizzer);
	}

	private Quizzer<Crossword> createQuizzer() {
		return getQuizzerCache().getQuizzer(
				getPlayId(), 
				createQuizzerFactory());
	}
	
	private CrosswordQuizzerCache getQuizzerCache() {
		return CrosswordQuizzerCache.getInstance();
	}
	
	private PlayId getPlayId() {
		return baseGameModel.getPlayId();
	}

	private CrosswordQuizzerFactory createQuizzerFactory() {
		return new CrosswordQuizzerFactory(
				quizzerConfig,
				crosswordRepository);
	}
}

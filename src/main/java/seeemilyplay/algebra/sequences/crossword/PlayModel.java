package seeemilyplay.algebra.sequences.crossword;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.bulletin.BulletinModel;
import seeemilyplay.algebra.goals.GoalModel;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;
import seeemilyplay.quizzer.QuizQuestion;
import seeemilyplay.quizzer.Quizzer;

final class PlayModel extends SwingModel {

	private final BaseGameModel baseGameModel;
	private final Quizzer<Crossword> quizzer;
	private final GoalModel goalModel;
	
	private BulletinModel bulletinModel;
	private Listener bulletinModelListener;

	private QuizQuestion<Crossword> question;
	private CrosswordModel crosswordModel;
	private boolean isCompleteFired;
	private Listener crosswordModelListener;


	public PlayModel(
			BaseGameModel baseGameModel,
			Quizzer<Crossword> quizzer,
			GoalModel goalModel) {
		this.baseGameModel = baseGameModel;
		this.quizzer = quizzer;
		this.goalModel = goalModel;

		initBulletinModelListener();
		initCrosswordModelListener();
		setupNextRound();
	}
	
	private void initBulletinModelListener() {
		bulletinModelListener = new Listener() {
			public void onChange() {
				updateFromBulletinModel();
			}
		};
	}

	private void updateFromBulletinModel() {
		cancelPlayIfRequested();
		setupNextRoundIfRequested();
	}
	
	private void cancelPlayIfRequested() {
		if (isCancelRequested()) {
			cancelPlay();
		}
	}
	
	private boolean isCancelRequested() {
		return bulletinModel.isCancelRequested();
	}
	
	public void cancelPlay() {
		baseGameModel.setFinished();
	}
	
	private void setupNextRoundIfRequested() {
		if (isContinueRequested()) {
			setupNextRound();
		}
	}
	
	private boolean isContinueRequested() {
		return bulletinModel.isContinueRequested();
	}
	
	public BulletinModel createBulletinModel(JComponent backgroundComponent) {
		bulletinModel = new BulletinModel(backgroundComponent);
		bulletinModel.addListener(bulletinModelListener);
		return bulletinModel;
	}
	
	private void initCrosswordModelListener() {
		crosswordModelListener = new Listener() {
			public void onChange() {
				finishRoundIfRequired();
			}
		};
	}
	
	private void finishRoundIfRequired() {
		if (!isCompleteFired && isCrosswordComplete()) {
			isCompleteFired = true;
			finishRound();
		}
	}
	
	private boolean isCrosswordComplete() {
		return crosswordModel.isComplete();
	}
	
	private void finishRound() {
		question.answerCorrectly();
		setupNextRoundIfNoAward();
	}
	
	private void setupNextRoundIfNoAward() {
		if (!isAward()) {
			setupNextRound();
		}
	}
	
	private boolean isAward() {
		return bulletinModel.isAward();
	}

	private void setupNextRound() {
		setupNextQuestion();
		setupNextCrosswordModel();
		fireChange();
	}

	private void setupNextQuestion() {
		question = quizzer.next();
	}

	private void setupNextCrosswordModel() {
		isCompleteFired = false;
		crosswordModel = new CrosswordModel(
				getCrossword());
		crosswordModel.addListener(
				crosswordModelListener);
	}

	private Crossword getCrossword() {
		return question.getQuestion();
	}

	public CrosswordModel getCrosswordModel() {
		return crosswordModel;
	}

	public GoalModel getGoalModel() {
		return goalModel;
	}
}

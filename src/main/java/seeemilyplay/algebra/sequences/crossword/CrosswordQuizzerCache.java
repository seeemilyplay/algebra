package seeemilyplay.algebra.sequences.crossword;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.games.core.PlayId;
import seeemilyplay.quizzer.Quizzer;

public final class CrosswordQuizzerCache {

	private static final CrosswordQuizzerCache instance = new CrosswordQuizzerCache();

	private final Map<PlayId,Quizzer<Crossword>> quizzers = 
		new HashMap<PlayId,Quizzer<Crossword>>();
	
	private PlayId playId;
	private CrosswordQuizzerFactory factory;
	
	private CrosswordQuizzerCache() {
		super();
	}
	
	public static CrosswordQuizzerCache getInstance() {
		return instance; 
	}
	
	public Quizzer<Crossword> getQuizzer(
			PlayId playId, 
			CrosswordQuizzerFactory factory) {
		init(playId, factory);
		createAndSaveQuizzerIfRequired();
		Quizzer<Crossword> quizzer = loadQuizzer();
		return quizzer;
	}
	
	private void init(
			PlayId playId,
			CrosswordQuizzerFactory factory) {
		this.playId = playId;
		this.factory = factory;
	}
	
	private void createAndSaveQuizzerIfRequired() {
		if (!isQuizzer()) {
			createAndSaveQuizzer();
		}
	}
	
	private boolean isQuizzer() {
		return quizzers.containsKey(playId);
	}
	
	private void createAndSaveQuizzer() {
		quizzers.put(playId, factory.createCrosswordQuizzer(playId));
	}
	
	private Quizzer<Crossword> loadQuizzer() {
		return quizzers.get(playId);
	}
}

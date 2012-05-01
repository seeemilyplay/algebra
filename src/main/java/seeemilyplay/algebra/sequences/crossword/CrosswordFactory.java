package seeemilyplay.algebra.sequences.crossword;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Solution;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;

final class CrosswordFactory implements CreamSolutionInterpretter<Crossword> {

	private final CrosswordRepository crosswordRepository;
	private final IntVariable questionIndex;

	public CrosswordFactory(
			CrosswordRepository crosswordRepository,
			IntVariable questionIndex) {
		this.crosswordRepository = crosswordRepository;
		this.questionIndex = questionIndex;
	}

	public Crossword convertToQuestion(Solution solution) {
		int questionIndex = getQuestionIndex(solution);
		return crosswordRepository.getCrossword(questionIndex);
	}

	private int getQuestionIndex(Solution solution) {
		return solution.getIntValue(questionIndex);
	}
}

package seeemilyplay.algebra.sequences.crossword;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;

import seeemilyplay.algebra.progress.Level;
import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;
import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.IntegerConstraints;
import seeemilyplay.quizzer.cream.IntegerRange;

final class CrosswordSpaceDefinition implements CreamSpaceDefinition<Crossword> {

	private final CrosswordRepository crosswordRepository;
	private final Level level;

	public CrosswordSpaceDefinition(
			CrosswordRepository crosswordRepository,
			Level level) {
		this.crosswordRepository = crosswordRepository;
		this.level = level;
	}

	public CreamSolutionInterpretter<Crossword> installConstraints(
			Network network) {
		IntVariable index = new IntVariable(network);

		IntegerConstraints indexConstraints = getIndexConstraints();
		indexConstraints.install(index);

		return new CrosswordFactory(
				crosswordRepository,
				index);
	}

	private IntegerConstraints getIndexConstraints() {
		return new IntegerRange(getMinIndex(), getMaxIndex());
	}

	private int getMinIndex() {
		return crosswordRepository.getFirstIndex(level);
	}

	private int getMaxIndex() {
		return crosswordRepository.getLastIndex(level);
	}
}

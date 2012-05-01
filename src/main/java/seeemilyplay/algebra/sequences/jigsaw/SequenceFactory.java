package seeemilyplay.algebra.sequences.jigsaw;

import java.util.HashMap;
import java.util.Map;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Solution;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;

final class SequenceFactory implements CreamSolutionInterpretter<Sequence> {

	private final IntVariable type;
	private final IntVariable firstTerm;
	private final IntVariable difference;

	private final Map<PuzzleType, SequenceCreator> sequenceCreators =
		new HashMap<PuzzleType, SequenceCreator>();
	
	private Solution solution;
	
	public SequenceFactory(
			IntVariable type,
			IntVariable firstTerm,
			IntVariable difference) {
		this.type = type;
		this.firstTerm = firstTerm;
		this.difference = difference;
		
		initSequenceCreators();
	}
	
	private void initSequenceCreators() {
		init(PuzzleType.ARITHMETIC, new ArithmeticSequenceCreator());
		init(PuzzleType.GEOMETRIC, new GeometricSequenceCreator());
		init(PuzzleType.SQUARES, new SquaresSequenceCreator());
	}
	
	private void init(PuzzleType type, SequenceCreator sequenceCreator) {
		sequenceCreators.put(type, sequenceCreator);
	}
	
	public Sequence convertToQuestion(Solution solution) {
		init(solution);
		
		return getSequenceCreator().createSequence();
	}
	
	private void init(Solution solution) {
		this.solution = solution;
	}
	
	private SequenceCreator getSequenceCreator() {
		return sequenceCreators.get(getType());
	}
	
	private PuzzleType getType() {
		int ordinal = solution.getIntValue(type);
		return PuzzleType.values()[ordinal];
	}
	
	private int getFirstTerm() {
		return solution.getIntValue(firstTerm);
	}
	
	private int getDifference() {
		return solution.getIntValue(difference);
	}
	
	private interface SequenceCreator {
		
		public Sequence createSequence();
	}
	
	private class ArithmeticSequenceCreator implements SequenceCreator {
		
		public ArithmeticSequenceCreator() {
			super();
		}

		public Sequence createSequence() {
			return new ArithmeticSequence(
					getFirstTerm(), 
					getDifference());
		}	
	}
	
	private class GeometricSequenceCreator implements SequenceCreator {
		
		public GeometricSequenceCreator() {
			super();
		}
		
		public Sequence createSequence() {
			return new GeometricSequence(
					getFirstTerm(), 
					getDifference());
		}
	}
	
	private class SquaresSequenceCreator implements SequenceCreator {
		
		public SquaresSequenceCreator() {
			super();
		}
		
		public Sequence createSequence() {
			return new SquaresSequence(
					getFirstTerm());
		}
	}
}

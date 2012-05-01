package seeemilyplay.algebra.sequences.jigsaw;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;
import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.IntegerConstraints;

final class SequenceSpaceDefinition implements CreamSpaceDefinition<Sequence> {

	private final IntegerConstraints constraintsOnType;
	private final IntegerConstraints constraintsOnFirstTerm;
	private final IntegerConstraints constraintsOnDifference;

	public SequenceSpaceDefinition(
			IntegerConstraints constraintsOnType,
			IntegerConstraints constraintsOnFirstTerm,
			IntegerConstraints constraintsOnDifference) {
		this.constraintsOnType = constraintsOnType;
		this.constraintsOnFirstTerm = constraintsOnFirstTerm;
		this.constraintsOnDifference = constraintsOnDifference;
	}

	public CreamSolutionInterpretter<Sequence> installConstraints(
			Network network) {

		
		IntVariable type = new IntVariable(network);
		IntVariable firstTerm = new IntVariable(network);
		IntVariable difference = new IntVariable(network);

		constraintsOnType.install(type);
		constraintsOnFirstTerm.install(firstTerm);
		constraintsOnDifference.install(difference);

		
		return new SequenceFactory(
				type,
				firstTerm,
				difference);
	}
}

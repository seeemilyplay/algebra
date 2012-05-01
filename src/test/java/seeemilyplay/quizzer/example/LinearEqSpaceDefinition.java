package seeemilyplay.quizzer.example;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;
import seeemilyplay.quizzer.cream.CreamSpaceDefinition;
import seeemilyplay.quizzer.cream.IntegerConstraints;

final class LinearEqSpaceDefinition implements CreamSpaceDefinition<LinearEquation> {

	private final IntegerConstraints constraintsOnM;
	private final IntegerConstraints constraintsOnC;

	public LinearEqSpaceDefinition(
			IntegerConstraints constraintsOnM,
			IntegerConstraints constraintsOnC) {
		this.constraintsOnM = constraintsOnM;
		this.constraintsOnC = constraintsOnC;
	}

	public CreamSolutionInterpretter<LinearEquation> installConstraints(
			Network network) {

		IntVariable m = new IntVariable(network);
		IntVariable c = new IntVariable(network);

		constraintsOnM.install(m);
		constraintsOnC.install(c);

		return new LinearEqFactory(m, c);
	}
}

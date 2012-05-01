package seeemilyplay.quizzer.example;

import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Solution;

import seeemilyplay.quizzer.cream.CreamSolutionInterpretter;

final class LinearEqFactory implements CreamSolutionInterpretter<LinearEquation> {

	private final IntVariable m;
	private final IntVariable c;

	public LinearEqFactory(
			IntVariable m,
			IntVariable c) {
		this.m = m;
		this.c = c;
	}

	public LinearEquation convertToQuestion(Solution solution) {
		return createLinearQuestion(
				getM(solution),
				getC(solution));
	}

	private LinearEquation createLinearQuestion(int m, int c) {
		return new LinearEquation(m, c);
	}

	private int getM(Solution solution) {
		return solution.getIntValue(m);
	}

	private int getC(Solution solution) {
		return solution.getIntValue(c);
	}
}

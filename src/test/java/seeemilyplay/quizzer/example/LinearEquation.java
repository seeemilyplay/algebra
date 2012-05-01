package seeemilyplay.quizzer.example;

import seeemilyplay.quizzer.core.Question;

public final class LinearEquation implements Question {

	private final int m;
	private final int c;

	public LinearEquation(int m, int c) {
		this.m = m;
		this.c = c;
	}

	public int getM() {
		return m;
	}

	public int getC() {
		return c;
	}

	public int hashCode() {
		return m + c;
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		LinearEquation other = (LinearEquation)obj;
		return (m==other.m && c==other.c);
	}

	public String toString() {
		return ("[m=" + m + " c=" + c + "]");
	}
}

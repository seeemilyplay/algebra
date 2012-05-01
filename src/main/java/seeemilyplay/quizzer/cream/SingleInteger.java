package seeemilyplay.quizzer.cream;

import jp.ac.kobe_u.cs.cream.IntVariable;

public final class SingleInteger implements IntegerConstraints {

	private final int value;

	public SingleInteger(int value) {
		this.value = value;
	}

	public void install(IntVariable var) {
		var.equals(value);
	}
}
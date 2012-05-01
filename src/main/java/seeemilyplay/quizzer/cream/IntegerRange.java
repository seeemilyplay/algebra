package seeemilyplay.quizzer.cream;

import jp.ac.kobe_u.cs.cream.IntVariable;

public final class IntegerRange implements IntegerConstraints {

	private final int min;
	private final int max;

	public IntegerRange(int min, int max) {
		this.min = min;
		this.max = max;
		validate();
	}

	public void install(IntVariable var) {
		var.ge(min);
		var.le(max);
	}

	private void validate() {
		if (min>max) {
			throw new IllegalArgumentException();
		}
	}
}

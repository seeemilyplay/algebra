package seeemilyplay.quizzer.core;

/**
 * Data structure object for storing numbers representing
 * probabilities.
 */
public final class Probability {

	private final double value;

	public Probability(double value) {
		this.value = value;
		validate();
	}

	public static Probability createProbability(double value) {
		double limitedValue = Math.max(Math.min(value, 1.0), 0.0);
		return new Probability(limitedValue);
	}

	public static Probability getCertainty() {
		return new Probability(1.0);
	}

	public static Probability getImpossibility() {
		return new Probability(0.0);
	}

	private void validate() {
		if (value<0.0 || value>1.0) {
			throw new IllegalArgumentException();
		}
	}

	public double getValue() {
		return value;
	}

	public int hashCode() {
		long temp = Double.doubleToLongBits(value);
		return 1 + (int) (temp ^ (temp >>> 32));
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		Probability other = (Probability)obj;
		return value==other.value;
	}

	public String toString() {
		return Double.toString(value);
	}
}

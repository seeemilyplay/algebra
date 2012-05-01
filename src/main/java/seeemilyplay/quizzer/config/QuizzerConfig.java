package seeemilyplay.quizzer.config;

/**
 * Data structure that stores important constants
 * used by the <code>Quizzer</code> and related classes.
 */
public final class QuizzerConfig {

	private static final double DEFAULT_TARGET_PROBABILITY = 0.7;
	private static final int DEFAULT_NO_QUESTIONS_WITHOUT_REPS = 3;

	private static final double DEFAULT_AWARD = 0.2;
	private static final double DEFAULT_PENALTY = 0.5;

	private static final double DEFAULT_UP_RIPPLE_DECAY = 0.05;
	private static final double DEFAULT_DOWN_RIPPLE_DECAY = 0.1;

	private double targetProbability = DEFAULT_TARGET_PROBABILITY;
	private int noQuestionsWithoutReps = DEFAULT_NO_QUESTIONS_WITHOUT_REPS;

	private double award = DEFAULT_AWARD;
	private double penalty = DEFAULT_PENALTY;

	private double upRippleDecay = DEFAULT_UP_RIPPLE_DECAY;
	private double downRippleDecay = DEFAULT_DOWN_RIPPLE_DECAY;

	public QuizzerConfig() {
		super();
	}

	public double getTargetProbability() {
		return targetProbability;
	}

	public void setTargetProbability(double targetProbability) {
		this.targetProbability = targetProbability;
	}

	public int getNoQuestionsWithoutReps() {
		return noQuestionsWithoutReps;
	}

	public void setNoQuestionsWithoutReps(int noQuestionsWithoutReps) {
		this.noQuestionsWithoutReps = noQuestionsWithoutReps;
	}

	public double getAward() {
		return award;
	}

	public void setAward(double award) {
		this.award = award;
	}

	public double getPenalty() {
		return penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public double getUpRippleDecay() {
		return upRippleDecay;
	}

	public void setUpRippleDecay(double upRippleDecay) {
		this.upRippleDecay = upRippleDecay;
	}

	public double getDownRippleDecay() {
		return downRippleDecay;
	}

	public void setDownRippleDecay(double downRippleDecay) {
		this.downRippleDecay = downRippleDecay;
	}
}

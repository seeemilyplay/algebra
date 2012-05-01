package seeemilyplay.algebra.goals;

import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.Level;

public final class Goal implements Comparable<Goal> {

	private final Level level;
	private final Award award;
	private final double progress;

	Goal(
			Level level,
			Award award,
			double progress) {
		this.level = level;
		this.award = award;
		this.progress = progress;
	}

	public Level getLevel() {
		return level;
	}

	public Award getAward() {
		return award;
	}

	public double getProgress() {
		return progress;
	}

	public int compareTo(Goal o) {
		return Double.compare(progress, o.progress);
	}
}

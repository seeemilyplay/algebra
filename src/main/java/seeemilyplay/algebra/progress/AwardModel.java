package seeemilyplay.algebra.progress;

import java.util.HashMap;
import java.util.Map;

public final class AwardModel {

	private final Map<Level,Award> awards =
		new HashMap<Level,Award>();

	AwardModel() {
		super();
		initAwards();
	}

	private void initAwards() {
		initNoviceAward();
		initIntermediateAward();
		initExpertAward();
	}

	private void initNoviceAward() {
		awards.put(Level.NOVICE, new NoviceAward());
	}

	private void initIntermediateAward() {
		awards.put(Level.INTERMEDIATE, new IntermediateAward());
	}

	private void initExpertAward() {
		awards.put(Level.EXPERT, new ExpertAward());
	}

	public Award getAward(Level level) {
		return awards.get(level);
	}
}

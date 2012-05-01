package seeemilyplay.algebra.bulletin.notices;

import seeemilyplay.algebra.bulletin.Notice;
import seeemilyplay.algebra.progress.Award;

public final class AwardNotice implements Notice {

	private final Award award;
	private final Award nextAward;
	
	public AwardNotice(Award award, Award nextAward) {
		this.award = award;
		this.nextAward = nextAward;
	}
	
	public Award getAward() {
		return award;
	}
	
	public boolean isNextAward() {
		return nextAward!=null;
	}
	
	public Award getNextAward() {
		return nextAward;
	}
}

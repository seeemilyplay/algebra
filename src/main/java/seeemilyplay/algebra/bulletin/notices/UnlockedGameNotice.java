package seeemilyplay.algebra.bulletin.notices;

import seeemilyplay.algebra.bulletin.Notice;

public final class UnlockedGameNotice implements Notice {

	private final String game;
	
	public UnlockedGameNotice(String game) {
		this.game = game;
	}
	
	public String getGame() {
		return game;
	}
}

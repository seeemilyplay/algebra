package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.bulletin.Notice;
import seeemilyplay.algebra.bulletin.notices.UnlockedGameNotice;
import seeemilyplay.algebra.games.core.GameId;

final class UnlockedGameNoticeFactory implements UnlockNoticeFactory<GameId> {

	public UnlockedGameNoticeFactory() {
		super();
	}
	
	public Notice createUnlockNotice(GameId gameId) {
		String game = getGameName(gameId);
		return new UnlockedGameNotice(game);
	}

	private String getGameName(GameId gameId) {
		return gameId.getName();
	}
}

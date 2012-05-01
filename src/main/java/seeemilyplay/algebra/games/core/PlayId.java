package seeemilyplay.algebra.games.core;

import seeemilyplay.algebra.users.UserId;

public final class PlayId {

	private final GameId gameId;
	private final UserId userId;
	
	public PlayId(
			GameId gameId, 
			UserId userId) {
		this.gameId = gameId;
		this.userId = userId;
	}
	
	public String getId() {
		StringBuilder sb = new StringBuilder();
		sb.append(getGameId());
		sb.append(getUser());
		return sb.toString();
	}
	
	private String getUser() {
		return userId.getName();
	}
	
	private String getGameId() {
		return gameId.getId();
	}

	public int hashCode() {
		return getId().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		PlayId other = (PlayId)obj;
		return getId().equals(other.getId());
	}
}

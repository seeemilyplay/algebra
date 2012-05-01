package seeemilyplay.algebra.users;

/**
 * A user profile.
 */
public final class Profile {

	private long lastLoginTime;

	public Profile() {
		super();
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}

package seeemilyplay.algebra.users;

/**
 * A user.
 */
public final class User {

	private UserId userId;
	private Profile profile;

	public User() {
		super();
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public int hashCode() {
		return userId!=null ?
				userId.hashCode()
				: 0;
	}

	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		User other = (User)obj;
		return userId!=null ?
				userId.equals(other.userId)
				: other.userId==null;
	}
}

package seeemilyplay.algebra.users;

import java.util.Comparator;

/**
 * Orders users so the most recently used coming first.
 */
final class UserComparator implements Comparator<User> {

	public int compare(User o1, User o2) {
		long diff = getDifferenceInLoginTime(o1, o2);
		if (diff==0) {
			diff = getDifferenceInID(o1, o2);
		}
		return toComparisonInt(diff);
	}

	private int toComparisonInt(long diff) {
		if (diff==0) {
			return 0;
		}
		return (diff<0 ? -1 : 1);
	}

	private long getDifferenceInID(User o1, User o2) {
		return getId(o2) - getId(o1);
	}

	private long getId(User user) {
		UserId userId = getUserId(user);
		return getId(userId);
	}

	private UserId getUserId(User user) {
		return user.getUserId();
	}

	private long getId(UserId userId) {
		return userId.getId();
	}

	private long getDifferenceInLoginTime(User o1, User o2) {
		return getLastLoginTime(o2) - getLastLoginTime(o1);
	}

	private long getLastLoginTime(User user) {
		Profile profile = getProfile(user);
		return getLastLoginTime(profile);
	}

	private Profile getProfile(User user) {
		return user.getProfile();
	}

	private long getLastLoginTime(Profile profile) {
		return profile.getLastLoginTime();
	}
}

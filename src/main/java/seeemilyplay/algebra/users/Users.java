package seeemilyplay.algebra.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Stores users.
 */
final class Users {

	private SortedSet<User> users = new TreeSet<User>(new UserComparator());
	private SortedSet<String> lowerCaseNames = new TreeSet<String>();
	private SortedSet<Long> ids = new TreeSet<Long>();

	public Users(List<User> users) {
		saveUsers(users);
	}

	private void saveUsers(List<User> users) {
		for (User user : users) {
			save(user);
		}
	}

	public synchronized void save(User user) {
		users.add(user);
		lowerCaseNames.add(getLowerCaseName(user));
		ids.add(getId(user));
	}

	private String getLowerCaseName(User user) {
		String name = getName(user);
		return toLowerCase(name);
	}

	private long getId(User user) {
		UserId userId = getUserId(user);
		return userId.getId();
	}

	private String getName(User user) {
		UserId userId = getUserId(user);
		return userId.getName();
	}

	private UserId getUserId(User user) {
		return user.getUserId();
	}

	private String toLowerCase(String name) {
		return name.toLowerCase();
	}

	public synchronized List<User> getUsers() {
		return Collections.unmodifiableList(
				new ArrayList<User>(users));
	}

	public synchronized boolean isExistingName(String name) {
		String lowerCaseName = toLowerCase(name);
		return isExistingLowerCaseName(lowerCaseName);
	}

	private boolean isExistingLowerCaseName(String lowerCaseName) {
		return (lowerCaseNames.contains(lowerCaseName));
	}

	public synchronized long getNextId() {
		if (ids.isEmpty()) {
			return 0;
		} else {
			return ids.last() + 1;
		}
	}
}

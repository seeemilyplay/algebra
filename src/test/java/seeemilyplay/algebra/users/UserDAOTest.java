package seeemilyplay.algebra.users;

import java.util.List;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;

public class UserDAOTest extends TestCase {

	private UserDAO userDao;
	private User savedUser;

	protected void setUp() throws Exception {
		super.setUp();
		userDao = new UserDAO();
		savedUser = null;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testSavedUsersWillBeLoadedInTheFuture() {

		whenSaveUsers("a", "b", "c");

		thenUserLoadReturns("a", "b", "c");
	}

	public void testUpdatingAProfile() {

		givenSavedUser();

		whenLastLoginUpdatedTo(13);

		thenHasLastLoginTime(13);

	}

	private void givenSavedUser() {
		savedUser = createUser(0, "x");
		userDao.saveUser(savedUser);
	}

	private void whenSaveUsers(String ... usernames) {
		saveUsers(usernames);
	}

	private void saveUsers(String[] usernames) {
		for (int i=0; i<usernames.length; i++) {
			User user = createUser(i, usernames[i]);
			userDao.saveUser(user);
		}
	}

	private User createUser(long id, String name) {
		User user = new User();
		user.setUserId(createUserId(id, name));
		user.setProfile(createProfile());
		return user;
	}

	private UserId createUserId(long id, String name) {
		return new UserId(id, name);
	}

	private Profile createProfile() {
		return new Profile();
	}

	private void whenLastLoginUpdatedTo(long lastLoginTime) {
		Profile profile = savedUser.getProfile();
		profile.setLastLoginTime(lastLoginTime);
		userDao.saveProfile(profile);
	}

	private void thenUserLoadReturns(String ... usernames) {
		List<User> users = userDao.loadUsers();
		assertEquals(usernames.length, users.size());
		for (int i=0; i<usernames.length; i++) {
			User user = users.get(i);
			assertEquals(usernames[i], getName(user));
		}
	}

	private String getName(User user) {
		UserId userId = user.getUserId();
		return userId.getName();
	}

	private void thenHasLastLoginTime(long lastLoginTime) {
		User savedUser = userDao.loadUsers().get(0);
		Profile profile = savedUser.getProfile();
		assertEquals(this.savedUser.getUserId().getName(), savedUser.getUserId().getName());
		assertEquals(lastLoginTime, profile.getLastLoginTime());
	}
}

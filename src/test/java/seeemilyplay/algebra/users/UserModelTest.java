package seeemilyplay.algebra.users;

import java.util.List;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.test.TestUtils;

public class UserModelTest extends TestCase {

	private UserDAO userDao;
	private UserModel userModel;
	private Throwable t;
	private Listener listener;
	private boolean isChangeFired;

	protected void setUp() throws Exception {
		userDao = new UserDAO();
		userModel = null;
		t = null;
		listener = null;
		isChangeFired = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testUserModelLoadsUsersFromDAO() {

		givenUserDaoWith("a", "B", "c");

		whenUserModelCreated();

		thenUserModelHas("c", "B", "a");
	}

	public void testSavingANewUser() {

		givenUserModelWith("a", "b", "C");

		whenSaveNewUser("D");

		thenUserModelHas("D", "C", "b", "a");
	}

	public void testSavingANewUserFailsIfNameInUse() {

		givenUserModelWith("a", "b", "c");

		whenSaveNewUser("A");

		thenExceptionIsThrown();
	}

	public void testSavingANewUserFiresAChange() {

		givenUserModelWith();

		whenSaveNewUser("A");

		thenChangeIsFired();
	}

	public void testIgnoresCaseWhenCheckingIfUsernameExists() {

		givenUserDaoWith("A", "b", "c");

		whenUserModelCreated();

		thenUsernameExists("a");
	}

	public void testCanTellWhenUsernameDoesntExist() {

		givenUserDaoWith("A", "b", "c");

		whenUserModelCreated();

		thenUsernameDoesNotExist("d");
	}

	public void testUpdatedLastLoginTimeIsRecordedByModel() {

		givenUserModelWith("A", "B", "c");

		whenUpdateLastLoginTime("B", 15);

		thenLastLoginTimeIs("B", 15);
	}

	public void testUpdatingALastLoginTimeFiresAChange() {

		givenUserModelWith("A", "B", "c");

		whenUpdateLastLoginTime("B", 15);

		thenChangeIsFired();
	}

	private void givenUserModelWith(String ... usernames) {
		saveUsers(usernames);
		createUserModel();

	}

	private void givenUserDaoWith(String ... usernames) {
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

	private void whenUserModelCreated() {
		createUserModel();
	}

	private void createUserModel() {
		userModel = new UserModel(userDao);
		TestUtils.prepareModel(userModel);
		initListener();
	}

	private void initListener() {
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		userModel.addListener(listener);
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private void whenSaveNewUser(String username) {
		try {
			resetListener();
			userModel.saveNewUser(username);
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void whenUpdateLastLoginTime(String username, long lastLoginTime) {
		User user = getUser(username);
		resetListener();
		userModel.updateLastLoginTime(user, lastLoginTime);
	}

	private User getUser(String username) {
		for (User user : userModel.getUsers()) {
			UserId userId = user.getUserId();
			String name = userId.getName();
			if (username.equals(name)) {
				return user;
			}
		}
		throw new Error();
	}

	private void thenLastLoginTimeIs(String username, long lastLoginTime) {
		User user = getUser(username);
		Profile profile = user.getProfile();
		assertEquals(lastLoginTime, profile.getLastLoginTime());
	}

	private void thenUsernameExists(String name) {
		assertTrue(userModel.isExistingName(name));
	}

	private void thenUsernameDoesNotExist(String name) {
		assertFalse(userModel.isExistingName(name));
	}

	private void thenExceptionIsThrown() {
		assertNotNull(t);
	}

	private void thenChangeIsFired() {
		assertTrue(isChangeFired);
	}

	private void thenUserModelHas(String ... usernames) {
		List<User> users = userModel.getUsers();
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
}

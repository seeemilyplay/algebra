package seeemilyplay.algebra.users;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.test.TestUtils;

public class LogInModelTest extends TestCase {

	private UserModel userModel;
	private LogInModel logInModel;
	private long originalLastLoginTime;
	private long newLastLoginTime;
	private Listener listener;
	private boolean isChangeFired;

	protected void setUp() throws Exception {
		super.setUp();
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		logInModel = null;
		originalLastLoginTime = 0;
		newLastLoginTime = 0;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testLoggingIn() {

		givenLogInModelAndUsers("a", "b", "c");

		whenLogInAs("b");

		thenLoggedInAs("b");
	}

	public void testLoggingInFiresChange() {

		givenLogInModelAndUsers("a", "b", "c");

		whenLogInAs("b");

		thenChangeIsFired();
	}

	public void testLoggingInUpdatesLastLoginTime() {

		givenLogInModelAndUsers("a", "b", "c");

		whenLogInAs("b");

		thenLastLoginTimeIsUpdated();
	}

	public void testLoggingOut() {

		givenLoggedInAs("a");

		whenLogOut();

		thenNobodyIsLoggedIn();
	}

	public void testLoggingOutFiresChange() {

		givenLoggedInAs("a");

		whenLogOut();

		thenChangeIsFired();
	}

	private void givenLoggedInAs(String name) {
		saveUsers(name);
		createLogInModel();
		logInAs(name);
	}

	private void givenLogInModelAndUsers(String ... names) {
		saveUsers(names);
		createLogInModel();
	}

	private void saveUsers(String ... names) {
		for (String name : names) {
			userModel.saveNewUser(name);
		}
	}

	private void createLogInModel() {
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		initListener();
	}

	private void initListener() {
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		logInModel.addListener(listener);
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private void whenLogInAs(String name) {
		logInAs(name);
	}

	private void logInAs(String name) {
		User user = getUser(name);
		originalLastLoginTime = getLastLoginTime(user);
		resetListener();
		logInModel.login(user);
		newLastLoginTime = getLastLoginTime(user);
	}

	private long getLastLoginTime(User user) {
		Profile profile = user.getProfile();
		return profile.getLastLoginTime();
	}

	private User getUser(String name) {
		for (User user : userModel.getUsers()) {
			if (name.equals(getName(user))) {
				return user;
			}
		}
		throw new Error();
	}

	private String getName(User user) {
		return getName(user.getUserId());
	}

	private String getName(UserId userId) {
		return userId.getName();
	}

	private void whenLogOut() {
		resetListener();
		logInModel.logout();
	}

	private void thenLoggedInAs(String name) {
		assertTrue(logInModel.isLoggedIn());
		assertEquals(name, getLoggedInUserName());
	}

	private String getLoggedInUserName() {
		return getName(getLoggedInUser());
	}

	private UserId getLoggedInUser() {
		return logInModel.getLoggedInUser();
	}

	private void thenNobodyIsLoggedIn() {
		assertFalse(logInModel.isLoggedIn());
		assertNull(getLoggedInUser());
	}

	private void thenLastLoginTimeIsUpdated() {
		assertTrue(newLastLoginTime>originalLastLoginTime);
	}

	private void thenChangeIsFired() {
		assertTrue(isChangeFired);
	}
}

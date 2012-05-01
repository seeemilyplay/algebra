package seeemilyplay.algebra.users;

import java.util.List;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.test.TestUtils;

public class MultiplerUserModelTest extends TestCase {

	private UserModel userModel;
	private LogInModel logInModel;
	private MultipleUserModel multipleUserModel;
	private Listener listener;
	private boolean isChangeFired;

	protected void setUp() throws Exception {
		super.setUp();
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		multipleUserModel = null;
		listener = null;
		isChangeFired = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testDeterminesCorrectUserNames() {

		givenUserModelWithUsers("a", "b", "c");

		whenMultipleUserModelCreated();

		thenUsersAre("c", "b", "a");
	}

	public void testLoggingInWorks() {

		givenMultipleUserModelWithUsers("a", "b", "c");

		whenLogInAs("a");

		thenLoggedInAs("a");
	}

	public void testRequestingNewUserFiresChange() {

		givenMultipleUserModelWithUsers("a", "b", "c");

		whenCreateNewUser();

		thenChangeIsFired();
	}

	private void givenMultipleUserModelWithUsers(String ... names) {
		saveInUserModel(names);
		createMultipleUserModel();
	}

	private void givenUserModelWithUsers(String ... names) {
		saveInUserModel(names);
	}

	private void saveInUserModel(String[] names) {
		for (String name : names) {
			userModel.saveNewUser(name);
		}
	}

	private void whenMultipleUserModelCreated() {
		createMultipleUserModel();
	}

	private void createMultipleUserModel() {
		multipleUserModel = new MultipleUserModel(userModel, logInModel);
		TestUtils.prepareModel(multipleUserModel);
		initListener();
	}

	private void initListener() {
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		multipleUserModel.addListener(listener);
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private void whenLogInAs(String name) {
		User user = getUser(name);
		multipleUserModel.loginAs(user);
	}

	private User getUser(String name) {
		for (User user : multipleUserModel.getUsers()) {
			if (name.equals(getName(user))) {
				return user;
			}
		}
		throw new Error();
	}


	private void whenCreateNewUser() {
		resetListener();
		multipleUserModel.createNewUser();
	}

	private void thenUsersAre(String ... expectedNames) {
		List<User> users = multipleUserModel.getUsers();
		assertEquals(expectedNames.length, users.size());
		for (int i=0; i<expectedNames.length; i++) {
			String name = getName(users.get(i));
			assertEquals(expectedNames[i], name);
		}
	}

	private String getName(User user) {
		UserId userId = user.getUserId();
		return userId.getName();
	}

	private void thenChangeIsFired() {
		assertTrue(isChangeFired);
	}

	private void thenLoggedInAs(String name) {
		assertEquals(name, getLoggedInName());
	}

	private String getLoggedInName() {
		UserId loggedInUser = getLoggedInUser();
		return loggedInUser.getName();
	}

	private UserId getLoggedInUser() {
		return logInModel.getLoggedInUser();
	}

}

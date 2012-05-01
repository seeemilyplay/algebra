package seeemilyplay.algebra.users;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.test.TestUtils;

public class SingleUserModelTest extends TestCase {

	private UserModel userModel;
	private LogInModel logInModel;
	private SingleUserModel singleUserModel;
	private Listener listener;
	private boolean isChangeFired;

	protected void setUp() throws Exception {
		super.setUp();
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		singleUserModel = null;
		listener = null;
		isChangeFired = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testDeterminesCorrectSingleUserName() {

		givenUserModelWithUser("testUser");

		whenSingleUserModelCreated();

		thenSingleUserNameIs("testUser");
	}

	public void testLoggingInWorks() {

		givenSingleUserModelForUser("testUser");

		whenLogInAsSingleUser();

		thenLoggedInAs("testUser");
	}

	public void testRequestingNewUserFiresChange() {

		givenSingleUserModelForUser("testUser");

		whenCreateNewUser();

		thenChangeIsFired();
	}

	private void givenSingleUserModelForUser(String name) {
		saveInUserModel(name);
		createSingleUserModel();
	}

	private void givenUserModelWithUser(String name) {
		saveInUserModel(name);
	}

	private void saveInUserModel(String name) {
		userModel.saveNewUser(name);
	}

	private void whenSingleUserModelCreated() {
		createSingleUserModel();
	}

	private void createSingleUserModel() {
		singleUserModel = new SingleUserModel(userModel, logInModel);
		TestUtils.prepareModel(singleUserModel);
		initListener();
	}

	private void initListener() {
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		singleUserModel.addListener(listener);
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private void whenLogInAsSingleUser() {
		singleUserModel.loginAsSingleUser();
	}

	private void whenCreateNewUser() {
		resetListener();
		singleUserModel.createNewUser();
	}

	private void thenSingleUserNameIs(String expectedName) {
		assertEquals(expectedName, singleUserModel.getSingleUserName());
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

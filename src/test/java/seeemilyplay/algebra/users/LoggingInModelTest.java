package seeemilyplay.algebra.users;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.test.TestUtils;

public class LoggingInModelTest extends TestCase {

	private UserModel userModel;
	private LogInModel logInModel;
	private LoggingInModel loggingInModel;

	protected void setUp() throws Exception {
		super.setUp();
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		loggingInModel = null;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testCreatingNewUserWhenNoExistingUsers() {

		whenLoggingInModelCreated();

		thenIsCreatingNewUser();
	}

	public void testCreatingFirstUser() {

		givenCreatingFirstUser();

		whenUserCreated();

		thenLoggedIn();
	}

	public void testSelectingFromSingleUserWhenJustOneUser() {

		givenUsers("a");

		whenLoggingInModelCreated();

		thenIsSelectingFromSingleUser();
	}

	public void testNewUserCreationWhenSelectingFromSingleUser() {

		givenSelectingFromSingleUser();

		whenNewUserRequested();

		thenIsCreatingAdditionalUser();
	}

	public void testCancellingAdditionalUserCreationWhileSelectingFromSingleUser() {

		givenSelectingFromSingleUser();

		whenNewUserRequested();
		andUserCreationCancelled();

		thenIsSelectingFromSingleUser();
	}

	public void testSelectingFromMultipleUserWhenMoreThanOneUser() {

		givenUsers("a", "b");

		whenLoggingInModelCreated();

		thenIsSelectingFromMultipleUsers();
	}

	public void testNewUserCreationWhenSelectingFromMultipleUsers() {

		givenSelectingFromMultipleUsers();

		whenNewUserRequested();

		thenIsCreatingAdditionalUser();
	}

	public void testCancellingAdditionalUserCreationWhileSelectingFromMultipleUsers() {

		givenSelectingFromMultipleUsers();

		whenNewUserRequested();
		andUserCreationCancelled();

		thenIsSelectingFromMultipleUsers();
	}

	private void givenSelectingFromSingleUser() {
		saveUsers("a");
		createLoggingInModel();
	}

	private void givenSelectingFromMultipleUsers() {
		saveUsers("a", "b");
		createLoggingInModel();
	}

	private void givenCreatingFirstUser() {
		createLoggingInModel();
	}

	private void givenUsers(String ... names) {
		saveUsers(names);
	}

	private void saveUsers(String ... names) {
		for (String name : names) {
			userModel.saveNewUser(name);
		}
	}

	private void whenLoggingInModelCreated() {
		createLoggingInModel();
	}

	private void createLoggingInModel() {
		loggingInModel = new LoggingInModel(userModel, logInModel);
		TestUtils.prepareModel(loggingInModel);
	}

	private void whenUserCreated() {
		NewUserModel newUserModel = loggingInModel.createNewUserModel();
		TestUtils.prepareModel(newUserModel);
		newUserModel.setSuggestedName("new user");
		newUserModel.saveNewUserAndLogin();
	}

	private void whenNewUserRequested() {
		if (loggingInModel.isSelectingFromSingleUser()) {
			requestNewUserFromSingleUserModel();
		} else {
			requestNewUserFromMultipleUserModel();
		}
	}

	private void requestNewUserFromSingleUserModel() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					SingleUserModel singleUserModel = loggingInModel.createSingleUserModel();
					singleUserModel.createNewUser();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void requestNewUserFromMultipleUserModel() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					MultipleUserModel multipleUserModel = loggingInModel.createMultipleUserModel();
					multipleUserModel.createNewUser();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void andUserCreationCancelled() {
		loggingInModel.cancelUserCreation();
	}

	private void thenLoggedIn() {
		assertTrue(logInModel.isLoggedIn());
	}

	private void thenIsCreatingNewUser() {
		assertTrue(loggingInModel.isCreatingFirstUser());
		assertFalse(loggingInModel.isCreatingAdditionalUser());
		assertFalse(loggingInModel.isSelectingFromSingleUser());
	}

	private void thenIsCreatingAdditionalUser() {
		assertFalse(loggingInModel.isCreatingFirstUser());
		assertTrue(loggingInModel.isCreatingAdditionalUser());
	}

	private void thenIsSelectingFromSingleUser() {
		assertFalse(loggingInModel.isCreatingFirstUser());
		assertFalse(loggingInModel.isCreatingAdditionalUser());
		assertTrue(loggingInModel.isSelectingFromSingleUser());
	}

	private void thenIsSelectingFromMultipleUsers() {
		assertFalse(loggingInModel.isCreatingFirstUser());
		assertFalse(loggingInModel.isCreatingAdditionalUser());
		assertFalse(loggingInModel.isSelectingFromSingleUser());
	}
}

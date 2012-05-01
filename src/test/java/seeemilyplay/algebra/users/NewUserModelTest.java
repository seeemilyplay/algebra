package seeemilyplay.algebra.users;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.test.TestUtils;

public class NewUserModelTest extends TestCase {

	private UserModel userModel;
	private LogInModel logInModel;
	private NewUserModel newUserModel;
	private Listener listener;
	private boolean isChangeFired;

	protected void setUp() throws Exception {
		super.setUp();
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		newUserModel = null;
		listener = null;
		isChangeFired = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testSuggestingAValidName() {

		givenNewUserModel();

		whenSuggest("a");

		thenSuggestionIsValid();
	}

	public void testSuggestingANameFiresChange() {

		givenNewUserModel();

		whenSuggest("a");

		thenChangeIsFired();
	}

	public void testSuggestingAnExistingNameIsRecognised() {

		givenNewUserModelWith("a", "b", "c");

		whenSuggest("B");

		thenSuggestedExistingName();
	}

	public void testSuggestionOfExistingNameIsNotValid() {

		givenNewUserModelWith("a", "b", "c");

		whenSuggest("B");

		thenSuggestionIsNotValid();
	}

	public void testSuggestingEmptyStringIsRecognisedAsNothing() {

		givenNewUserModel();

		whenSuggest("");

		thenNoSuggestionMade();
	}

	public void testSuggestingEmptyStringIsNotValid() {

		givenNewUserModel();

		whenSuggest("");

		thenSuggestionIsNotValid();
	}

	public void testSuggestingNullIsRecognisedAsNothing() {

		givenNewUserModel();

		whenSuggest(null);

		thenNoSuggestionMade();
	}

	public void testSuggestingNullIsNotValid() {

		givenNewUserModel();

		whenSuggest("");

		thenSuggestionIsNotValid();
	}

	public void testSaveAndLoginSucceeedsWithValidSuggestion() {

		givenNewUserModelWith("a", "b");

		whenSuggest("c");

		thenSaveAndLoginSucceeds();
	}

	public void testSaveAndLoginFailsWithInvalidSuggestion() {

		givenNewUserModelWith("a", "b");

		whenSuggest("b");

		thenSaveAndLoginFails();
	}

	private void givenNewUserModelWith(String ... names) {
		createUserModel();
		saveUsers(names);
	}

	private void saveUsers(String[] names) {
		for (String name : names) {
			userModel.saveNewUser(name);
		}
	}

	private void givenNewUserModel() {
		createUserModel();
	}

	private void createUserModel() {
		newUserModel = new NewUserModel(userModel, logInModel);
		TestUtils.prepareModel(newUserModel);
		initListener();
	}

	private void initListener() {
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		newUserModel.addListener(listener);
	}

	private void whenSuggest(String suggestedName) {
		resetListener();
		newUserModel.setSuggestedName(suggestedName);
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private void thenSuggestionIsValid() {
		assertTrue(newUserModel.isValidSuggestion());
	}

	private void thenSuggestionIsNotValid() {
		assertFalse(newUserModel.isValidSuggestion());
	}

	private void thenSuggestedExistingName() {
		assertTrue(newUserModel.isExistingSuggestion());
	}

	private void thenNoSuggestionMade() {
		assertFalse(newUserModel.isSuggestionMade());
	}

	private void thenSaveAndLoginFails() {
		int originalCount = userModel.getUsers().size();
		try {
			newUserModel.saveNewUserAndLogin();
			fail();
		} catch (Throwable t) {
			//expected
		}
		assertEquals(originalCount, userModel.getUsers().size());
	}

	private void thenSaveAndLoginSucceeds() {
		int originalCount = userModel.getUsers().size();
		newUserModel.saveNewUserAndLogin();
		assertEquals(originalCount+1, userModel.getUsers().size());
		assertTrue(logInModel.isLoggedIn());
	}

	private void thenChangeIsFired() {
		assertTrue(isChangeFired);
	}
}

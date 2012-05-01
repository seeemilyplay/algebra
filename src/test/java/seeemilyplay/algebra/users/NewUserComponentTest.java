package seeemilyplay.algebra.users;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.core.matcher.JLabelMatcher;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.core.db.Database;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.test.TestUtils;

public class NewUserComponentTest extends TestCase {

	private static final String EXISTING_USER_NAME = "C c";

	private FrameFixture window;
	private UserModel userModel;
	private LogInModel logInModel;
	private NewUserModel newUserModel;

	protected void setUp() throws Exception {
		super.setUp();
		UserContext.initPhraseBook();
		setUpModels();
		setUpWindow();

	}

	private void setUpModels() {
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		userModel.saveNewUser(EXISTING_USER_NAME);
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		newUserModel = new NewUserModel(userModel, logInModel);
		TestUtils.prepareModel(newUserModel);
	}

	private void setUpWindow() {
		JFrame frame = setUpFrame();
		window = new FrameFixture(frame);
		window.show();
	}

	private JFrame setUpFrame() {
		return GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				NewUserComponent newUserComponent =
					createNewUserComponent();
				return createFrame(newUserComponent);
			}
		});
	}

	private NewUserComponent createNewUserComponent() {
		return new NewUserComponent(newUserModel);
	}

	private JFrame createFrame(NewUserComponent newUserComponent) {
		JFrame frame = new JFrame();
		frame.add(newUserComponent);
		frame.pack();
		return frame;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		window.cleanUp();
		Database.shutdown();
	}

	public void testTypingNewUserAndPressingEnterSavesAndLogsIn() {

		whenTypeIn("a a");
		andPressEnter();

		thenSavedAndLoggedInAs("a a");
	}

	public void testTypingNewUserAndClickingEnterSavesAndLogsIn() {

		whenTypeIn(" b b ");
		andClickOK();

		thenSavedAndLoggedInAs("b b");
	}

	public void testWarningShowedWhenTryToEnterExistingName() {

		whenTypeInExistingUserName();
		andClickOK();

		thenExistingUserWarningDisplayed();
	}

	public void testWarningShowedWhenEnterEmptyName() {

		whenTypeIn(" ");
		andClickOK();

		thenNoSuggestionWarningIsDisplayed();
	}

	private void whenTypeInExistingUserName() {
		whenTypeIn(EXISTING_USER_NAME);
	}

	private void whenTypeIn(String name) {
		window.textBox().enterText(name);
	}

	private void andClickOK() {
		window.button().click();
	}

	private void andPressEnter() {
		window.pressKey(KeyEvent.VK_ENTER);
	}

	private void thenExistingUserWarningDisplayed() {
		assertNotNull(window.label(
				JLabelMatcher.withText(getExistingUserWarningText())));
	}

	private void thenNoSuggestionWarningIsDisplayed() {
		assertNotNull(window.label(
				JLabelMatcher.withText(getNoSuggestionWarningText())));
	}

	private String getExistingUserWarningText() {
		return getUserPhrases().getNameUsedWarning();
	}

	private String getNoSuggestionWarningText() {
		return getUserPhrases().getEnterYourNameWarning();
	}

	private UserPhrases getUserPhrases() {
		return PhraseBook.getPhrases(UserPhrases.class);
	}

	private void thenSavedAndLoggedInAs(String name) {
		checkSaved(name);
		checkedLoggedIn(name);
	}

	private void checkSaved(String name) {
		assertTrue(userModel.isExistingName(name));
	}

	private void checkedLoggedIn(String name) {
		String loggedInName = getLoggedInName();
		assertEquals(name, loggedInName);
	}

	private String getLoggedInName() {
		UserId loggedInUser = logInModel.getLoggedInUser();
		return loggedInUser.getName();
	}
}

package seeemilyplay.algebra.users;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.core.matcher.JLabelMatcher;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.core.db.Database;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.test.TestUtils;

public class MultipleUserComponentTest extends TestCase {

	private static final String[] USERS = new String[] {
		"alfred",
		"beatrice",
		"catherine",
		"diana",
		"edward",
		"frederick",
		"gwyn",
		"harold"};

	private FrameFixture window;
	private UserModel userModel;
	private LogInModel logInModel;
	private MultipleUserModel multipleUserModel;
	private Listener listener;
	private boolean isChangeFired;

	protected void setUp() throws Exception {
		super.setUp();
		UserContext.initPhraseBook();
		setUpModels();
		setUpWindow();

	}

	private void setUpModels() {
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		for (String name : USERS) {
			userModel.saveNewUser(name);
		}
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		multipleUserModel = new MultipleUserModel(userModel, logInModel);
		TestUtils.prepareModel(multipleUserModel);
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		multipleUserModel.addListener(listener);
	}

	private void setUpWindow() {
		JFrame frame = setUpFrame();
		window = new FrameFixture(frame);
		window.show();
	}

	private JFrame setUpFrame() {
		return GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				MultipleUserComponent multipleUserComponent =
					createMultipleUserComponent();
				return createFrame(multipleUserComponent);
			}
		});
	}

	private MultipleUserComponent createMultipleUserComponent() {
		return new MultipleUserComponent(multipleUserModel);
	}

	private JFrame createFrame(MultipleUserComponent singleUserComponent) {
		JFrame frame = new JFrame();
		frame.add(singleUserComponent);
		frame.pack();
		return frame;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		window.cleanUp();
		Database.shutdown();
	}

	public void testClickingANameLogsInAsThem() {

		whenClick("alfred");

		thenLoggedInAs("alfred");
	}

	public void testClickingImSomeoneNewFiresChange() {

		whenClickImSomeoneNew();

		thenChangeIsFired();
	}

	private void whenClick(String name) {

		window.label(JLabelMatcher.withText(name)).click();
	}

	private void whenClickImSomeoneNew() {

		resetListener();
		window.label(JLabelMatcher.withText(
				getImSomeoneNewText())).click();
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private String getImSomeoneNewText() {
		return getUserPhrases().getImSomeoneNew();
	}

	private UserPhrases getUserPhrases() {
		return PhraseBook.getPhrases(UserPhrases.class);
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

	private void thenChangeIsFired() {
		assertTrue(isChangeFired);
	}

}

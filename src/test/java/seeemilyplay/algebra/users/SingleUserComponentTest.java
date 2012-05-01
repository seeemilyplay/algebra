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

public class SingleUserComponentTest extends TestCase {

	private static final String SINGLE_USER_NAME = "the single user";

	private FrameFixture window;
	private UserModel userModel;
	private LogInModel logInModel;
	private SingleUserModel singleUserModel;
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
		userModel.saveNewUser(SINGLE_USER_NAME);
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		singleUserModel = new SingleUserModel(userModel, logInModel);
		TestUtils.prepareModel(singleUserModel);
		listener = new Listener() {
			public void onChange() {
				isChangeFired = true;
			}
		};
		singleUserModel.addListener(listener);
	}

	private void setUpWindow() {
		JFrame frame = setUpFrame();
		window = new FrameFixture(frame);
		window.show();
	}

	private JFrame setUpFrame() {
		return GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				SingleUserComponent singleUserComponent =
					createSingleUserComponent();
				return createFrame(singleUserComponent);
			}
		});
	}

	private SingleUserComponent createSingleUserComponent() {
		return new SingleUserComponent(singleUserModel);
	}

	private JFrame createFrame(SingleUserComponent singleUserComponent) {
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

	public void testClickingYesLogsUserIn() {

		whenClickYes();

		thenLoggedIn();
	}

	public void testClickingNoFiresChange() {

		whenClickNo();

		thenChangeIsFired();
	}

	private void whenClickYes() {

		window.label(getYesLabelMatcher()).click();
	}

	private void whenClickNo() {

		resetListener();
		window.label(getNoLabelMatcher()).click();
	}

	private void resetListener() {
		isChangeFired = false;
	}

	private JLabelMatcher getYesLabelMatcher() {
		return JLabelMatcher.withText(getYesIAmText());
	}

	private JLabelMatcher getNoLabelMatcher() {
		return JLabelMatcher.withText(getNoIAmSomeoneElseText());
	}

	private String getYesIAmText() {
		return getUserPhrases().getYesIAm();
	}

	private String getNoIAmSomeoneElseText() {
		return getUserPhrases().getNoIAmSomeoneElse();
	}

	private UserPhrases getUserPhrases() {
		return PhraseBook.getPhrases(UserPhrases.class);
	}

	private void thenLoggedIn() {
		assertEquals(SINGLE_USER_NAME, getLoggedInName());
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

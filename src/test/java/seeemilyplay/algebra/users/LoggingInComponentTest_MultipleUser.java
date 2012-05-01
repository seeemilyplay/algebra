package seeemilyplay.algebra.users;

import java.awt.Component;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.core.matcher.JLabelMatcher;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.core.components.text.ComponentPhrases;
import seeemilyplay.core.db.Database;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.test.TestUtils;

public class LoggingInComponentTest_MultipleUser extends TestCase {

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
	private LoggingInModel loggingInModel;

	protected void setUp() throws Exception {
		super.setUp();
		UserContext.initPhraseBook();
		setUpModels();
		setUpWindow();
	}

	private void setUpModels() {
		userModel = new UserModel(new UserDAO());
		TestUtils.prepareModel(userModel);
		for (String user : USERS) {
			userModel.saveNewUser(user);
		}
		logInModel = new LogInModel(userModel);
		TestUtils.prepareModel(logInModel);
		loggingInModel = new LoggingInModel(userModel, logInModel);
	}

	private void setUpWindow() {
		JFrame frame = setUpFrame();
		window = new FrameFixture(frame);
		window.show();
	}

	private JFrame setUpFrame() {
		return GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				LoggingInComponent loggingInComponent =
					createLoggingInComponent();
				return createFrame(loggingInComponent);
			}
		});
	}

	private LoggingInComponent createLoggingInComponent() {
		return new LoggingInComponent(loggingInModel);
	}

	private JFrame createFrame(LoggingInComponent loggingInComponent) {
		JFrame frame = new JFrame();
		frame.add(loggingInComponent);
		frame.pack();
		return frame;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		window.cleanUp();
		Database.shutdown();
	}

	public void testMultipleUserComponentDisplayed() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		thenMultipleUserComponentDisplayed();
	}

	public void testRequestingNewUser() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		whenRequestNewUser();

		thenNewUserComponentDisplayed();
	}

	public void testCancellingNewUserRequest() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		givenCreatingNewUser();

		whenCancel();

		thenMultipleUserComponentDisplayed();
	}

	private void givenCreatingNewUser() {
		whenRequestNewUser();
	}

	private void whenRequestNewUser() {
		requestNewUser();
	}

	private void requestNewUser() {
		window.label(getSomeoneElseLabelMatcher()).click();
	}

	private void whenCancel() {
		window.button(getBackButtonMatcher()).click();
	}

	private JButtonMatcher getBackButtonMatcher() {
		return JButtonMatcher.withText(getBackText());
	}

	private JLabelMatcher getSomeoneElseLabelMatcher() {
		return JLabelMatcher.withText(getImSomeoneNewText());
	}

	private String getImSomeoneNewText() {
		return getUserPhrases().getImSomeoneNew();
	}

	private String getBackText() {
		return getComponentPhrases().getBack();
	}

	private ComponentPhrases getComponentPhrases() {
		return PhraseBook.getPhrases(ComponentPhrases.class);
	}

	private UserPhrases getUserPhrases() {
		return PhraseBook.getPhrases(UserPhrases.class);
	}

	private void thenNewUserComponentDisplayed() {
		assertNotNull(findComponentWithType(NewUserComponent.class));
	}

	private void thenMultipleUserComponentDisplayed() {
		assertNotNull(findComponentWithType(MultipleUserComponent.class));
	}

	private <T extends Component> Component findComponentWithType(Class<T> type) {
		return window.robot.finder().find(new TypeMatcher<T>(type));
	}

	private class TypeMatcher<T extends Component> extends GenericTypeMatcher<T> {

		private boolean isFirst = true;

		public TypeMatcher(Class<T> type) {
			super(type);
		}

		protected boolean isMatching(T obj) {
			if (isFirst) {
				isFirst = false;
				return true;
			}
			return false;
		}
	}
}

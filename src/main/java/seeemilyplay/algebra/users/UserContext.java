package seeemilyplay.algebra.users;

import javax.swing.JComponent;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.algebra.users.text.UserPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * Setups and stores user models and components.
 */
public final class UserContext {

	private final UserDAO userDao = new UserDAO();
	private final UserModel userModel = new UserModel(userDao);
	private final LogInModel logInModel = new LogInModel(userModel);


	public UserContext() {
		super();
		initPhraseBook();
	}

	public static void initPhraseBook() {
		PhraseBook.registerPhrases(UserPhrases.class, new UserPhrasesFactory());
	}

	public LogInModel getLogInModel() {
		return logInModel;
	}

	public JComponent createLoggingInComponent() {
		LoggingInModel loggingInModel = createLoggingInModel();
		return new LoggingInComponent(loggingInModel);
	}

	private LoggingInModel createLoggingInModel() {
		return new LoggingInModel(userModel, logInModel);
	}
}

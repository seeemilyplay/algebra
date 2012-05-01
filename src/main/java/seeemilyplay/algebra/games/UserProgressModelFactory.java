package seeemilyplay.algebra.games;

import seeemilyplay.algebra.progress.ProgressContext;
import seeemilyplay.algebra.progress.ProgressModel;
import seeemilyplay.algebra.users.LogInModel;
import seeemilyplay.algebra.users.UserId;

final class UserProgressModelFactory {

	private final LogInModel logInModel;
	private final ProgressContext progressContext;

	public UserProgressModelFactory(
			LogInModel logInModel,
			ProgressContext progressContext) {
		this.logInModel = logInModel;
		this.progressContext = progressContext;
	}

	public ProgressModel createUserProgressModel() {
		String user = getUser();
		return progressContext.getUserProgressModel(user);
	}

	private String getUser() {
		checkLoggedIn();
		return getLoggedInUser().getName();
	}

	private void checkLoggedIn() {
		if (!isLoggedIn()) {
			throw new IllegalStateException();
		}
	}

	private boolean isLoggedIn() {
		return getLogInModel().isLoggedIn();
	}

	private UserId getLoggedInUser() {
		return getLogInModel().getLoggedInUser();
	}

	private LogInModel getLogInModel() {
		return logInModel;
	}
}

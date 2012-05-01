package seeemilyplay.algebra.users;

import seeemilyplay.core.swing.SwingModel;

/**
 * Keeps a track of who is logged in.
 */
public final class LogInModel extends SwingModel {

	private final UserModel userModel;

	private User loggedInUser;


	LogInModel(UserModel userModel) {
		this.userModel = userModel;
	}

	void login(User user) {
		updateLastLoginTime(user);
		loggedInUser = user;
		fireChange();
	}

	public void logout() {
		loggedInUser = null;
		fireChange();
	}

	public boolean isLoggedIn() {
		return (loggedInUser!=null);
	}

	public UserId getLoggedInUser() {
		if (loggedInUser==null) {
			return null;
		}
		return loggedInUser.getUserId();
	}

	private void updateLastLoginTime(User user) {
		userModel.updateLastLoginTime(user, getNow());
	}

	private long getNow() {
		return System.currentTimeMillis();
	}
}
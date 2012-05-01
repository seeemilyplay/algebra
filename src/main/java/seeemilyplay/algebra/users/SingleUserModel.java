package seeemilyplay.algebra.users;

import seeemilyplay.core.swing.SwingModel;

/**
 * Controls logging in when there
 * is just a single existing user.
 */
final class SingleUserModel extends SwingModel {

	private final UserModel userModel;
	private final LogInModel logInModel;

	public SingleUserModel(
			UserModel userModel,
			LogInModel logInModel) {
		this.userModel = userModel;
		this.logInModel = logInModel;
	}

	public String getSingleUserName() {
		User singleUser = getSingleUser();
		return getName(singleUser);
	}

	private String getName(User user) {
		UserId userId = user.getUserId();
		return userId.getName();
	}

	private User getSingleUser() {
		return userModel.getUsers().get(0);
	}

	public void loginAsSingleUser() {
		User singleUser = getSingleUser();
		logInModel.login(singleUser);
	}

	public void createNewUser() {
		fireChange();
	}
}

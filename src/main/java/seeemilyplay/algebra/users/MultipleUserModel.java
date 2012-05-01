package seeemilyplay.algebra.users;

import java.util.List;

import seeemilyplay.core.swing.SwingModel;

/**
 * Manages logging in when there are multiple
 * users already available.
 */
final class MultipleUserModel extends SwingModel {

	private final UserModel userModel;
	private final LogInModel logInModel;

	public MultipleUserModel(
			UserModel userModel,
			LogInModel logInModel) {
		this.userModel = userModel;
		this.logInModel = logInModel;
	}

	public List<User> getUsers() {
		return userModel.getUsers();
	}

	public void loginAs(User user) {
		logInModel.login(user);
	}

	public void createNewUser() {
		fireChange();
	}
}

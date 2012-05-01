package seeemilyplay.algebra.users;

import java.util.List;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

/**
 * Backs the <code>LoggingInComponent</code> by
 * tracking the type of login required.
 */
final class LoggingInModel extends SwingModel {

	private final UserModel userModel;
	private final LogInModel logInModel;

	private boolean isCreatingNewUser;

	private Listener newUserListener;


	public LoggingInModel(
			UserModel userModel,
			LogInModel logInModel) {
		this.userModel = userModel;
		this.logInModel = logInModel;

		initNewUserListener();
	}

	private void initNewUserListener() {
		newUserListener = new Listener() {
			public void onChange() {
				createNewUser();
			}
		};
	}

	private void createNewUser() {
		isCreatingNewUser = true;
		fireChange();
	}

	public void cancelUserCreation() throws Error {
		isCreatingNewUser = false;
		fireChange();
	}

	public boolean isCreatingAdditionalUser() {
		return (isCreatingNewUser
				&& !isCreatingFirstUser());
	}

	public boolean isCreatingFirstUser() {
		return getUserCount()==0;
	}

	public boolean isSelectingFromSingleUser() {
		return getUserCount()==1;
	}

	private int getUserCount() {
		List<User> users = userModel.getUsers();
		return users.size();
	}

	public NewUserModel createNewUserModel() {
		return new NewUserModel(userModel, logInModel);
	}

	public SingleUserModel createSingleUserModel() {
		SingleUserModel singleUserModel =
			new SingleUserModel(userModel, logInModel);
		installNewUserListener(singleUserModel);
		return singleUserModel;
	}

	public MultipleUserModel createMultipleUserModel() {
		MultipleUserModel multipleUserModel =
			new MultipleUserModel(userModel, logInModel);
		installNewUserListener(multipleUserModel);
		return multipleUserModel;
	}

	private void installNewUserListener(SwingModel model) {
		model.addListener(newUserListener);
	}
}

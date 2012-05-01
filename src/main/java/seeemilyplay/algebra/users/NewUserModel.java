package seeemilyplay.algebra.users;

import seeemilyplay.algebra.bulletin.BulletinBoard;
import seeemilyplay.algebra.bulletin.Notice;
import seeemilyplay.algebra.bulletin.notices.NewUserNotice;
import seeemilyplay.core.swing.SwingModel;

/**
 * Controls logging in as a new user.
 */
final class NewUserModel extends SwingModel {

	private final UserModel userModel;
	private final LogInModel logInModel;

	private String suggestedName;


	public NewUserModel(
			UserModel userModel,
			LogInModel logInModel) {
		this.userModel = userModel;
		this.logInModel = logInModel;
	}

	public void setSuggestedName(String suggestedName) {
		this.suggestedName = trim(suggestedName);
		fireChange();
	}

	private String trim(String suggestedName) {
		return (suggestedName!=null) ? suggestedName.trim() : null;
	}

	public boolean isValidSuggestion() {
		return isSuggestionMade() && !isExistingSuggestion();
	}

	public boolean isSuggestionMade() {
		return (suggestedName!=null && suggestedName.length()>0);
	}

	public boolean isExistingSuggestion() {
		return (suggestedName!=null && userModel.isExistingName(suggestedName));
	}

	private void checkValidSuggestion() {
		if (!isValidSuggestion()) {
			throw new Error();
		}
	}

	public void saveNewUserAndLogin() throws Error {
		checkValidSuggestion();
		User user = userModel.saveNewUser(suggestedName);
		logInModel.login(user);
		postNewUserNotice(user);
	}
	
	private void postNewUserNotice(User user) {
		Notice notice = createNewUserNotice(user);
		getBulletinBoard().post(notice);
	}
	
	private Notice createNewUserNotice(User user) {
		String username = getName(user);
		return new NewUserNotice(username);
	}
	
	private String getName(User user) {
		UserId userId = getUserId(user);
		return userId.getName();
	}
	
	private UserId getUserId(User user) {
		return user.getUserId();
	}
	
	private BulletinBoard getBulletinBoard() {
		return BulletinBoard.getInstance();
	}
}

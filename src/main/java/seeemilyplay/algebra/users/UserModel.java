package seeemilyplay.algebra.users;

import java.util.List;

import seeemilyplay.core.swing.SwingModel;

/**
 * A model of all the users.
 */
final class UserModel extends SwingModel {

	private final UserDAO userDao;

	private volatile Users users;

	UserModel(UserDAO userDao) {
		this.userDao = userDao;

		loadFromDAO();
	}

	private void loadFromDAO() {
		createUsers();
	}

	private void createUsers() {
		users = new Users(userDao.loadUsers());
	}

	public List<User> getUsers() {
		return users.getUsers();
	}

	public boolean isExistingName(String name) {
		return users.isExistingName(name);
	}

	public User saveNewUser(String name) {
		validateNewUser(name);
		return createAndSaveUser(name);
	}

	private User createAndSaveUser(String name) {
		User user = createNewUser(name);
		saveUser(user);
		return user;
	}

	private void saveUser(User user) {
		saveLocally(user);
		saveToDAO(user);
	}

	private void validateNewUser(String name) {
		if (isExistingName(name)) {
			throw new IllegalStateException();
		}
	}

	private User createNewUser(String name) {
		UserId userId = createNewUserId(name);
		Profile profile = createNewUserProfile();
		User user = new User();
		user.setUserId(userId);
		user.setProfile(profile);
		return user;
	}

	private UserId createNewUserId(String name) {
		UserId userId = new UserId();
		userId.setId(users.getNextId());
		userId.setName(name);
		return userId;
	}

	private Profile createNewUserProfile() {
		return new Profile();
	}

	private void saveLocally(User user) {
		users.save(user);
		fireChange();
	}

	private void saveToDAO(User user) {
		userDao.saveUser(user);
	}

	public void updateLastLoginTime(User user, long lastLoginTime) {
		Profile profile = getProfile(user);
		setLoginTime(profile, lastLoginTime);
		fireChange();
		updateWithDAO(profile);
	}

	private Profile getProfile(User user) {
		return user.getProfile();
	}

	private void setLoginTime(Profile profile, long lastLoginTime) {
		profile.setLastLoginTime(lastLoginTime);
	}

	private void updateWithDAO(Profile profile) {
		userDao.saveProfile(profile);
	}
}

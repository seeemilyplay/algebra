package seeemilyplay.algebra.users;

import hu.netmind.persistence.Store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seeemilyplay.core.db.Database;

/**
 * Deals with user persistence.
 */
final class UserDAO {

	public void saveUser(User user) {
		Store store = getStore();
		store.save(user);
	}

	public void saveProfile(Profile profile) {
		Store store = getStore();
		store.save(profile);
	}

	public List<User> loadUsers() {
		Store store = getStore();
		return cast(store.find("find user"), User.class);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> cast(List<?> uncastList, Class<T> type) {
		List<T> checkedList =
			Collections.checkedList(new ArrayList<T>(), type);
		checkedList.addAll((List<T>)uncastList);
		return Collections.unmodifiableList(checkedList);
	}

	private Store getStore() {
		return Database.getStore();
	}
}

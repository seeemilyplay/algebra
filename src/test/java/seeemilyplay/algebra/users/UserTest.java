package seeemilyplay.algebra.users;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;
	private Object compareObject;

	protected void setUp() throws Exception {
		user = null;
		compareObject = null;
	}

	public void testUsersEqualIfIdsAreEqual() {

		givenUser(1, "a");

		whenComparedTo(1, "a");

		thenTheyAreEqual();
	}

	public void testUsersDontEqualNull() {

		givenUser(1, "a");

		whenComparedToNull();

		thenTheyAreNotEqual();
	}

	public void testUsersDontEqualOtherObjects() {

		givenUser(1, "a");

		whenComparedTo("not a user");

		thenTheyAreNotEqual();
	}

	public void testUsersDontEqualsUsersWithDifferentIds() {

		givenUser(1, "a");

		whenComparedTo(2, "b");

		thenTheyAreNotEqual();
	}

	public void testUsersWithNullIdsEqualsUsersWithNullIds() {

		givenUserWithNullId();

		whenCompareToUserWithNullId();

		thenTheyAreEqual();
	}

	public void testUsersWithNullIdsDontEqualsUsersWithIds() {

		givenUserWithNullId();

		whenComparedTo(1, "a");

		thenTheyAreNotEqual();
	}

	private void givenUserWithNullId() {
		user = createUser();
	}

	private void givenUser(long id, String name) {
		user = createUser(id, name);
	}

	private void whenComparedTo(long id, String name) {
		compareObject = createUser(id, name);
	}

	private void whenCompareToUserWithNullId() {
		compareObject = createUser();
	}

	private void whenComparedToNull() {
		compareObject = null;
	}

	private void whenComparedTo(Object object) {
		compareObject = object;
	}

	private User createUser(long id, String name) {
		UserId userId = new UserId(id, name);
		Profile profile = new Profile();
		User user = new User();
		user.setUserId(userId);
		user.setProfile(profile);
		return user;
	}

	private User createUser() {
		Profile profile = new Profile();
		User user = new User();
		user.setProfile(profile);
		return user;
	}

	private void thenTheyAreEqual() {
		assertEquals(user.hashCode(), compareObject.hashCode());
		assertTrue(user.equals(compareObject));
	}

	private void thenTheyAreNotEqual() {
		assertFalse(user.equals(compareObject));
	}
}

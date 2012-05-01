package seeemilyplay.algebra.users;

import junit.framework.TestCase;

public class UserIdTest extends TestCase {

	private UserId userId;
	private Object compareObject;

	protected void setUp() throws Exception {
		userId = null;
		compareObject = null;
	}

	public void testUserIdsDontEqualNull() {

		givenUserId(1, "a");

		whenComparedToNull();

		thenTheyAreNotEqual();
	}

	public void testUserIdsDontEqualOtherObjects() {

		givenUserId(1, "a");

		whenComparedTo("not a user id");

		thenTheyAreNotEqual();
	}

	public void testUserIdsAreEqualWhenHaveSaveIdAndName() {

		givenUserId(1, "a");

		whenComparedTo(1, "a");

		thenTheyAreEqual();
	}

	public void testUserIdsDontEqualUserIdsWithDifferentNames() {

		givenUserId(1, "a");

		whenComparedTo(1, "b");

		thenTheyAreNotEqual();
	}

	public void testUserIdsDontEqualUserIdsWithDifferentIds() {

		givenUserId(1, "a");

		whenComparedTo(2, "a");

		thenTheyAreNotEqual();
	}

	private void givenUserId(long id, String name) {
		userId = createUserId(id, name);
	}

	private void whenComparedTo(long id, String name) {
		compareObject = createUserId(id, name);
	}

	private void whenComparedToNull() {
		compareObject = null;
	}

	private void whenComparedTo(Object object) {
		compareObject = object;
	}

	private UserId createUserId(long id, String name) {
		return new UserId(id, name);
	}

	private void thenTheyAreEqual() {
		assertEquals(userId.hashCode(), compareObject.hashCode());
		assertTrue(userId.equals(compareObject));
	}

	private void thenTheyAreNotEqual() {
		assertFalse(userId.equals(compareObject));
	}
}

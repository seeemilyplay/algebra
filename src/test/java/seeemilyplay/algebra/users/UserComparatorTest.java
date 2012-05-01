package seeemilyplay.algebra.users;

import junit.framework.TestCase;

public class UserComparatorTest extends TestCase {

	private User user;
	private User compareUser;

	protected void setUp() throws Exception {
		user = null;
		compareUser = null;
	}

	public void testsUsersWithSameIdAndLoginTimeAreSame() {

		givenUser(1, "a", 200);

		whenComparedToUser(1, "b", 200);

		thenSame();
	}

	public void testUsersWithMoreRecentLoginsAreLesser() {

		givenUser(1, "a", 200);

		whenComparedToUser(1, "b", 100);

		thenLesser();
	}

	public void testUsersWithLessRecentLoginsAreGreater() {

		givenUser(1, "a", 100);

		whenComparedToUser(1, "b", 200);

		thenGreater();
	}

	public void testUsersWithHigherIdsAreLesser() {

		givenUser(2, "a", 200);

		whenComparedToUser(1, "b", 200);

		thenLesser();
	}

	public void testUsersWithLowerIdsAreGreater() {

		givenUser(1, "a", 200);

		whenComparedToUser(2, "b", 200);

		thenGreater();
	}

	private void givenUser(long id, String name, long lastLoginTime) {
		user = createUser(id, name, lastLoginTime);
	}

	private void whenComparedToUser(long id, String name, long lastLoginTime) {
		compareUser = createUser(id, name, lastLoginTime);
	}

	private User createUser(long id, String name, long lastLoginTime) {
		UserId userId = new UserId(id, name);
		Profile profile = new Profile();
		profile.setLastLoginTime(lastLoginTime);
		User user = new User();
		user.setUserId(userId);
		user.setProfile(profile);
		return user;
	}

	private void thenGreater() {
		assertTrue(compare()>0);
	}

	private void thenLesser() {
		assertTrue(compare()<0);
	}

	private void thenSame() {
		assertEquals(0, compare());
	}

	private int compare() {
		UserComparator comparator = new UserComparator();
		return comparator.compare(user, compareUser);
	}
}

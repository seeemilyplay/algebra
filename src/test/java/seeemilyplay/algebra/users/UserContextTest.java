package seeemilyplay.algebra.users;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import junit.framework.TestCase;

import seeemilyplay.core.db.Database;

public class UserContextTest extends TestCase {

	private UserContext userContext;

	protected void setUp() throws Exception {
		super.setUp();
		userContext = new UserContext();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Database.shutdown();
	}

	public void testProvidesALoginModel() {
		thenHasLoginModel();
	}

	public void testCreatesLoggingInComponent() {

		thenCreatesLoggingInComponent();
	}

	private void thenHasLoginModel() {
		assertNotNull(userContext.getLogInModel());
	}

	private void thenCreatesLoggingInComponent() {
		final JComponent[] loggingInComponent = new JComponent[1];
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					loggingInComponent[0] =
						userContext.createLoggingInComponent();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		assertNotNull(loggingInComponent[0]);
	}
}

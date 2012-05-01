package seeemilyplay.core.listeners;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;


public class ListenerManagersTest extends TestCase {

	private ListenerManager listenerManager;

	protected void setUp() throws Exception {
		listenerManager = null;
	}

	public void testPrivateConstructorDoesntThrowException() {

		whenPrivateConstructorCalled();

		thenNoExceptionsAreThrown();
	}

	public void testCreatingWeakRefSwingListenerManager() {

		whenWeakRefSwingListenerManagerCreated();

		thenAListenerManagerIsMade();
	}

	private void whenPrivateConstructorCalled() {
		try {
			Class<?> cls = ListenerManagers.class;
		    Constructor<?> constructor = cls.getDeclaredConstructors()[0];
		    constructor.setAccessible(true);
		    constructor.newInstance((Object[])null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void whenWeakRefSwingListenerManagerCreated() {
		listenerManager = ListenerManagers.createWeakRefSwingListenerManager();
	}

	private void thenNoExceptionsAreThrown() {
		return;
	}

	private void thenAListenerManagerIsMade() {
		assertNotNull(listenerManager);
	}
}

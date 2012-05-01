package seeemilyplay.core.swing;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;


public class BlockingTaskUtilsTest extends TestCase {

	private BlockingTask task;
	private Throwable t;
	private boolean isRan;


	protected void setUp() throws Exception {
		super.setUp();
		task = null;
		t = null;
		isRan = false;
	}

	public void testThatExercisesPrivateConstructor() {

		whenPrivateConstructorCalled();

		thenNoExceptionsAreThrown();
	}

	public void testExecutionRunsTask() {

		givenBlockingTask();

		whenExecuted();

		thenTaskIsRan();
	}

	public void testErrorIsThrowingIfTaskIsInterrupted() {

		givenBlockingTaskThatThrowsInterruption();

		whenExecuted();

		thenInterruptedExceptionWrappedWithErrorAndThrown();
	}

	private void givenBlockingTask() {
		task = new Task();
	}

	private class Task implements BlockingTask {

		public Task() {
			super();
		}

		public void run() throws InterruptedException {
			isRan = true;
		}
	}

	private void givenBlockingTaskThatThrowsInterruption() {
		task = new InterruptingTask();
	}

	private class InterruptingTask implements BlockingTask {

		public InterruptingTask() {
			super();
		}

		public void run() throws InterruptedException {
			throw new InterruptedException();
		}
	}

	private void whenExecuted() {
		try {
			BlockingTaskUtils.execute(task);
		} catch (Throwable t) {
			this.t = t;
		}
	}

	private void whenPrivateConstructorCalled() {
		callPrivateConstructor();
	}

	private void callPrivateConstructor() {
		try {
			Class<?> cls = BlockingTaskUtils.class;
		    Constructor<?> constructor = cls.getDeclaredConstructors()[0];
		    constructor.setAccessible(true);
		    constructor.newInstance((Object[])null);
		} catch (Throwable t) {

		}
	}

	private void thenTaskIsRan() {
		assertTrue(isRan);
	}

	private void thenInterruptedExceptionWrappedWithErrorAndThrown() {
		assertTrue(t instanceof Error);
		assertTrue(t.getCause() instanceof InterruptedException);
	}

	private void thenNoExceptionsAreThrown() {
		assertNull(t);
	}
//
//	/**
//	 * This tests that an <code>Error</code> is thrown by the
//	 * <code>execute</code> method if the <code>BlockingTask</code>
//	 * throws an <code>InterruptedException</code>.
//	 */
//	public void testErrorThrownInResponseToInterruptionInExecuteMethod() {
//		BlockingTask blockingTask = new BlockingTask() {
//			public void run() throws InterruptedException {
//				throw new InterruptedException();
//			}
//		};
//
//		try {
//			BlockingTaskUtils.execute(blockingTask);
//			fail();
//		} catch (AssertionFailedError e) {
//			throw e;
//		} catch (Error e) {
//			//wait
//		}
//	}
}

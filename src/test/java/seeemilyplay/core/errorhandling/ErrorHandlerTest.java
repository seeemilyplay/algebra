package seeemilyplay.core.errorhandling;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;

import junit.framework.TestCase;


public class ErrorHandlerTest extends TestCase {

	private Throwable originalThrowable;
	private Throwable handlerThrowable;


	protected void setUp() throws Exception {
		super.setUp();
		originalThrowable = null;
		handlerThrowable = null;
	}

	public void testRunningTaskThatThrowsARuntimeExceptionThrowsItAgain() {

		whenRunTaskThatThrows(new NullPointerException());

		thenHandlerThrowsSameException();
	}

	public void testRunningTaskThatThrowsAnErrorThrowsItAgain() {

		whenRunTaskThatThrows(new Error());

		thenHandlerThrowsSameException();
	}

	public void testRunningTaskThatThrowsACheckedExceptionThrowsItAgainAsCause() {

		whenRunTaskThatThrows(new Exception());

		thenHandlerThrowsSameExceptionAsCause();
	}

	public void testRunningTaskThatIsSuccessfulDoesntThrowException() {

		whenRunTaskThatDoesntThrowException();

		thenHandlerDoesntThrowException();
	}

	private void whenRunTaskThatThrows(Exception e) {
		ExceptionThrowingTask task = createTaskThatThrows(e);
		runTaskWithThrowable(e, task);
	}

	private void whenRunTaskThatThrows(Error e) {
		ExceptionThrowingTask task = createTaskThatThrows(e);
		runTaskWithThrowable(e, task);
	}

	private void runTaskWithThrowable(Throwable t, ExceptionThrowingTask task) {
		originalThrowable = t;
		runTask(task);
	}

	private ExceptionThrowingTask createTaskThatThrows(Exception e) {
		return new ExceptionThrowerTask(e);
	}

	private static class ExceptionThrowerTask implements ExceptionThrowingTask {

		private final Exception e;

		public ExceptionThrowerTask(Exception e) {
			this.e = e;
		}

		public void run() throws Exception {
			throw e;
		}
	}

	private ExceptionThrowingTask createTaskThatThrows(Error e) {
		return new ErrorThrowerTask(e);
	}

	private static class ErrorThrowerTask implements ExceptionThrowingTask {

		private final Error e;

		public ErrorThrowerTask(Error e) {
			this.e = e;
		}

		public void run() {
			throw e;
		}
	}

	private void whenRunTaskThatDoesntThrowException() {
		ExceptionThrowingTask task = createSuccessfulTask();
		runTask(task);
	}

	private ExceptionThrowingTask createSuccessfulTask() {
		return new SuccessfulTask();
	}

	private static class SuccessfulTask implements ExceptionThrowingTask {

		public void run() {
			return;
		}
	}

	private void runTask(ExceptionThrowingTask task) {
		try {
			ErrorHandler.getInstance().run(task);
		} catch (Throwable t) {
			handlerThrowable = t;
		}
	}

	private void thenHandlerThrowsSameException() {
		assertEquals(originalThrowable, handlerThrowable);
	}

	private void thenHandlerThrowsSameExceptionAsCause() {
		assertEquals(originalThrowable, handlerThrowable.getCause());
	}

	private void thenHandlerDoesntThrowException() {
		assertNull(handlerThrowable);
	}
}

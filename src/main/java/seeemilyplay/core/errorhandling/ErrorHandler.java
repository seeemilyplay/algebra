package seeemilyplay.core.errorhandling;

/**
 * A singleton used for handling errors and exceptions gracefully.
 */
public final class ErrorHandler {

	private static final ErrorHandler instance = new ErrorHandler();

	private ErrorHandler() {
		super();
	}

	public static ErrorHandler getInstance() {
		return instance;
	}

	public void run(ExceptionThrowingTask task) {
		try {
			task.run();
		} catch (RuntimeException e) {
			throw (RuntimeException)e;
		} catch (Error e) {
			throw (Error)e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

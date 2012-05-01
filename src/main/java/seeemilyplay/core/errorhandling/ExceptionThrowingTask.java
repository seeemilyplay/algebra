package seeemilyplay.core.errorhandling;

/**
 * A task that may throw an <code>Exception</code>.
 * This can be used directly with an <code>ErrorHandler</code>.
 *
 * @see ErrorHandler
 */
public interface ExceptionThrowingTask {

	public void run() throws Exception;
}

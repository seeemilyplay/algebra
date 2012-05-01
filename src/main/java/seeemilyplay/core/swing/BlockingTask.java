package seeemilyplay.core.swing;

/**
 * A blocking task is a task that could potentially
 * be blocking the thread's execution and could possibly
 * throw an <code>InterruptedException</code>.
 *
 * @see BlockingTaskUtils
 */
public interface BlockingTask {

	public void run() throws InterruptedException;
}

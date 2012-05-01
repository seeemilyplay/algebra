package seeemilyplay.core.swing;

/**
 * A utility class that has methods
 * designed to help with running <code>BlockingTasks</code>
 * in a Swing environment.
 */
public final class BlockingTaskUtils {

	private BlockingTaskUtils() {
		super();
	}

	public static void execute(BlockingTask blockingTask) throws Error {
		try {
			blockingTask.run();
		} catch (InterruptedException e) {
			throw new Error(e);
		}
	}
}

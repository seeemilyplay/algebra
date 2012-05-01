package seeemilyplay.core.swing;

import java.util.concurrent.Executor;

import javax.swing.SwingUtilities;

/**
 * A implementation of an <code>Executor</code> that runs tasks using
 * <code>SwingUtilities.invokeLater</code>.
 */
public final class SwingExecutor implements Executor {

	public SwingExecutor() {
		super();
	}

	public void execute(final Runnable command) {
		SwingUtilities.invokeLater(command);
	}
}

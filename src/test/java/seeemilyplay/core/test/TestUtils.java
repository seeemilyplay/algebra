package seeemilyplay.core.test;

import java.util.concurrent.Executor;

import seeemilyplay.core.listeners.StrongRefListenerManager;
import seeemilyplay.core.swing.MultiThreadedSwingModel;
import seeemilyplay.core.swing.SwingModel;

public final class TestUtils {

	private TestUtils() {
		super();
	}

	public static void prepareModel(SwingModel model) {
		model.setListenerManager(new StrongRefListenerManager());
	}

	public static void prepareModel(MultiThreadedSwingModel model) {
		model.setListenerManager(new StrongRefListenerManager());
		model.setExecutor(new SameThreadExecutor());
		model.setSwingExecutor(new SameThreadExecutor());
	}

	private static class SameThreadExecutor implements Executor {

		public SameThreadExecutor() {
			super();
		}

		public void execute(Runnable task) {
			task.run();
		}
	}
}

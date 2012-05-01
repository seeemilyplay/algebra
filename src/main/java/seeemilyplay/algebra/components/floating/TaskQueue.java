package seeemilyplay.algebra.components.floating;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;
import seeemilyplay.core.swing.MultiThreadedSwingModel;

final class TaskQueue extends MultiThreadedSwingModel {

	private final BlockingQueue<Runnable> tasks =
		new LinkedBlockingQueue<Runnable>();


	public TaskQueue() {
		super();

		scheduleProcessing();
	}

	public void queue(Runnable task) {
		tasks.add(task);
	}

	private void scheduleProcessing() {
		executeOffSwingThread(new Runnable() {
			public void run() {
				processTasks();
			}
		});
	}

	private void processTasks() {
		while (true) {
			processNextTask();
		}
	}

	private void processNextTask() {
		Runnable nextTask = nextTask();
		nextTask.run();
	}

	private Runnable nextTask() {
		final Runnable[] task = new Runnable[1];
		ErrorHandler.getInstance().run(new ExceptionThrowingTask() {
			public void run() throws InterruptedException {
				task[0] = tasks.take();
			}
		});
		return task[0];
	}
}

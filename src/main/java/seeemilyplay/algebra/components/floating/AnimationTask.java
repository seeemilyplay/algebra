package seeemilyplay.algebra.components.floating;

import java.util.concurrent.CountDownLatch;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;
import seeemilyplay.core.swing.MultiThreadedSwingModel;

final class AnimationTask extends MultiThreadedSwingModel implements Runnable {

	private final Animator animator;
	private final CountDownLatch latch;

	public AnimationTask(Animator animator) {
		this.animator = animator;
		this.latch = new CountDownLatch(1);

		installEndLatch();
	}

	public void run() {
		startAnimator();
		awaitAnimationEnd();
	}

	private void installEndLatch() {
		animator.addTarget(new TimingTargetAdapter() {
			public void end() {
				latch.countDown();
			}
		});
	}

	private void startAnimator() {
		executeOnSwingThread(new Runnable() {
			public void run() {
				animator.start();
			}
		});
	}

	private void awaitAnimationEnd() {
		ErrorHandler.getInstance().run(new ExceptionThrowingTask() {
			public void run() throws InterruptedException {
				latch.await();
			}
		});
	}
}

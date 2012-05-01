package seeemilyplay.algebra.goals;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.SwingModel;

public final class GoalBarModel extends SwingModel {
	
	private static final double TIME_PER_PERCENT = 5000;
	private static final String PROGRESS = "progress";
	
	private final GoalModel goalModel;
	
	private double progress;
	
	private Listener goalModelListener;
	
	private Animator animator;
	
	private double startProgress;
	private double targetProgress;
	

	public GoalBarModel(GoalModel goalModel) {
		this.goalModel = goalModel;
		
		initProgress();
		initGoalModelListener();
	}
	
	private void initProgress() {
		progress = getModelProgress();
		startProgress = getModelProgress();
		targetProgress = getModelProgress();
	}
	
	private void initGoalModelListener() {
		goalModelListener = new Listener() {
			public void onChange() {
				animateProgressChangeIfRequired();
			}
		};
		goalModel.addListener(goalModelListener);
		animateProgressChangeIfRequired();
	}
	
	public double getProgress() {
		return progress;
	}
	
	public void setProgress(double progress) {
		this.progress = progress;
		fireChange();
	}
	
	private void animateProgressChangeIfRequired() {
		if (isAnimationRequired()) {
			animateProgressChange();
		}
	}
	
	private boolean isAnimationRequired() {
		return targetProgress!=getModelProgress();
	}
	
	private void animateProgressChange() {
		cancelAnimationIfRequired();
		createAnimation();
	}
	
	private void cancelAnimationIfRequired() {
		if (isAnimationRunning()) {
			cancelAnimation();
		}
	}
	
	private boolean isAnimationRunning() {
		return (animator!=null && animator.isRunning());
	}
	
	private void cancelAnimation() {
		animator.cancel();
	}
	
	private void createAnimation() {
		initAnimation();
		animator = PropertySetter.createAnimator(
				getDuration(), 
				this, 
				PROGRESS,
				startProgress,
				targetProgress);
		animator.start();
	}
	
	private void initAnimation() {
		startProgress = progress;
		targetProgress = getModelProgress();
	}
	
	private int getDuration() {
		return (int)(getProgressChange() * TIME_PER_PERCENT);
	}
	
	private double getProgressChange() {
		return Math.abs(targetProgress - startProgress);
	}
	
	private double getModelProgress() {
		return goalModel.getProgress();
	}
}

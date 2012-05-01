package seeemilyplay.algebra.components.floating;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

final class BackgroundOpacityChanger extends TimingTargetAdapter {

	private static final double ACCURACY = 0.001;
	private static final int FADE_DURATION = 750;

	private static final String BACKGROUND_OPACITY = "backgroundOpacity";

	private final FloatingPaneModel floatingPaneModel;
	private final float backgroundOpacity;

	private Animator animator;

	private TimingTarget opacity;

	public BackgroundOpacityChanger(
			FloatingPaneModel floatingPaneModel,
			float backgroundOpacity) {
		this.floatingPaneModel = floatingPaneModel;
		this.backgroundOpacity = backgroundOpacity;

		init();
	}

	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

	private void init() {
		initOpacity();
	}

	private void initOpacity() {
		opacity = new PropertySetter(
					floatingPaneModel,
					BACKGROUND_OPACITY,
					getBackgroundOpacity(),
					backgroundOpacity);
	}

	private float getBackgroundOpacity() {
		return floatingPaneModel.getBackgroundOpacity();
	}

	public void begin() {
		if (isTargetOpacityMet()) {
			cancel();
		}
	}

	private boolean isTargetOpacityMet() {
		double diff = Math.abs(getBackgroundOpacity()-backgroundOpacity);
		return diff<ACCURACY;
	}

	private void cancel() {
		animator.stop();
	}

	public void timingEvent(float fraction) {
		if (isTargetOpacityMet()) {
			return;
		}

		opacity.timingEvent(fraction);
	}

	public int getDuration() {
		return FADE_DURATION;
	}
}

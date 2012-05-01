package seeemilyplay.algebra.components.floating;

import java.awt.Dimension;

import javax.swing.JComponent;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

final class FloatingComponentChanger extends TimingTargetAdapter {

	private static final String FLOAT_OPACITY = "floatOpacity";
	private static final String FLOAT_SIZE = "floatSize";

	private static final int FADE_TIME = 250;
	private static final int RESIZE_TIME = 250;

	private final FloatingPaneModel floatingPaneModel;
	private final String floatingComponentId;
	private final JComponent nakedFloatingComponent;
	private final LocationCalculator locationCalculator;

	private Animator animator;

	private TimingTarget fadeOut;
	private TimingTarget resize;
	private TimingTarget fadeIn;

	private float fraction;

	public FloatingComponentChanger(
			FloatingPaneModel floatingPaneModel,
			String floatingComponentId,
			JComponent nakedFloatingComponent) {
		this.floatingPaneModel = floatingPaneModel;
		this.floatingComponentId = floatingComponentId;
		this.nakedFloatingComponent = nakedFloatingComponent;
		this.locationCalculator = new LocationCalculator(
				floatingPaneModel);

		init();
	}

	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

	private void init() {
		initFadeOut();
		initResize();
		initFadeIn();
	}

	private void initFadeOut() {
		fadeOut = new PropertySetter(
				floatingPaneModel,
				FLOAT_OPACITY,
				getFloatOpacity(),
				FloatingPaneModel.getFullyFaded());
	}

	private float getFloatOpacity() {
		return floatingPaneModel.getFloatOpacity();
	}

	private void initResize() {
		resize = new PropertySetter(
				floatingPaneModel,
				FLOAT_SIZE,
				getFloatSize(),
				getTargetSize());
	}

	private Dimension getFloatSize() {
		return floatingPaneModel.getFloatSize();
	}

	private Dimension getTargetSize() {
		return nakedFloatingComponent.getPreferredSize();
	}

	private void initFadeIn() {
		fadeIn = new PropertySetter(
				floatingPaneModel,
				FLOAT_OPACITY,
				FloatingPaneModel.getFullyFaded(),
				FloatingPaneModel.getFullyVisible());
	}

	public void begin() {
		if (!isComponentUpdateRequired()) {
			cancelAnimation();
		}
	}

	private void cancelAnimation() {
		animator.stop();
	}

	private boolean isFullyVisible() {
		return (floatingPaneModel.getFloatOpacity()
				==FloatingPaneModel.getFullyVisible());
	}

	private boolean isTargetComponentSetup() {
		return (!isComponentUpdateRequired()
				&& isFullyVisible());
	}

	public void timingEvent(float fraction) {
		if (isTargetComponentSetup()) {
			return;
		}

		init(fraction);

		if (isFadingOut()) {
			updateFadeOut();
		} else if (isResizing()) {
			updateResize();
		} else {
			updateComponentIfRequired();
			updateFadeIn();
		}

		updateLocation();
	}

	private void init(float fraction) {
		this.fraction = fraction;
	}

	private boolean isFadingOut() {
		return toDuration(fraction)<=getFadeTime();
	}

	private void updateFadeOut() {
		fadeOut.timingEvent(getFadeOutFraction());
	}

	private float getFadeOutFraction() {
		return toFraction(
				toDuration(fraction),
				getFadeTime());
	}

	private boolean isResizing() {
		return toDuration(fraction)<=(getFadeTime() + getResizeTime());
	}

	private void updateResize() {
		resize.timingEvent(getResizeFraction());
	}

	private float getResizeFraction() {
		return toFraction(
				toDuration(fraction)-getFadeTime(),
				getResizeTime());
	}

	private void updateComponentIfRequired() {
		if (isComponentUpdateRequired()) {
			updateComponent();
		}
	}

	private boolean isComponentUpdateRequired() {
		return !getFloatingComponentId().equals(
				floatingComponentId);
	}

	private String getFloatingComponentId() {
		return floatingPaneModel.getFloatingComponentId();
	}

	private void updateComponent() {
		floatingPaneModel.setNakedFloatingComponent(
				floatingComponentId,
				nakedFloatingComponent);
	}

	private void updateFadeIn() {
		fadeIn.timingEvent(getFadeInFraction());
	}

	private float getFadeInFraction() {
		return toFraction(
				toDuration(fraction) - getFadeTime() - getResizeTime(),
				getFadeTime());
	}

	private void updateLocation() {
		locationCalculator.updateLocation();
	}

	private int toDuration(float fraction) {
		return (int)(fraction * getDuration());
	}

	public int getDuration() {
		return 2 * getFadeTime() + getResizeTime();
	}

	private int getResizeTime() {
		return RESIZE_TIME;
	}

	private int getFadeTime() {
		return FADE_TIME;
	}

	private float toFraction(int duration, int totalDuration) {
		return duration / (float)totalDuration;
	}
}

package seeemilyplay.algebra.components.floating;

import java.awt.Point;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

final class FloatPositionChanger extends TimingTargetAdapter {

	private static final int MOVEMENT_DURATION = 500;

	private static final String FLOAT_LOCATION = "floatLocation";

	private final FloatingPaneModel floatingPaneModel;
	private final FloatPosition floatPosition;
	private final LocationCalculator locationCalculator;

	private Animator animator;

	private TimingTarget location;

	public FloatPositionChanger(
			FloatingPaneModel floatingPaneModel,
			FloatPosition floatPosition) {
		this.floatingPaneModel = floatingPaneModel;
		this.floatPosition = floatPosition;
		this.locationCalculator = new LocationCalculator(
				floatingPaneModel);

		init();
	}

	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

	private void init() {
		initLocation();
	}

	private void initLocation() {
		location = new PropertySetter(
					floatingPaneModel,
					FLOAT_LOCATION,
					getFloatLocation(),
					getTargetLocation());
	}

	private Point getFloatLocation() {
		return floatingPaneModel.getFloatLocation();
	}

	private Point getTargetLocation() {
		return locationCalculator.getLocation(floatPosition);
	}

	public void begin() {
		if (isTargetLocationReached()) {
			cancel();
			return;
		}
		floatingPaneModel.setFloatPosition(floatPosition);
	}

	private boolean isTargetLocationReached() {
		return getFloatLocation().equals(getTargetLocation());
	}

	private void cancel() {
		animator.stop();
	}

	public void timingEvent(float fraction) {
		if (isTargetLocationReached()) {
			return;
		}

		location.timingEvent(fraction);
	}

	public int getDuration() {
		return MOVEMENT_DURATION;
	}
}

package seeemilyplay.algebra.components.floating;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;

import seeemilyplay.core.swing.MultiThreadedSwingModel;

/**
 * Backs the <code>FloatingPane</code>.
 */
public final class FloatingPaneModel extends MultiThreadedSwingModel {

	private static final String INVISIBLE_COMPONENT_ID = "invisibleComponentId";
	private static final JComponent INVISIBLE_COMPONENT = new JPanel(new BorderLayout());
	
	private static final float FULLY_FADED = 0.0f;
	private static final float FULLY_VISIBLE = 1.0f;
	private static final float MOSTLY_FADED = 0.2f;

	private final JComponent backgroundComponent;
	private final FloatingComponent floatingComponent;
	private final LocationCalculator locationCalculator;

	private Float backgroundOpacity;

	private String floatingComponentId;
	private JComponent nakedFloatingComponent;

	private FloatPosition floatPosition;
	private Point floatLocation;
	private Float floatOpacity;
	private Dimension floatSize;

	private TaskQueue taskQueue = new TaskQueue();

	public FloatingPaneModel(JComponent backgroundComponent) {
		this.backgroundComponent = backgroundComponent;
		this.floatingComponent = new FloatingComponent(this);
		this.locationCalculator = new LocationCalculator(this);
		checkReady();
	}

	public static float getFullyFaded() {
		return FULLY_FADED;
	}

	public static float getFullyVisible() {
		return FULLY_VISIBLE;
	}

	public static float getMostlyFaded() {
		return MOSTLY_FADED;
	}

	public JComponent getBackgroundComponent() {
		return backgroundComponent;
	}

	public JComponent getFloatingComponent() {
		return floatingComponent;
	}

	private void checkReady() {
		if (isReady()) {
			updateLocation();
			fireChange();
			return;
		}
		executeOnSwingThread(new Runnable() {
			public void run() {
				checkReady();
			}
		});
	}

	public boolean isReady() {
		return (getWindow()!=null);
	}

	private Window getWindow() {
    	return SwingUtilities.windowForComponent(
    			getBackgroundComponent());
    }

	public void setBackgroundOpacity(float backgroundOpacity) {
		this.backgroundOpacity = backgroundOpacity;
		fireChange();
	}

	public float getBackgroundOpacity() {
		return (backgroundOpacity!=null ? backgroundOpacity : FULLY_FADED);
	}

	public boolean isBackgroundFaded() {
		return (backgroundOpacity!=null 
				&& backgroundOpacity<FULLY_VISIBLE);
	}

	public void setNakedFloatingComponent(
			String floatingComponentId,
			JComponent nakedFloatingComponent) {
		this.floatingComponentId = floatingComponentId;
		this.nakedFloatingComponent = nakedFloatingComponent;
		fireChange();
	}

	public String getFloatingComponentId() {
		return floatingComponentId;
	}

	public JComponent getNakedFloatingComponent() {
		return nakedFloatingComponent;
	}

	public void setFloatPosition(FloatPosition floatPosition) {
		this.floatPosition = floatPosition;
		fireChange();
	}

	public FloatPosition getFloatPosition() {
		return floatPosition;
	}

	public void setFloatLocation(Point floatLocation) {
		this.floatLocation = floatLocation;
		fireChange();
	}

	public Point getFloatLocation() {
		return floatLocation;
	}

	public void setFloatOpacity(float floatOpacity) {
		this.floatOpacity = floatOpacity;
		fireChange();
	}

	public float getFloatOpacity() {
		return (floatOpacity!=null ? floatOpacity : FULLY_FADED);
	}
	
	public boolean isFloatFaded() {
		return (floatOpacity!=null && floatOpacity<FULLY_VISIBLE);
	}

	public void setFloatSize(Dimension floatSize) {
		this.floatSize = floatSize;
		fireChange();
	}

	public Dimension getFloatSize() {
		return floatSize;
	}
	
	public void animateFloatingComponentDisappears() {
		animateFloatingComponentChange(
				INVISIBLE_COMPONENT_ID, INVISIBLE_COMPONENT);
	}

	public void animateFloatingComponentChange(
			String floatingComponentId,
			JComponent nakedFloatingComponent) {
		if (!isInitialisedFloatingComponent()) {
			initialiseFloatingComponent(
					floatingComponentId,
					nakedFloatingComponent);
		} else if (isDifferentFloatingComponent(
				floatingComponentId)){
			queueFloatingComponentAnimation(
					floatingComponentId,
					nakedFloatingComponent);
		}
	}

	private boolean isInitialisedFloatingComponent() {
		return floatingComponentId!=null;
	}

	private void initialiseFloatingComponent(
			String floatingComponentId,
			JComponent nakedFloatingComponent) {
		this.floatingComponentId = floatingComponentId;
		this.nakedFloatingComponent = nakedFloatingComponent;
		this.floatOpacity = FULLY_VISIBLE;
		this.floatSize = nakedFloatingComponent.getPreferredSize();
		updateLocation();
		fireChange();
	}

	private boolean isDifferentFloatingComponent(
			String floatingComponentId) {
		return !this.floatingComponentId.equals(floatingComponentId);
	}

	private void queueFloatingComponentAnimation(
			String floatingComponentId,
			JComponent nakedFloatingComponent) {
		FloatingComponentChanger timingTarget =
			new FloatingComponentChanger(
					this,
					floatingComponentId,
					nakedFloatingComponent);
		Animator animator = new Animator(
				timingTarget.getDuration(),
				timingTarget);
		timingTarget.setAnimator(animator);
		queue(animator);
	}

	public void animateFloatPositionChange(
			FloatPosition floatPosition) {
		if (!isInitialisedFloatPosition()) {
			initialiseFloatPosition(floatPosition);
		} else if (isDifferentFloatPosition(floatPosition)) {
			queueFloatPositionAnimation(floatPosition);
		}
	}

	private boolean isInitialisedFloatPosition() {
		return floatPosition!=null;
	}

	private void initialiseFloatPosition(FloatPosition floatPosition) {
		this.floatPosition = floatPosition;
		updateLocation();
		fireChange();
	}

	private boolean isDifferentFloatPosition(FloatPosition floatPosition) {
		return !this.floatPosition.equals(floatPosition);
	}

	private void queueFloatPositionAnimation(FloatPosition floatPosition) {
		FloatPositionChanger floatPositionChanger =
			new FloatPositionChanger(
					this,
					floatPosition);
		Animator animator = new Animator(
				floatPositionChanger.getDuration(),
				floatPositionChanger);
		floatPositionChanger.setAnimator(animator);
		queue(animator);
	}

	public void animateBackgroundDisabling() {
		animateBackgroundOpacityChange(MOSTLY_FADED);
	}

	public void animateBackgroundEnabling() {
		animateBackgroundOpacityChange(FULLY_VISIBLE);
	}

	private void animateBackgroundOpacityChange(float backgroundOpacity) {
		if (!isInitialisedBackgroundOpacity()) {
			initialiseBackgroundOpacity(backgroundOpacity);
		} else if (isDifferentBackgroundOpacity(backgroundOpacity)) {
			queueBackgroundOpacityAnimation(backgroundOpacity);
		}
	}

	private boolean isInitialisedBackgroundOpacity() {
		return backgroundOpacity!=null;
	}

	private void initialiseBackgroundOpacity(float backgroundOpacity) {
		this.backgroundOpacity = backgroundOpacity;
		fireChange();
	}

	private boolean isDifferentBackgroundOpacity(float backgroundOpacity) {
		return this.backgroundOpacity!=backgroundOpacity;
	}

	private void queueBackgroundOpacityAnimation(float backgroundOpacity) {
		BackgroundOpacityChanger backgroundOpacityChanger =
			new BackgroundOpacityChanger(
					this,
					backgroundOpacity);
		Animator animator = new Animator(
				backgroundOpacityChanger.getDuration(),
				backgroundOpacityChanger);
		backgroundOpacityChanger.setAnimator(animator);
		queue(animator);
	}

	private void queue(Animator animator) {
		AnimationTask animationTask = new AnimationTask(animator);
		taskQueue.queue(animationTask);
	}

	private void updateLocation() {
		locationCalculator.updateLocation();
	}
}

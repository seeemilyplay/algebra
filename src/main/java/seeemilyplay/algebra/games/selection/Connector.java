package seeemilyplay.algebra.games.selection;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;

final class Connector {

	private final JComponent fromComponent;
	private final JComponent toComponent;

	public Connector(
			JComponent fromComponent,
			JComponent toComponent) {
		this.fromComponent = fromComponent;
		this.toComponent = toComponent;
	}

	public boolean isEnabled() {
		return toComponent.isEnabled();
	}

	public boolean isVisible() {
		return (getFromBounds()!=null
				&& getToBounds()!=null);
	}

	public Point getFromLocation() {
		Rectangle fromBounds = getFromBounds();
		return new Point(
				(int)fromBounds.getCenterX(),
				(int)fromBounds.getMaxY());
	}

	public Point getToLocation() {
		Rectangle toBounds = getToBounds();
		return new Point(
				(int)toBounds.getCenterX(),
				(int)toBounds.getMinY());
	}

	Rectangle getFromBounds() {
		return getBoundsRelativeToMapComponent(fromComponent);
	}

	Rectangle getToBounds() {
		return getBoundsRelativeToMapComponent(toComponent);
	}

	private Rectangle getBoundsRelativeToMapComponent(JComponent component) {
		if (!isReady(component)) {
			return null;
		}
		Rectangle bounds = component.getBounds();
		Point mapComponentLocation = getMapComponentLocation(component);
		Point locationOnScreen = component.getLocationOnScreen();
		return new Rectangle(
				locationOnScreen.x - mapComponentLocation.x,
				locationOnScreen.y - mapComponentLocation.y,
				bounds.width,
				bounds.height);
	}

	private boolean isReady(JComponent component) {
		return (component.getBounds()!=null
				&& getMapComponentLocation(component)!=null);
	}

	private Point getMapComponentLocation(JComponent component) {
		Container parent = component.getParent();
		while (parent!=null) {
			if (parent instanceof GamesComponent) {
				return parent.getLocationOnScreen();
			}
			parent = parent.getParent();
		}
		return null;
	}
}

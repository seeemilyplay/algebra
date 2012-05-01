package seeemilyplay.algebra.sequences.crossword;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

final class CenteringLayoutManager implements LayoutManager {

	private Component component;
	private Container parent;

	public CenteringLayoutManager() {
		super();
	}

	public void addLayoutComponent(String name, Component comp) {
		return;
	}

	public void removeLayoutComponent(Component comp) {
		return;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Dimension preferredLayoutSize(Container parent) {
		return parent.getPreferredSize();
	}

	public Dimension minimumLayoutSize(Container parent) {
		return  parent.getMinimumSize();
	}

	public void layoutContainer(Container parent) {
		init(parent);
		if (isComponentSet()) {
			locateComponentInCentre();
		}
	}

	private void init(Container parent) {
		this.parent = parent;
	}

	private boolean isComponentSet() {
		return component!=null;
	}

	private void locateComponentInCentre() {
		Point centreLocation = getCentreLocation();
		setComponentLocation(centreLocation);
	}

	private void setComponentLocation(Point location) {
		Rectangle bounds = new Rectangle(
				location,
				getComponentSize());
		component.setBounds(bounds);
	}

	private Point getCentreLocation() {
    	return createLocation(getCentreY(), getCentreX());
    }

	private Point createLocation(int y, int x) {
    	return new Point(x, y);
    }

	private int getCentreX() {
    	Rectangle parentBounds = getParentBounds();
    	Dimension componentSize = getComponentSize();
    	return (int)(
    			parentBounds.getX()
    			+ (parentBounds.getWidth()/2.0)
    			- (componentSize.getWidth()/2.0));
    }

	private int getCentreY() {
    	Rectangle parentBounds = getParentBounds();
    	Dimension componentSize = getComponentSize();
    	return (int)(
    			parentBounds.getY()
    			+ (parentBounds.getHeight()/2.0)
    			- (componentSize.getHeight()/2.0));
    }

	private Rectangle getParentBounds() {
		return parent.getBounds();
	}

	private Dimension getComponentSize() {
		return component.getPreferredSize();
	}
}

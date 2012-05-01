package seeemilyplay.algebra.components.floating;

import static seeemilyplay.algebra.components.floating.FloatPosition.CENTRE;
import static seeemilyplay.algebra.components.floating.FloatPosition.EAST;
import static seeemilyplay.algebra.components.floating.FloatPosition.NORTH;
import static seeemilyplay.algebra.components.floating.FloatPosition.NORTH_EAST;
import static seeemilyplay.algebra.components.floating.FloatPosition.NORTH_WEST;
import static seeemilyplay.algebra.components.floating.FloatPosition.ON_SCREEN_MID_LINE;
import static seeemilyplay.algebra.components.floating.FloatPosition.SCREEN_CENTRE;
import static seeemilyplay.algebra.components.floating.FloatPosition.SOUTH;
import static seeemilyplay.algebra.components.floating.FloatPosition.SOUTH_EAST;
import static seeemilyplay.algebra.components.floating.FloatPosition.SOUTH_WEST;
import static seeemilyplay.algebra.components.floating.FloatPosition.WEST;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;


final class LocationCalculator {

	private static final int PADDING = 15;

	private final FloatingPaneModel floatingPaneModel;

	private final Map<FloatPosition, Locator> locators =
		new HashMap<FloatPosition, Locator>();


	public LocationCalculator(
			FloatingPaneModel floatingPaneModel) {
		this.floatingPaneModel = floatingPaneModel;

		initLocators();
	}

	public Point getLocation() {
		if (!isReady()) {
			return null;
		}
    	return getLocation(getFloatPosition());
    }

	private boolean isReady() {
		return floatingPaneModel.isReady();
	}

	public Point getLocation(FloatPosition floatPosition) {
		Locator locator = getLocator(floatPosition);
		return new Point(locator.getX(), locator.getY());
	}

	public void updateLocation() {
		Point location = getLocation();
		floatingPaneModel.setFloatLocation(location);
	}

    private Locator getLocator(FloatPosition floatPosition) {
    	return locators.get(floatPosition);
    }

    private FloatPosition getFloatPosition() {
    	return floatingPaneModel.getFloatPosition();
    }

	private void initLocators() {
		initLocator(NORTH, new NorthLocator());
		initLocator(SOUTH, new SouthLocator());
		initLocator(EAST, new EastLocator());
		initLocator(WEST, new WestLocator());
		initLocator(NORTH_WEST, new NorthWestLocator());
		initLocator(NORTH_EAST, new NorthEastLocator());
		initLocator(SOUTH_WEST, new SouthWestLocator());
		initLocator(SOUTH_EAST, new SouthEastLocator());
		initLocator(CENTRE, new CentreLocator());
		initLocator(SCREEN_CENTRE, new ScreenCentreLocator());
		initLocator(ON_SCREEN_MID_LINE, new OnScreenMidLineLocator());
	}

	private void initLocator(FloatPosition floatPosition, Locator locator) {
		locators.put(floatPosition, locator);
	}

	private interface Locator {

		public int getY();

		public int getX();
	}

	private class NorthLocator implements Locator {

		public int getY() {
			return getNorthY();
		}

		public int getX() {
			return getCentreX();
		}
	}

	private class SouthLocator implements Locator {

		public int getY() {
			return getSouthY();
		}

		public int getX() {
			return getCentreX();
		}
	}

	private class EastLocator implements Locator {

		public int getY() {
			return getCentreY();
		}

		public int getX() {
			return getEastX();
		}
	}

	private class WestLocator implements Locator {

		public int getY() {
			return getCentreY();
		}

		public int getX() {
			return getWestX();
		}
	}

	private class NorthWestLocator implements Locator {

		public int getY() {
			return getNorthY();
		}

		public int getX() {
			return getWestX();
		}
	}

	private class NorthEastLocator implements Locator {

		public int getY() {
			return getNorthY();
		}

		public int getX() {
			return getEastX();
		}
	}

	private class SouthWestLocator implements Locator {

		public int getY() {
			return getSouthY();
		}

		public int getX() {
			return getWestX();
		}
	}

	private class SouthEastLocator implements Locator {

		public int getY() {
			return getSouthY();
		}

		public int getX() {
			return getEastX();
		}
	}

	private class CentreLocator implements Locator {

		public int getY() {
			return getCentreY();
		}

		public int getX() {
			return getCentreX();
		}
	}

	private class ScreenCentreLocator implements Locator {

		public int getY() {
			return getScreenCentreY();
		}

		public int getX() {
			return getScreenCentreX();
		}
	}

	private class OnScreenMidLineLocator implements Locator {

		public int getY() {
			return getOnScreenMidLineY();
		}

		public int getX() {
			return getScreenCentreX();
		}
	}

    private int getWestX() {
    	return getBackgroundBounds().x + PADDING;
    }

    private int getEastX() {
    	return (int)(
    			getBackgroundBounds().getMaxX()
    			- getFloatingSize().getWidth()
    			- PADDING);
    }

    private int getCentreX() {
    	Rectangle backgroundBounds = getBackgroundBounds();
    	return getCentreX(backgroundBounds);
    }

    private int getScreenCentreX() {
    	Rectangle backgroundBounds = getWindowBounds();
    	int x = getCentreX(backgroundBounds);
    	return convertFromScreenX(x);
    }

    private int getCentreX(Rectangle backgroundBounds) {
    	Dimension floatingSize = getFloatingSize();
    	return (int)(
    			backgroundBounds.getX()
    			+ (backgroundBounds.getWidth()/2.0)
    			- (floatingSize.getWidth()/2.0));
    }

    private int getNorthY() {
    	return getBackgroundBounds().y + PADDING;
    }

    private int getSouthY() {
    	return (int)(
    			getBackgroundBounds().getMaxY()
    			- getFloatingSize().getHeight()
    			- PADDING);
    }

    private int getCentreY() {
    	Rectangle backgroundBounds = getBackgroundBounds();
    	return getCentreY(backgroundBounds);
    }

    private int getScreenCentreY() {
    	Rectangle backgroundBounds = getWindowBounds();
    	int y = getCentreY(backgroundBounds);
    	return convertFromScreenY(y);
    }

    private int getCentreY(Rectangle backgroundBounds) {
    	Dimension floatingSize = getFloatingSize();
    	return (int)(
    			backgroundBounds.getY()
    			+ (backgroundBounds.getHeight()/2.0)
    			- (floatingSize.getHeight()/2.0));
    }

    private int getOnScreenMidLineY() {
    	Rectangle backgroundBounds = getWindowBounds();
    	Dimension floatingSize = getFloatingSize();
    	int y = (int)(
    			backgroundBounds.getY()
    			+ (backgroundBounds.getHeight()/2.0)
    			- floatingSize.getHeight());
    	return convertFromScreenY(y);
    }

    private int convertFromScreenX(int x) {
    	Point point = new Point(x, 0);
    	point = convertPointFromScreen(point);
    	return point.x;
    }

    private int convertFromScreenY(int y) {
    	Point point = new Point(0, y);
    	point = convertPointFromScreen(point);
    	return point.y;
    }

    private Point convertPointFromScreen(Point point) {
    	Point copy = new Point(point);
    	SwingUtilities.convertPointFromScreen(
    			copy,
    			getBackgroundComponent());
    	return copy;
    }

	private Dimension getFloatingSize() {
    	return getFloatingComponent().getPreferredSize();
    }

    private Rectangle getBackgroundBounds() {
        return getActualBackgroundComponent().getBounds();
    }

    private Rectangle getWindowBounds() {
    	return getWindow().getBounds();
    }

    private Window getWindow() {
    	return SwingUtilities.windowForComponent(
    			getBackgroundComponent());
    }

    private JComponent getActualBackgroundComponent() {
    	if (isBackgroundAScrollPane()) {
            return getBackgroundViewPort();
        }
        return getBackgroundComponent();
    }

    private boolean isBackgroundAScrollPane() {
    	return getBackgroundComponent() instanceof JScrollPane;
    }

	private JViewport getBackgroundViewPort() {
		JScrollPane scrollPane = (JScrollPane)getBackgroundComponent();
		return scrollPane.getViewport();
	}

	private JComponent getBackgroundComponent() {
		return floatingPaneModel.getBackgroundComponent();
	}

	private JComponent getFloatingComponent() {
		return floatingPaneModel.getFloatingComponent();
	}
}

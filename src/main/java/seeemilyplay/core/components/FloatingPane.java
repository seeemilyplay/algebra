package seeemilyplay.core.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import seeemilyplay.core.listeners.Listener;

/**
 * A custom swing component.  This should be used to float one smaller component above
 * a larger background component.
 * For example if you would like to include a legend for a graph that is displayed within
 * a scroll pane the code would look like this:
 *
 * FloatingComponent legend = createLegend();
 * JPanel graph = createGraph();
 *
 * FloatingPane floatingPane = new FloatingPane();
 * floatingPane.setDocking(FloatingPane.DOCK_NW);
 * floatingPane.setBackgroundComponent(new JScrollPane(graph));
 * floatingPane.setFloatingComponent(legend);
 *
 * When the page is first displayed the legend will appear in the top left of the scroll pane's
 * view.  It will remain there when the scroll bar is pulled down to view the bottom of the graph.
 *
 * The floating legend can be moved and resized as much as the user wants as long as it respects the
 * minimum size of the legend and stays within the confines of the scroll pane's view (or if the background
 * was another JComponent - within the bounds of the background component).
 *
 * If the user pulls the floating component to a side or a corner then it will stick there
 * (for example when they resize the application's window), until the
 * user pulls it away again (this is a kind of docking - but its more like the edges being 'sticky').
 *
 * By default the floating component is visible but you can change this using the
 *
 * isFloatingComponentVisible()
 * and setFloatingComponentVisible(boolean isVisible)
 *
 * methods.  You may want to do this if the floating component to pop-up and disappear as appropriate, for
 * example in the above method the you could pop the legend up whenever a user clicks on an graph axis.
 *
 * If the floating component has the potential to get the way of important things in the background component
 * then use the
 *
 * moveFloatingComponentAwayFromArea(Rectangle areaToAvoid)
 *
 * method.  For example if the background component is a table and you are using the floating component
 * as an input form to this table, then it wouldn't be very good if when you selected a table cell under
 * the floating component (probably using the arrow keys) the cell remained obscured.  By calling this
 * method with the bounds of the cell you've just selected the FloatingPane will deal with working out
 * if the cell is currently obscured and if it is move the floating component somewhere else more sensible.
 *
 * @deprecated
 */
@SuppressWarnings("serial")
public class FloatingPane extends JComponent {

	/** The size of the panel's resize corners*/
    private static int RESIZE_CORNER_SIZE = 5;
    /** The width of the panel's resize borders*/
    private static int RESIZE_BORDER_WIDTH = 5;

    /** Indicates to dock the floating component in the NW corner of the background component*/
    public static final int DOCK_NW = 0;
    /** Indicates to dock the floating component in the NE corner of the background component*/
    public static final int DOCK_NE = 1;
    /** Indicates to dock the floating component in the SW corner of the background component*/
    public static final int DOCK_SW = 2;
    /** Indicates to dock the floating component in the SE corner of the background component*/
    public static final int DOCK_SE = 3;
    /** The actual component that does all the hard work */
    private FloatingPaneComponent floatingPaneComponent;
    /** The background component */
    private JComponent backgroundComponent;
    /** The floating component*/
    private FloatingComponent floatingComponent;
    /** The docking settings*/
    private int docking = DOCK_SE;

    /**
     * This constructs a FloatingPane object which uses the given preferences to
     * determine the position of its floating component.
     */
    public FloatingPane() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout(0, 0));
    }

    /**
     * This builds the floating pane component from the current settings
     */
    private void buildFloatingPaneComponent() {
        if (floatingPaneComponent!=null) {
            floatingPaneComponent.clearUp();
        }
        if (backgroundComponent!=null && floatingComponent!=null) {
            this.removeAll();
            floatingPaneComponent = new FloatingPaneComponent(
                    backgroundComponent,
                    floatingComponent,
                    docking);
            this.add(floatingPaneComponent);
        }
    }

    /**
     * Sets the component that should appear in the background of this floating
     * pane.
     */
    public void setBackgroundComponent(JComponent backgroundComponent) {
        this.backgroundComponent = backgroundComponent;
        buildFloatingPaneComponent();
    }

    /**
     * Sets the component that should appear floating above the background
     * component in this floating pane.
     */
    public void setFloatingComponent(FloatingComponent floatingComponent) {
        this.floatingComponent = floatingComponent;
        buildFloatingPaneComponent();
    }

    /**
     * Sets the docking that the floating component should use if
     * no position for it is set in the preferences pair
     */
    public void setDocking(int docking) {
        this.docking = docking;
        buildFloatingPaneComponent();
    }

    /**
     * This sets whether the floating component is visible or not
     */
    public void setFloatingComponentVisible(boolean isVisible) {
        floatingPaneComponent.setFloatingComponentVisible(isVisible);
    }

    /**
     * This returns whether the floating component is currently visible or not
     */
    public boolean isFloatingComponentVisible() {
        return floatingPaneComponent.isFloatingComponentVisible();
    }

    /**
     * This moves the floating component out of the way of the specified rectangle
     */
    public void moveFloatingComponentAwayFromArea(Rectangle areaToAvoid) {
        floatingPaneComponent.moveFloatingComponentAwayFromArea(areaToAvoid);
    }

    /**
     * The actual implementation of the floating pane which extends the JLayeredPane
     */
    @SuppressWarnings("serial")
	private static class FloatingPaneComponent extends JLayeredPane {

    	/** The content component that should be displayed behind the floating component*/
        @SuppressWarnings("unused")
		private JComponent backgroundComponent;
        /** The floating component that should be displayed above the background component*/
        private FloatingComponent floatingComponent;
        /** The mover for the component*/
        private FloatingComponentMover floatingComponentMover;
        /** The preferred size change handler */
        private PreferredSizeChangeHandler preferredSizeChangeHandler;
        /** The layout manager*/
        private FloatingLayoutManager floatingLayoutManager;

        /**
         * Constructs a new FloatingPanel
         * @param backgroundComponent The component displayed behind the floating component
         * @param floatingComponent The component displayed above the background component
         * @param docking A flag to indicate how the floating component
         *                  should be docked if the prefs are empty or null
         * @param preferences The preferences for this floating pane
         */
        public FloatingPaneComponent(
                JComponent backgroundComponent,
                FloatingComponent floatingComponent,
                int docking) {
            super();
            //sets the layout manager using the given docking
            FloatingLayoutManager floatingLayoutManager = new FloatingLayoutManager(
                    backgroundComponent,
                    floatingComponent,
                    docking);
            setLayout(floatingLayoutManager);
            this.floatingLayoutManager = floatingLayoutManager;
            //add in the background and floating components
            this.add(backgroundComponent, 0);
            this.add(floatingComponent, 1);
            //keep refs to the two components
            this.floatingComponent = floatingComponent;
            this.backgroundComponent = backgroundComponent;
            //install listeners so that the floating component is movable
            installFloatingComponentMover();
            //install listeners so that the floating component will resize with its
            //preferred size
            installPreferredSizeChangeHandler();
        }

		/**
         * This clears up any mouse listeners that may be hanging around on the floating
         * component
         */
        public void clearUp() {
            if (floatingComponent!=null && floatingComponentMover!=null) {
                floatingComponent.removeMouseListener(floatingComponentMover);
                floatingComponent.removeMouseMotionListener(floatingComponentMover);
                floatingComponentMover = null;
            }
            if (floatingComponent!=null && preferredSizeChangeHandler!=null) {
            	floatingComponent.removeListener(preferredSizeChangeHandler);
            	preferredSizeChangeHandler = null;
            }
        }

        /**
         * This sets whether the floating component is visible or not
         */
        public void setFloatingComponentVisible(boolean isVisible) {
            floatingComponent.setVisible(isVisible);
        }

        /**
         * This returns whether the floating component is currently visible or not
         */
        public boolean isFloatingComponentVisible() {
            return floatingComponent.isVisible();
        }

        /**
         * This moves the floating component out of the way of the specified rectangle
         */
        public void moveFloatingComponentAwayFromArea(Rectangle areaToAvoid) {
            floatingLayoutManager.moveFloatingComponentAwayFromArea(areaToAvoid);
        }

        /**
         * This method installs a component mover on the display component
         */
        private void installFloatingComponentMover() {
            floatingComponentMover = new FloatingComponentMover(floatingLayoutManager, floatingComponent);
            floatingComponent.addMouseListener(floatingComponentMover);
            floatingComponent.addMouseMotionListener(floatingComponentMover);
        }

        /**
         * This method installs something that will cope with the floating
         * component altering its preferred size
         */
        private void installPreferredSizeChangeHandler() {
        	if (floatingComponent!=null && preferredSizeChangeHandler==null) {
	        	preferredSizeChangeHandler = new PreferredSizeChangeHandler(
	        			floatingLayoutManager,
	        			backgroundComponent,
	        			floatingComponent);
	        	floatingComponent.addListener(preferredSizeChangeHandler);
        	}
        }
    }

    /**
     * Deals with user re-sizing and moving of the floating component using the mouse.
     */
    private static class FloatingComponentMover implements MouseListener, MouseMotionListener {

    	/** The different areas that the mouse can be in*/
        /**nowhere significant*/
        private static final int NOWHERE_AREA = -1;
        /**somewhere that means component can be moved*/
        private static final int MOVE_AREA = -2;
        /**on the n edge*/
        private static final int N_AREA = -3;
        /**on the s edge*/
        private static final int S_AREA = -4;
        /**on the e edge*/
        private static final int E_AREA = -5;
        /**on the w edge*/
        private static final int W_AREA = -6;
        /**on the ne corner*/
        private static final int NE_AREA = -7;
        /**on the nw corner*/
        private static final int NW_AREA = -8;
        /**on the se corner*/
        private static final int SE_AREA = -9;
        /**on the sw corner*/
        private static final int SW_AREA = -10;
        /** The various states that the user interaction can be in*/
        /**nothing happened*/
        private static final int START_STATE = 0;
        /**currently moving*/
        private static final int MOVING_STATE = 1;
        /**moving northern edge*/
        private static final int RESIZING_N_STATE = 2;
        /**moving southern edge*/
        private static final int RESIZING_S_STATE = 3;
        /**moving western edge*/
        private static final int RESIZING_W_STATE = 4;
        /**moving eastern edge*/
        private static final int RESIZING_E_STATE = 5;
        /**moving nw corner*/
        private static final int RESIZING_NW_STATE = 6;
        /**moving ne corner*/
        private static final int RESIZING_NE_STATE = 7;
        /**moving sw corner*/
        private static final int RESIZING_SW_STATE = 8;
        /**moving se corner*/
        private static final int RESIZING_SE_STATE = 9;
        /** The component that this mover is for; the floating component for the pane*/
        private JComponent component;
        /** The layout manager for the floating pane that this component is in*/
        private FloatingLayoutManager floatingLayoutManager;
        /** The original cursor before any changes are made - this will typically be just an average arrow*/
        private Cursor originalCursor;
        /** The starting point of any resize or move*/
        private Point startingPoint = null;
        /** The state that the user interaction with the component is currently in*/
        private int state = START_STATE;

        /**
         * Creates a component mover that will deal with mouse input and resize/move the given
         * component accordingly
         */
        public FloatingComponentMover(FloatingLayoutManager floatingLayoutMananger, JComponent component) {
            this.floatingLayoutManager = floatingLayoutMananger;
            this.component = component;
            this.originalCursor = this.component.getCursor();
        }

        /**
         * Whether the floating component is currently being moved or resized
         */
        public boolean isBeingMovedOrResized() {
            return (state!=START_STATE);
        }

        /**
         * Simply show an appropriate cursor to indicate to the user that they can
         * move the component or resize an edge or corner
         */
        public void mouseClicked(MouseEvent e) {
            handleMouseEvent(e);
        }

        /**
         * Simply show an appropriate cursor to indicate to the user that they can
         * move the component or resize an edge or corner
         */
        public void mouseMoved(MouseEvent e) {
            handleMouseEvent(e);
        }

        /**
         * Simply show an appropriate cursor to indicate to the user that they can
         * move the component or resize an edge or corner
         */
        public void mouseEntered(MouseEvent e) {
            handleMouseEvent(e);
        }

        /**
         * If we aren't moving or resizing then we want
         * to reset the cursor to its original cursor
         */
        public void mouseExited(MouseEvent e) {
            if (state==START_STATE) {
                component.setCursor(originalCursor);
            }
        }

        /**
         * When the mouse is pressed we want to work out if it happened on a significant
         * part of the component (meaning that moving or resizing is appropriate) and if
         * it is we want to start off that process by setting a starting point (to calculate
         * moves and resizes relative to) and alter the cursor (although it mostly will be correct)
         * and alter the start (so that any further events can be interpretated correctly).
         */
        public void mousePressed(MouseEvent e) {
            if (state==START_STATE) {
                //work out where this mouse press event occurred
                int area = getArea(e.getPoint());
                //start off the appropriate move/resize process depending on the area pressed
                switch (area) {
                   /** case (N_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_N_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        break;
                    } case (S_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_S_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                        break;
                    } case (W_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_W_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                        break;
                    } case (E_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_E_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        break;
                    } case (NW_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_NW_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                        break;
                    }case (NE_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_NE_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        break;
                    } case (SW_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_SW_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                        break;
                    } case (SE_AREA): {
                        startingPoint = e.getPoint();
                        state = RESIZING_SE_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        break;
                    }*/ case (MOVE_AREA): {
                        startingPoint = e.getPoint();
                        state = MOVING_STATE;
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        break;
                    } case (NOWHERE_AREA): {
                        state = START_STATE;
                        component.setCursor(originalCursor);
                        break;
                    }
                }
            } else {
                handleMouseEvent(e);
            }
        }

        /**
         * If we are currently moving or resizing an edge or corner then we
         * want to handle this move event to change the size/position of the floating
         * component.
         */
        public void mouseDragged(MouseEvent e) {
            //if we are in the middle of a move or resize then alter the bounds
            //of the floating component as required
            if (state!=START_STATE) {
                //work out the bounds change that we wish to make
                BoundsChange desiredBoundsChange = getDesiredBoundsChange(e.getPoint());
                //request that the layout manager make these changes
                BoundsChange actualBoundsChange = floatingLayoutManager.alterFloatingComponentBounds(desiredBoundsChange);
                //now move the starting point to cope with actual change in the bounds made by the layout manager
                handleBoundsChange(actualBoundsChange);
            } else {
                handleMouseEvent(e);
            }
        }

        /**
         * When the mouse is released we want to finish off any resizing/moving processes
         * that are currently being performed by resetting the state to be a starting state,
         * and resetting the cursor, we also want to make sure that the component is validated
         * if a resize took place, as it will need to alter its layout.
         */
        public void mouseReleased(MouseEvent e) {
            switch(state) {
                case RESIZING_N_STATE:
                case RESIZING_S_STATE:
                case RESIZING_W_STATE:
                case RESIZING_E_STATE:
                case RESIZING_NE_STATE:
                case RESIZING_NW_STATE:
                case RESIZING_SE_STATE:
                case RESIZING_SW_STATE: {
                    component.validate();
                    state = START_STATE;
                    component.setCursor(originalCursor);
                    break;
                } case MOVING_STATE: {
                    state = START_STATE;
                    component.setCursor(originalCursor);
                    break;
                }
            }
            handleMouseEvent(e);
        }

        /**
         * This handles a general mouse event
         */
        private void handleMouseEvent(MouseEvent e) {
            if (state==START_STATE) {
                //get area of the current mouse position
                int area = getArea(e.getPoint());
                switch (area) {
                    /** case (N_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        break;
                    } case (S_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                        break;
                    } case (W_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                        break;
                    } case (E_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        break;
                    } case (NW_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                        break;
                    }case (NE_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        break;
                    } case (SW_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                        break;
                    } case (SE_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        break;
                    } */ case (MOVE_AREA): {
                        component.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        break;
                    } case (NOWHERE_AREA): {
                        component.setCursor(originalCursor);
                        break;
                    }
                }
            }
        }

        /**
         * This handles changes in the floating component bounds by altering the starting point
         * of the current move or resize
         */
        private void handleBoundsChange(BoundsChange boundsChange) {
            if (state==START_STATE) {
                throw new IllegalStateException();
            }
            if (boundsChange.getChangeInWidth()!=0 && boundsChange.getChangeInX()==0) {
                startingPoint = new Point(
                        startingPoint.x + boundsChange.getChangeInWidth(),
                        startingPoint.y);
            }
            if (boundsChange.getChangeInHeight()!=0 && boundsChange.getChangeInY()==0) {
                startingPoint = new Point(
                        startingPoint.x,
                        startingPoint.y + boundsChange.getChangeInHeight());
            }
        }

        /**
         * This works out the desired change of a move or resize given the new current mouse point
         */
        private BoundsChange getDesiredBoundsChange(Point point) {
            if (state==START_STATE) {
                throw new IllegalStateException();
            }
            //work out the changes we need to make to the bounds depending
            //on how we are moving or resizing the floating component
            int changeInX = 0;
            int changeInY = 0;
            int changeInWidth = 0;
            int changeInHeight = 0;
            switch (state) {
                case MOVING_STATE: {
                    changeInX = (point.x - startingPoint.x);
                    changeInY = (point.y - startingPoint.y);
                    break;
                } case RESIZING_N_STATE: {
                    changeInY = (point.y - startingPoint.y);
                    changeInHeight = (-1) * changeInY;
                    break;
                } case RESIZING_S_STATE: {
                    changeInHeight = (point.y - startingPoint.y);
                    break;
                } case RESIZING_W_STATE: {
                    changeInX = (point.x - startingPoint.x);
                    changeInWidth = (-1) * changeInX;
                    break;
                } case RESIZING_E_STATE: {
                    changeInWidth = (point.x - startingPoint.x);
                    break;
                } case RESIZING_NW_STATE: {
                    changeInY = (point.y - startingPoint.y);
                    changeInHeight = (-1) * changeInY;
                    changeInX = (point.x - startingPoint.x);
                    changeInWidth = (-1) * changeInX;
                    break;
                } case RESIZING_NE_STATE: {
                    changeInY = (point.y - startingPoint.y);
                    changeInHeight = (-1) * changeInY;
                    changeInWidth = (point.x - startingPoint.x);
                    break;
                } case RESIZING_SW_STATE: {
                    changeInHeight = (point.y - startingPoint.y);
                    changeInX = (point.x - startingPoint.x);
                    changeInWidth = (-1) * changeInX;
                    break;
                } case RESIZING_SE_STATE: {
                    changeInHeight = (point.y - startingPoint.y);
                    changeInWidth = (point.x - startingPoint.x);
                    break;
                }
            }
            return new BoundsChange(changeInX, changeInY, changeInWidth, changeInHeight);
        }

        /**
         * Work out the area that is indicated by the given mouse
         * position.
         */
        private int getArea(Point point) {
            int x = point.x;
            int y = point.y;
            //if they have exited the component then this is counted as no-where area
            if (x<0 || y<0) {
                return NOWHERE_AREA;
            }
            //work out if the point is at the top or bottom of the component
            boolean onTop = ((double)y) < (component.getBounds().getHeight()/2.0);
            //work out if the point is at the left or right of the component
            boolean onLeft = ((double)x) < (component.getBounds().getWidth()/2.0);
            //work out the smallest distance between the point and the top and bottom edges
            int verticalDistance = y;
            if (!onTop) {
                verticalDistance = (component.getBounds().height - y);
            }
            //work out the smallest distance between the point and the left and right edges
            int horizontalDistance = x;
            if (!onLeft) {
                horizontalDistance = (component.getBounds().width - x);
            }
            //see if these distance's mean that this point is in a corner
            if (verticalDistance<=RESIZE_CORNER_SIZE && horizontalDistance<=RESIZE_CORNER_SIZE) {
                //return the corner appropriate to where the the click was
                if (onTop) {
                    if (onLeft) {
                        return NW_AREA;
                    }
                    return NE_AREA;
                } else {
                    if (onLeft) {
                        return SW_AREA;
                    }
                    return SE_AREA;
                }
            }
            //see if these distance's mean that this point is at a left or right edge
            if (horizontalDistance<=RESIZE_BORDER_WIDTH) {
                //return the area appropriate to whether the click was at the left or right
                if (onLeft) {
                    return W_AREA;
                }
                return E_AREA;
            }
            //see if these distance's mean that this point is at a top or bottom edge
            if (verticalDistance<=RESIZE_BORDER_WIDTH) {
                //return the area appropriate to whether the click was at top or bottom
                if (onTop) {
                    return N_AREA;
                }
                return S_AREA;
            }
            //as we haven't got anything within an edge return that we are able to simply move
            //the component
            return MOVE_AREA;
        }
    }

    private static class PreferredSizeChangeHandler implements Listener {

    	/** The layout manager for the floating pane that this component is in*/
        private FloatingLayoutManager floatingLayoutManager;

        /** The background component */
        private JComponent backgroundComponent;
        /** The floating component that should be displayed above the background component*/
        private JComponent floatingComponent;

        private Dimension preferredSize;

        public PreferredSizeChangeHandler(
        		FloatingLayoutManager floatingLayoutManager,
        		JComponent backgroundComponent,
        		JComponent floatingComponent) {
        	this.floatingLayoutManager = floatingLayoutManager;
        	this.backgroundComponent = backgroundComponent;
        	this.floatingComponent = floatingComponent;
        	this.preferredSize = floatingComponent.getPreferredSize();
        }

        public void onChange() {
        	if (!SwingUtilities.isEventDispatchThread()) {
        		throw new Error();
        	}
        	Dimension newPreferredSize = floatingComponent.getPreferredSize();
			if (preferredSize!=null
					&& newPreferredSize!=null
					&& !preferredSize.equals(newPreferredSize)) {
				floatingLayoutManager.alterFloatingComponentBounds(calculateDesiredBoundsChange(
						preferredSize,
						newPreferredSize));
				floatingComponent.validate();
				floatingComponent.repaint();
			}
			preferredSize = newPreferredSize;
		}

		private BoundsChange calculateDesiredBoundsChange(
				Dimension oldPreferredSize, Dimension newPreferredSize) {

			Rectangle backgroundBounds = backgroundComponent.getBounds();
			Rectangle floatingBounds = floatingComponent.getBounds();

			int changeInX = 0;
			int changeInWidth = 0;

			int roomOnLeft = floatingBounds.x
								- backgroundBounds.x;
			int roomOnRight = (backgroundBounds.x
								+ backgroundBounds.width
								- floatingBounds.x
								- floatingBounds.width);

			if (oldPreferredSize.width<newPreferredSize.width) {
				int expansionRequired = (newPreferredSize.width - oldPreferredSize.width);
				if (roomOnLeft>roomOnRight) {
					//expand to the left
					changeInX = -1 * expansionRequired;
					changeInWidth = expansionRequired;
				} else {
					//expand to the right
					changeInWidth = expansionRequired;
				}
			}

			if (oldPreferredSize.width>newPreferredSize.width) {
				int contractionRequired = (oldPreferredSize.width - newPreferredSize.width);
				if (roomOnLeft>roomOnRight) {
					//contract on left side
					changeInX = contractionRequired;
					changeInWidth = -1 * contractionRequired;
				} else {
					//contract on right side
					changeInWidth = -1 * contractionRequired;
				}
			}

			int changeInY = 0;
			int changeInHeight = 0;

			int roomOnTop = floatingBounds.y
								- backgroundBounds.y;
			int roomOnBottom = (backgroundBounds.y
								+ backgroundBounds.height
								- floatingBounds.y
								- floatingBounds.height);

			if (oldPreferredSize.height<newPreferredSize.height) {
				int expansionRequired = (newPreferredSize.height - oldPreferredSize.height);
				if (roomOnTop>roomOnBottom) {
					//expand to the top
					changeInY = -1 * expansionRequired;
					changeInHeight = expansionRequired;
				} else if (roomOnBottom>roomOnTop) {
					//expand to the bottom
					changeInHeight = expansionRequired;
				}
			}

			if (oldPreferredSize.height>newPreferredSize.height) {
				int contractionRequired = (oldPreferredSize.height - newPreferredSize.height);
				if (roomOnTop>roomOnBottom) {
					//contract on top
					changeInY = contractionRequired;
					changeInHeight = -1 * contractionRequired;
				} else if (roomOnBottom>roomOnTop) {
					//contract on bottom
					changeInHeight = -1 * contractionRequired;
				}
			}

			return new BoundsChange(changeInX, changeInY, changeInWidth, changeInHeight);
		}
    }

    /**
     * This deals with laying out the floating component and the background component,
     * in particular it is responsible for moving and resizing the floating component at the
     * request of the FloatingComponentMover.
     */
    private static class FloatingLayoutManager implements LayoutManager {

        /** The content component that should be displayed behind the floating component*/
        private JComponent backgroundComponent;
        /** The floating component that should be displayed above the background component*/
        private JComponent floatingComponent;
        /** The current docking settings*/
        private StickyEdgeSettings stickyEdgeSettings;
        /**Whether this is the first time we have layed out the floating component*/
        private boolean firstLayoutOfFloatingComponent = true;
        /** Whether we prefer to stick things to the north or south*/
        private boolean preferNorth = true;
        /** Whether we prefer to stick things to the west or east*/
        private boolean preferWest = true;

        /**
         * Constructs a FloatingLayoutManager for the FloatingPane using the given
         * background component, floating component, and docking the floating component on the
         * background component as indicated by the docking flag.
         */
        public FloatingLayoutManager(
                JComponent backgroundComponent,
                JComponent floatingComponent,
                int docking) {
            this.backgroundComponent = backgroundComponent;
            this.floatingComponent = floatingComponent;
            //set up all the sticky edges settings based on the docking flag
            stickyEdgeSettings = StickyEdgeSettings.getStickyEdgeSettings(docking);
            //add a listener to the floating component - this will listen for dockings as the
            //component moves - this means that when the background component resizes the dockings can be preserved
            this.floatingComponent.addComponentListener(new ComponentAdapter() {
                public void componentMoved(ComponentEvent e) {
                    stickyEdgeSettings = calculateCurrentStickyEdges();
                    savePreferences();
                }
                public void componentResized(ComponentEvent e) {
                    stickyEdgeSettings = calculateCurrentStickyEdges();
                    savePreferences();
                }
            });
            //we would normally get re-layouts fired when the background component alters size,
            //but not in the case of JScrollPanes so we need to listen specially for them.
            if (backgroundComponent instanceof JScrollPane) {
                ((JScrollPane)backgroundComponent).getViewport().addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        layoutFloatingComponent();
                    }
                    public void componentMoved(ComponentEvent e) {
                        layoutFloatingComponent();
                    }
                });
            }
        }

        /**
         * Leave an empty implementation as this will never happen.
         */
        public void removeLayoutComponent(Component comp) {
        }

        /**
         * Empty implementation
         */
        public void addLayoutComponent(String name, Component comp) {
        }

        /**
         * Lays out the container as you would expect, except that the floating component
         * is handled specially.
         */
        public void layoutContainer(Container parent) {
            if (parent!=null && parent.getComponents().length==2) {
                backgroundComponent.setBounds(parent.getBounds());
                layoutFloatingComponent();
            }
        }

        /**
         * This works out which edges should be sticky based on the current position
         * of the floating component on the background component
         */
        private StickyEdgeSettings calculateCurrentStickyEdges() {
            Rectangle backgroundBounds = getBackgroundBounds();
            Rectangle floatingBounds = floatingComponent.getBounds();
            boolean stuckToSouth = (backgroundBounds.getMaxY() <= floatingBounds.getMaxY());
            boolean stuckToNorth = (!stuckToSouth) && (backgroundBounds.getMinY() >= floatingBounds.getMinY());
            boolean stuckToEast = (backgroundBounds.getMaxX() <= floatingBounds.getMaxX());
            boolean stuckToWest = (!stuckToEast) && (backgroundBounds.getMinX() >= floatingBounds.getMinX());

            return new StickyEdgeSettings(stuckToNorth, stuckToSouth, stuckToWest, stuckToEast);
        }

        /**
         * This moves the floating component out of the way of the specified rectangle
         */
        public void moveFloatingComponentAwayFromArea(Rectangle areaToAvoid) {
            layoutFloatingComponent(areaToAvoid);
        }

        /**
         * This lays out the the floating component
         */
        private void layoutFloatingComponent() {
            layoutFloatingComponent(null);
        }

        /**
         * This lays out the the floating component
         */
        private void layoutFloatingComponent(Rectangle areaToAvoid)
        {
            if (firstLayoutOfFloatingComponent) {
                //we want to load up the preferences pair in order to set the sticky edge settings
                loadPreferences();
            } else {
                //we want to update the sticky edge settings to cope with
                //things like when the floating component is swallowed by shrinking background
                //component
                stickyEdgeSettings = StickyEdgeSettings.combineStickyEdgeSettings(
                        stickyEdgeSettings,
                        calculateCurrentStickyEdges(),
                        preferNorth,
                        preferWest);
            }
            Rectangle newFloatingBounds = calculateBounds(stickyEdgeSettings);
            //if the floating bounds suggested currently aren't very good then work out an alternative
            //settting - if there aren't any good alternatives just keep with this one.
            if (!areSensibleBounds(newFloatingBounds, areaToAvoid)) {
                StickyEdgeSettings[] alternativeSettings = stickyEdgeSettings.getAlternativeSettings();
                boolean foundAlternative = false;
                for (int i=0; !foundAlternative && i<alternativeSettings.length; i++) {
                    Rectangle alternativeFloatingBounds = calculateBounds(alternativeSettings[i]);
                    if (areSensibleBounds(alternativeFloatingBounds, areaToAvoid)) {
                        newFloatingBounds = alternativeFloatingBounds;
                        foundAlternative = true;
                    }
                }
            }
            floatingComponent.setBounds(newFloatingBounds);
            firstLayoutOfFloatingComponent = false;
            stickyEdgeSettings = calculateCurrentStickyEdges();
            savePreferences();
        }

        /**
         * This works out whether the suggested bounds are any good
         */
        private boolean areSensibleBounds(Rectangle suggestedBounds, Rectangle areaToAvoid) {
            if (isPossibleFloatingComponentBounds(suggestedBounds)) {
                if (areaToAvoid!=null) {
                    Rectangle intersection = new Rectangle();
                    Rectangle.intersect(suggestedBounds, areaToAvoid, intersection);
                    return (intersection.width<=0 || intersection.height<=0);
                } else {
                    return true;
                }
            }
            return false;
        }

        /**
         * This returns the new bounds given the sticky edges specified
         */
        private Rectangle calculateBounds(StickyEdgeSettings stickyEdgeSettings) {
            Rectangle backgroundBounds = getBackgroundBounds();
            Rectangle currentFloatingBounds = floatingComponent.getBounds();
            int x = currentFloatingBounds.x;
            int y = currentFloatingBounds.y;
            //work out which edges we are stuck to
            boolean stuckToNorth = stickyEdgeSettings.isStuckToNorth();
            boolean stuckToSouth = stickyEdgeSettings.isStuckToSouth();
            boolean stuckToWest = stickyEdgeSettings.isStuckToWest();
            boolean stuckToEast = stickyEdgeSettings.isStuckToEast();
            //use our prefered edge settings to work out which edge we are set to when
            //there is a clash
            if (stuckToNorth && stuckToSouth) {
                stuckToNorth = preferNorth;
                stuckToSouth = !preferNorth;
            }
            if (stuckToWest && stuckToEast) {
                stuckToWest = preferWest;
                stuckToEast = !preferWest;
            }
            if (stuckToNorth) {
                y = (int)backgroundBounds.getMinY();
            } else if (stuckToSouth) {
                y = ((int)backgroundBounds.getMaxY()) - currentFloatingBounds.height;
            }
            if (stuckToWest) {
                x = (int)backgroundBounds.getMinX();
            } else if (stuckToEast) {
                x = ((int)backgroundBounds.getMaxX()) - currentFloatingBounds.width;
            }
            return new Rectangle(
                    x,
                    y,
                    currentFloatingBounds.width,
                    currentFloatingBounds.height);
        }

        /**
         * This attempts to make the given modifications to the floating component's bounds.  If these
         * changes move the bounds into an area that isn't allowed then the actual bounds change may be
         * different to the one requested.  The real change is returned.
         */
        public BoundsChange alterFloatingComponentBounds(BoundsChange desiredBoundsChange) {
            BoundsChange possibleBoundsChange = getPossibleBoundsChange(desiredBoundsChange);
            if (!BoundsChange.getEmptyInstance().equals(possibleBoundsChange)) {
                Rectangle currentFloatingBounds = floatingComponent.getBounds();
                Rectangle newBounds = possibleBoundsChange.calculateNewBounds(currentFloatingBounds);
                floatingComponent.setBounds(newBounds);
            }
            return possibleBoundsChange;
        }

        /**
         * This works out a possible bounds change based on the desired bounds change.
         */
        public BoundsChange getPossibleBoundsChange(BoundsChange desiredBoundsChange) {
            Rectangle currentFloatingBounds = floatingComponent.getBounds();
            //firstly work out the horizontal portion of the changes that we can accept
            BoundsChange horizontalBoundsChange = desiredBoundsChange.getHorizontalBoundsChange();
            while (!BoundsChange.getEmptyInstance().equals(horizontalBoundsChange)
                    && !isPossibleFloatingComponentBounds(horizontalBoundsChange.calculateNewBounds(currentFloatingBounds))) {
            	//then such a horizontal change is impossible so reduce it to a smaller size
                horizontalBoundsChange = horizontalBoundsChange.getSmallerHorizontalBoundsChange();
            }
            //now work out the vertical portion of the changes that we can accept
            BoundsChange verticalBoundsChange = desiredBoundsChange.getVerticalBoundsChange();
            while (!BoundsChange.getEmptyInstance().equals(verticalBoundsChange)
                    && !isPossibleFloatingComponentBounds(verticalBoundsChange.calculateNewBounds(currentFloatingBounds))) {
                //then such a vertical change is impossible so reduce it to a smaller size
                verticalBoundsChange = verticalBoundsChange.getSmallerVerticalBoundsChange();
            }
            //now combine these changes together to get the actual bounds change
            return BoundsChange.combineBoundsChanges(horizontalBoundsChange, verticalBoundsChange);
        }

        /**
         * This returns whether the suggested bounds for the floating component is a possibility.
         */
        private boolean isPossibleFloatingComponentBounds(Rectangle possibleBounds) {

            //makes sure that the possible bounds are within the background boundsChange
            Rectangle backgroundBounds = getBackgroundBounds();
            boolean withinBackgroundBounds = backgroundBounds.getMinX() <= possibleBounds.getMinX()
                    && backgroundBounds.getMaxX() >= possibleBounds.getMaxX()
                    && backgroundBounds.getMinY() <= possibleBounds.getMinY()
                    && backgroundBounds.getMaxY() >= possibleBounds.getMaxY();
            if (!withinBackgroundBounds) {
                return false;
            }
          /**  //makes sure that the possible bounds are larger than the floating component
            //minimum size
            Dimension minimumSize = floatingComponent.getMinimumSize();
            boolean largerThanMin = minimumSize.width <= possibleBounds.width
                    && minimumSize.height <= possibleBounds.height;
            if (!largerThanMin) {
                return false;
            }**/
            return true;
        }

        /**
         * This returns the background bounds
         */
        private Rectangle getBackgroundBounds() {
            //if this is a scroll pane then really we are just interested in the bounds
            //of the viewport - not the entire pane (we want to miss out all of the scroll bars)
            if (backgroundComponent instanceof JScrollPane) {
                return ((JScrollPane)backgroundComponent).getViewport().getBounds();
            }
            return backgroundComponent.getBounds();
        }

        /**
         * This saves the current settings to the preferences
         */
        public void savePreferences() {
                return;
        }

        /**
         * This loads the current settings from preferences
         */
        public void loadPreferences() {
            boolean isStuckNorth = stickyEdgeSettings.isStuckToNorth();
            boolean isStuckSouth = stickyEdgeSettings.isStuckToSouth();
            boolean isStuckWest = stickyEdgeSettings.isStuckToWest();
            boolean isStuckEast = stickyEdgeSettings.isStuckToEast();
            //update our preferred settings
            preferNorth = isStuckNorth;
            preferWest = isStuckWest;
            stickyEdgeSettings = new StickyEdgeSettings(isStuckNorth, isStuckSouth, isStuckWest, isStuckEast);
            Rectangle currentFloatingBounds = floatingComponent.getBounds();
            int x = currentFloatingBounds.x;
            int y = currentFloatingBounds.y;
            int width = currentFloatingBounds.width;
            int height = currentFloatingBounds.height;
            floatingComponent.setBounds(x, y, width, height);
            //now set up default positioning and sizes for the floating component
            //where the preferences load has failed
            Rectangle currentFloatingBounds2 = floatingComponent.getBounds();
            int x2 = (currentFloatingBounds2.x>=0) ? currentFloatingBounds2.x : 0;
            int y2 = (currentFloatingBounds2.y>=0) ? currentFloatingBounds2.y : 0;
            int width2 = (currentFloatingBounds2.width>=floatingComponent.getMinimumSize().width) ?
                    currentFloatingBounds2.width : floatingComponent.getPreferredSize().width;
            int height2 = (currentFloatingBounds2.height>=floatingComponent.getMinimumSize().height) ?
                    currentFloatingBounds2.height : floatingComponent.getPreferredSize().height;
            floatingComponent.setBounds(x2, y2, width2, height2);
        }

        /**
         * Returns the minimum layout size out of the background and the floating component
         */
        public Dimension minimumLayoutSize(Container parent) {
            Dimension ret = new Dimension();
            Component[] children = parent.getComponents();
            for (int i=0; i<children.length; i++) {
                Dimension d = children[i].getMinimumSize();
                ret.width = Math.max( ret.width, d.width );
                ret.height = Math.max( ret.height, d.height );
            }
            return ret;
        }

        /**
         * Returns the preferred layout size out of the background and the floating component
         */
        public Dimension preferredLayoutSize(Container parent) {
            Dimension ret = new Dimension();
            Component[] children = parent.getComponents();
            for (int i=0; i<children.length; i++) {
                Dimension d = children[i].getPreferredSize();
                ret.width = Math.max( ret.width, d.width );
                ret.height = Math.max( ret.height, d.height );
            }
            return ret;
        }
    }

    /**
     * This represents a set of changes that could or have been made to the floating
     * component's bounds.
     */
    private static class BoundsChange {

    	private static final BoundsChange noBoundsChange = new BoundsChange(0, 0, 0, 0);
        private int changeInX;
        private int changeInY;
        private int changeInWidth;
        private int changeInHeight;
        public BoundsChange(int changeInX, int changeInY, int changeInWidth, int changeInHeight) {
            this.changeInX = changeInX;
            this.changeInY = changeInY;
            this.changeInWidth = changeInWidth;
            this.changeInHeight = changeInHeight;
        }

        public int getChangeInHeight() {
            return changeInHeight;
        }

        public int getChangeInWidth() {
            return changeInWidth;
        }

        public int getChangeInX() {
            return changeInX;
        }

        public int getChangeInY() {
            return changeInY;
        }

        /**
         * This returns the new bounds rectangle after applying all the changes
         * that this object represents to the given original bounds rectangle.
         */
        public Rectangle calculateNewBounds(Rectangle originalBounds) {
            return new Rectangle(
                    originalBounds.x + changeInX,
                    originalBounds.y + changeInY,
                    originalBounds.width + changeInWidth,
                    originalBounds.height + changeInHeight);
        }

        /**
         * This returns the BoundsChange object if you take only the horizontal
         * changes from this bounds change object
         */
        public BoundsChange getHorizontalBoundsChange() {
            return new BoundsChange(changeInX, 0, changeInWidth, 0);
        }

        /**
         * Returns a decremented version of the horizontal bounds change
         */
        public BoundsChange getSmallerHorizontalBoundsChange() {
            int xDecrement = 0;
            if (changeInX>0) {
                xDecrement = -1;
            } else if (changeInX<0) {
                xDecrement = 1;
            }
            int widthDecrement = 0;
            if (changeInWidth>0) {
                widthDecrement = -1;
            } else if (changeInWidth<0) {
                widthDecrement = 1;
            }
            return new BoundsChange(changeInX + xDecrement, 0, changeInWidth + widthDecrement, 0);
        }

        /**
         * This returns the BoundsChange object if you take only the vertical
         * changes from this bounds change object
         */
        public BoundsChange getVerticalBoundsChange() {
            return new BoundsChange(0, changeInY, 0, changeInHeight);
        }

        /**
         * Returns a decremented version of the vertical bounds change
         */
        public BoundsChange getSmallerVerticalBoundsChange() {
            int yDecrement = 0;
            if (changeInY>0) {
                yDecrement = -1;
            } else if (changeInY<0) {
                yDecrement = 1;
            }
            int heightDecrement = 0;
            if (changeInHeight>0) {
                heightDecrement = -1;
            } else if (changeInHeight<0) {
                heightDecrement = 1;
            }
            return new BoundsChange(0, changeInY + yDecrement, 0, changeInHeight + heightDecrement);
        }

        /**
         * This combines two given BoundsChanges
         */
        public static BoundsChange combineBoundsChanges(BoundsChange horizontalBoundsChange, BoundsChange verticalBoundsChange) {
            return new BoundsChange(
                    horizontalBoundsChange.getChangeInX(),
                    verticalBoundsChange.getChangeInY(),
                    horizontalBoundsChange.getChangeInWidth(),
                    verticalBoundsChange.getChangeInHeight());
        }

        /**
         * The empty/no bounds change
         */
        public static BoundsChange getEmptyInstance() {
            return noBoundsChange;
        }

        /**
         * Prints out the bounds change nicely
         */
        public String toString() {
            return "(x="+changeInX+", y="+changeInY+", width="+changeInWidth+", height="+changeInHeight+")";
        }

        /**
         * Equals based on the four ints defining bound change
         */
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final BoundsChange that = (BoundsChange) o;
            if (changeInHeight != that.changeInHeight) return false;
            if (changeInWidth != that.changeInWidth) return false;
            if (changeInX != that.changeInX) return false;
            if (changeInY != that.changeInY) return false;
            return true;
        }

        public int hashCode() {
            int result;
            result = changeInX;
            result = 29 * result + changeInY;
            result = 29 * result + changeInWidth;
            result = 29 * result + changeInHeight;
            return result;
        }
    }

    private static class StickyEdgeSettings {

    	/**Whether the floating component is stuck to the north edge of the background component or not*/
        private boolean stuckToNorth = false;
        /**Whether the floating component is stuck to the south edge of the background component or not*/
        private boolean stuckToSouth = false;
        /**Whether the floating component is stuck to the west edge of the background component or not*/
        private boolean stuckToWest = false;
        /**Whether the floating component is stuck to the east edge of the background component or not*/
        private boolean stuckToEast = false;
        private final static StickyEdgeSettings stuckToNW = new StickyEdgeSettings(true, false, true, false);
        private final static StickyEdgeSettings stuckToNE = new StickyEdgeSettings(true, false, false, true);
        private final static StickyEdgeSettings stuckToSW = new StickyEdgeSettings(false, true, true, false);
        private final static StickyEdgeSettings stuckToSE = new StickyEdgeSettings(false, true, false, true);
        private final static StickyEdgeSettings stuckToN = new StickyEdgeSettings(true, false, false, false);
        private final static StickyEdgeSettings stuckToS = new StickyEdgeSettings(false, true, false, false);
        private final static StickyEdgeSettings stuckToW = new StickyEdgeSettings(false, false, true, false);
        private final static StickyEdgeSettings stuckToE = new StickyEdgeSettings(false, false, false, true);
        private final static StickyEdgeSettings notStuck = new StickyEdgeSettings(false, false, false, false);
        public StickyEdgeSettings(boolean stuckToNorth, boolean stuckToSouth, boolean stuckToWest, boolean stuckToEast) {
            this.stuckToNorth = stuckToNorth;
            this.stuckToSouth = stuckToSouth;
            this.stuckToWest = stuckToWest;
            this.stuckToEast = stuckToEast;
        }

        /**
         * Returns the sticky edge settings appropriate for the given docking setting
         */
        public static StickyEdgeSettings getStickyEdgeSettings(int docking) {
            switch (docking) {
                case DOCK_NW: {
                    return stuckToNW;
                } case DOCK_NE: {
                    return stuckToNE;
                } case DOCK_SW: {
                    return stuckToSW;
                } case DOCK_SE: {
                    return stuckToSE;
                } default: {
                    return notStuck;
                }
            }
        }

        public boolean isStuckToEast() {
            return stuckToEast;
        }

        public boolean isStuckToNorth() {
            return stuckToNorth;
        }

        public boolean isStuckToSouth() {
            return stuckToSouth;
        }

        public boolean isStuckToWest() {
            return stuckToWest;
        }

        /**
         * This returns an array of alternative sticky edge settings to try in order
         * if this particular sticky edge setting is not a very valid one.
         */
        public StickyEdgeSettings[] getAlternativeSettings() {
            if (this.equals(stuckToNW)) {
                return new StickyEdgeSettings[] {stuckToSW, stuckToNE, stuckToSE};
            } else if (this.equals(stuckToNE)) {
                return new StickyEdgeSettings[] {stuckToSE, stuckToNW, stuckToSW};
            } else if (this.equals(stuckToSW)) {
                return new StickyEdgeSettings[] {stuckToSE, stuckToNW, stuckToNE};
            } else if (this.equals(stuckToSE)) {
                return new StickyEdgeSettings[] {stuckToNE, stuckToSW, stuckToNW};
            } else if (this.equals(stuckToN)) {
                return new StickyEdgeSettings[] {stuckToNE, stuckToNW, stuckToS, stuckToSE, stuckToSW};
            } else if (this.equals(stuckToS)) {
                return new StickyEdgeSettings[] {stuckToSE, stuckToSW, stuckToN, stuckToNE, stuckToNW};
            } else if (this.equals(stuckToW)) {
                return new StickyEdgeSettings[] {stuckToNW, stuckToSW, stuckToE, stuckToNE, stuckToSE};
            } else if (this.equals(stuckToE)) {
                return new StickyEdgeSettings[] {stuckToNE, stuckToSE, stuckToW, stuckToNW, stuckToSW};
            } else if (this.equals(notStuck)) {
                return new StickyEdgeSettings[] {stuckToSE, stuckToE, stuckToS, stuckToNE, stuckToSW, stuckToN, stuckToW, stuckToNW};
            }
            //this should never happen
            throw new IllegalStateException();
        }

        /**
         * This combines the sticky-ness of two sticky edges settings
         */
        public static StickyEdgeSettings combineStickyEdgeSettings(StickyEdgeSettings settingsA, StickyEdgeSettings settingsB, boolean preferNorth, boolean preferWest) {
            if (settingsA.equals(settingsB)) {
                return settingsA;
            }
            boolean stuckToN = (settingsA.isStuckToNorth() || settingsB.isStuckToNorth());
            boolean stuckToS = (settingsA.isStuckToSouth() || settingsB.isStuckToSouth());
            boolean stuckToW = (settingsA.isStuckToWest() || settingsB.isStuckToWest());
            boolean stuckToE = (settingsA.isStuckToEast() || settingsB.isStuckToEast());
            //when we have clashes use the prefered edges
            if (stuckToN && stuckToS) {
                stuckToN = preferNorth;
                stuckToS = !preferNorth;
            }
            if (stuckToW && stuckToE) {
                stuckToW = preferWest;
                stuckToE = !preferWest;
            }
            return new StickyEdgeSettings(stuckToN, stuckToS, stuckToW, stuckToE);
        }

        /**
         * Print out prettily for debug's sake
         */
        public String toString() {
            if (this.equals(stuckToNW)) {
                return "stuckToNW";
            } else if (this.equals(stuckToNE)) {
                return "stuckToNE";
            } else if (this.equals(stuckToSW)) {
                return "stuckToSW";
            } else if (this.equals(stuckToSE)) {
                return "stuckToSE";
            } else if (this.equals(stuckToN)) {
                return "stuckToN";
            } else if (this.equals(stuckToS)) {
                return "stuckToS";
            } else if (this.equals(stuckToW)) {
                return "stuckToW";
            } else if (this.equals(stuckToE)) {
                return "stuckToE";
            } else if (this.equals(notStuck)) {
                return "notStuck";
            }
            //this should never happen
            throw new IllegalStateException();
        }

        /**
         * The equals bases equality on the four booleans
         */
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final StickyEdgeSettings that = (StickyEdgeSettings) o;
            if (stuckToEast != that.stuckToEast) return false;
            if (stuckToNorth != that.stuckToNorth) return false;
            if (stuckToSouth != that.stuckToSouth) return false;
            if (stuckToWest != that.stuckToWest) return false;
            return true;
        }

        public int hashCode() {
            int result;
            result = (stuckToNorth ? 1 : 0);
            result = 29 * result + (stuckToSouth ? 1 : 0);
            result = 29 * result + (stuckToWest ? 1 : 0);
            result = 29 * result + (stuckToEast ? 1 : 0);
            return result;
        }
    }
}

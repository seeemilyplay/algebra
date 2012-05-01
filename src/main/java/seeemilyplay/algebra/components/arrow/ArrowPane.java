package seeemilyplay.algebra.components.arrow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
public final class ArrowPane extends JComponent {

	private final ArrowPaneModel arrowPaneModel;

	private final JComponent backgroundComponent;
	private final JPanel foregroundPanel;

	private boolean isLayeredPaneInitialised = false;

	private JLayeredPane layeredPane;
	private ArrowLayoutManager layoutManager;

	private Listener modelListener;

	private int padding = 5;

	public ArrowPane(
			ArrowPaneModel arrowPaneModel,
			JComponent backgroundComponent) {
		this.arrowPaneModel = arrowPaneModel;
		this.backgroundComponent = backgroundComponent;
		this.foregroundPanel = new JPanel();

		initLayout();
		initForegroundPanel();
		initLayeredPane();
		addLayers();
		installListeners();
		relayout();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		foregroundPanel.setLayout(new BorderLayout());
		layoutManager = new ArrowLayoutManager();
	}

	private void initForegroundPanel() {
		foregroundPanel.setLayout(new BorderLayout());
		foregroundPanel.setOpaque(false);
	}

	private void initLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(layoutManager);
		add(layeredPane);
	}

	private void addLayers() {
		layeredPane.add(backgroundComponent, new Integer(0));
		layeredPane.add(foregroundPanel, new Integer(1));
		isLayeredPaneInitialised = true;
	}

	private void installListeners() {
		installArrowPaneModelListener();
		installScrollPaneListenerIfRequired();
	}

	private void installArrowPaneModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				relayout();
			}
		};
		arrowPaneModel.addListener(modelListener);
	}

	private void installScrollPaneListenerIfRequired() {
		if (isBackgroundAScrollPane()) {
			installScrollPaneListener();
        }
	}

	private boolean isBackgroundAScrollPane() {
    	return backgroundComponent instanceof JScrollPane;
    }

	private JViewport getBackgroundViewPort() {
		JScrollPane scrollPane = (JScrollPane)backgroundComponent;
		return scrollPane.getViewport();
	}

	private void installScrollPaneListener() {
		getBackgroundViewPort().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	relayout();
            }
            public void componentMoved(ComponentEvent e) {
            	relayout();
            }
        });
	}

	public void setPadding(int padding) {
		this.padding = padding;
		relayout();
	}

	private void relayout() {
		layoutManager.layoutContainer(this);
		repaint();
	}

	private boolean isPointing() {
		return arrowPaneModel.isAttentionArea();
	}

	private Rectangle getAttentionArea() {
		return arrowPaneModel.getAttentionArea();
	}

	private ArrowDirection getArrowDirection() {
		return arrowPaneModel.getArrowDirection();
	}

	private boolean isReady() {
		return isLayeredPaneInitialised;
	}

	private class ArrowLayoutManager implements LayoutManager {

		private final Map<ArrowDirection,ArrowInformation> arrowInformation =
			new HashMap<ArrowDirection,ArrowInformation>();

		public ArrowLayoutManager() {
			super();

			initLocationInformation();
		}

		private void initLocationInformation() {
			initInfo(ArrowDirection.NORTH, new NorthInfo());
			initInfo(ArrowDirection.NORTH_WEST, new NorthWestInfo());
			initInfo(ArrowDirection.NORTH_EAST, new NorthEastInfo());
			initInfo(ArrowDirection.SOUTH_WEST, new SouthWestInfo());
			initInfo(ArrowDirection.SOUTH_EAST, new SouthEastInfo());
		}

		private void initInfo(
				ArrowDirection arrowDirection,
				ArrowInformation info) {
			arrowInformation.put(
					arrowDirection,
					info);
		}

		public void layoutContainer(Container parent) {
			if (isReady()) {
				layoutBackground(parent);
				layoutForeground(parent);
			}
		}

		private void layoutBackground(Container parent) {
			backgroundComponent.setBounds(parent.getBounds());
		}

		private void layoutForeground(Container parent) {
			updateForegroundVisibility();
			updateForegroundContents();
			if (isPointing()) {
				updateForegroundLocation(parent);
			}
			foregroundPanel.validate();
			foregroundPanel.repaint();
		}

		private void updateForegroundVisibility() {
			foregroundPanel.setVisible(isPointing());
		}

		private void updateForegroundContents() {
			foregroundPanel.removeAll();
			if (isPointing()) {
				foregroundPanel.add(getPointer());
			}
		}

		private JComponent getPointer() {
			return new JLabel(getPointerIcon());
		}

		private Icon getPointerIcon() {
			return getArrowInformation().getIcon();
		}

		private ArrowInformation getArrowInformation() {
			return arrowInformation.get(getArrowDirection());
		}

		private void updateForegroundLocation(Container parent) {
			Point location = getForegroundLocation(parent);
			setForegroundLocation(location);
		}

		private Point getForegroundLocation(Container parent) {
			Point location = getForegroundScreenLocation();
			SwingUtilities.convertPointFromScreen(location, parent);
			return location;
		}

		private Point getForegroundScreenLocation() {
			ArrowInformation arrowInformation = getArrowInformation();
			return new Point(
					arrowInformation.getX(),
					arrowInformation.getY());
		}

		private void setForegroundLocation(Point location) {
			foregroundPanel.setBounds(new Rectangle(
					location,
					getForegroundSize()));
		}

		public void addLayoutComponent(String name, Component comp) {
			return;
		}

		public void removeLayoutComponent(Component comp) {
			return;
		}

		public Dimension minimumLayoutSize(Container parent) {
			return backgroundComponent.getMinimumSize();
		}

		public Dimension preferredLayoutSize(Container parent) {
			return backgroundComponent.getPreferredSize();
		}
	}

	private interface ArrowInformation {

		public int getY();

		public int getX();

		public Icon getIcon();
	}

	private class NorthInfo implements ArrowInformation {

		public int getY() {
			return getSouthY();
		}

		public int getX() {
			return getCentreX();
		}

		public Icon getIcon() {
			return getTheme().getPointerN();
		}
	}

	private class NorthEastInfo implements ArrowInformation {

		public int getY() {
			return getBelowCentreY();
		}

		public int getX() {
			return getWestX();
		}

		public Icon getIcon() {
			return getTheme().getPointerNE();
		}
	}

	private class NorthWestInfo implements ArrowInformation {

		public int getY() {
			return getBelowCentreY();
		}

		public int getX() {
			return getEastX();
		}

		public Icon getIcon() {
			return getTheme().getPointerNW();
		}
	}

	private class SouthEastInfo implements ArrowInformation {

		public int getY() {
			return getAboveCentreY();
		}

		public int getX() {
			return getWestX();
		}

		public Icon getIcon() {
			return getTheme().getPointerSE();
		}
	}

	private class SouthWestInfo implements ArrowInformation {

		public int getY() {
			return getAboveCentreY();
		}

		public int getX() {
			return getEastX();
		}

		public Icon getIcon() {
			return getTheme().getPointerSW();
		}
	}

    private int getSouthY() {
    	return (int)(getAttentionArea().getMaxY()
    			+ padding);
    }

    private int getAboveCentreY() {
    	Rectangle attentionArea = getAttentionArea();
    	return (int)(attentionArea.y
    			+ (attentionArea.height / 2.0)
    			- getForegroundSize().height);
    }

    private int getBelowCentreY() {
    	Rectangle attentionArea = getAttentionArea();
    	return (int)(attentionArea.y
    			+ (attentionArea.height / 2.0));
    }

    private int getWestX() {
		return (getAttentionArea().x
				- getForegroundSize().width
				- padding);
    }

    private int getEastX() {
    	return (int)(getAttentionArea().getMaxX()
    			+ padding);
    }

    private int getCentreX() {
    	Rectangle attentionArea = getAttentionArea();
    	return (int)(attentionArea.x
    			+ (attentionArea.width / 2.0)
    			- (getForegroundSize().width / 2.0));
    }

    private AlgebraTheme getTheme() {
    	return AlgebraTheme.getInstance();
    }

	private Dimension getForegroundSize() {
    	return foregroundPanel.getPreferredSize();
    }
}

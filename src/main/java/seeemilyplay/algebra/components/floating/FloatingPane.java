package seeemilyplay.algebra.components.floating;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import seeemilyplay.algebra.components.fading.FadingComponent;
import seeemilyplay.algebra.components.fading.FadingModel;
import seeemilyplay.core.listeners.Listener;

/**
 * A pane that displays a floating component over
 * a background component.
 */
@SuppressWarnings("serial")
public final class FloatingPane extends JComponent {

	private final FloatingPaneModel floatingPaneModel;

	private Listener modelListener;

	private JComponent fadingBackgroundComponent;
	private JPanel foregroundPanel = new JPanel();

	private JLayeredPane layeredPane;
	private FloatingLayoutManager layoutManager;

	public FloatingPane(
			FloatingPaneModel floatingPaneModel) {
		this.floatingPaneModel = floatingPaneModel;

		initLayout();
		initLayeredPane();
		initLayers();
		installFloatingPaneModelListener();
	}

	private void initLayout() {
		setLayout(new BorderLayout(0,0));
		layoutManager = new FloatingLayoutManager();
	}

	private void initLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(layoutManager);
		add(layeredPane);
	}

	private void initLayers() {
		initFadingBackgroundComponent();
		layeredPane.add(fadingBackgroundComponent, new Integer(0));

		foregroundPanel.setLayout(new BorderLayout());
		layeredPane.add(foregroundPanel, new Integer(1));
	}
	
	private void initFadingBackgroundComponent() {
		fadingBackgroundComponent = new FadingComponent(
				new BackgroundFadingModel(), 
				getBackgroundComponent());
	}
	
	private JComponent getBackgroundComponent() {
		return floatingPaneModel.getBackgroundComponent();
	}
	
	private class BackgroundFadingModel implements FadingModel {
		
		public BackgroundFadingModel() {
			super();
		}

		public float getOpacity() {
			return floatingPaneModel.getBackgroundOpacity();
		}
		
		public boolean isFaded() {
			return floatingPaneModel.isBackgroundFaded();
		}
		
		public void addListener(Listener listener) {
			floatingPaneModel.addListener(listener);
		}
	}

	private void installFloatingPaneModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				relayout();
			}
		};
		floatingPaneModel.addListener(modelListener);
		relayout();
	}

	private void relayout() {
		layoutForeground();
		validate();
		repaint();
	}

	private void layoutForeground() {
		foregroundPanel.removeAll();

		if (isFloatingComponent()) {
			foregroundPanel.add(getFloatingComponent());
		}

		foregroundPanel.validate();
		foregroundPanel.repaint();
	}

	private boolean isFloatingComponent() {
		return getFloatingComponent()!=null;
	}

	private JComponent getFloatingComponent() {
		return floatingPaneModel.getFloatingComponent();
	}

	private class FloatingLayoutManager implements LayoutManager {


		public FloatingLayoutManager() {
			super();
		}

		public void removeLayoutComponent(Component comp) {
			return;
        }

        public void addLayoutComponent(String name, Component comp) {
        	return;
        }

        public Dimension minimumLayoutSize(Container parent) {
			return fadingBackgroundComponent.getMinimumSize();
		}

		public Dimension preferredLayoutSize(Container parent) {
			return fadingBackgroundComponent.getPreferredSize();
		}

        public void layoutContainer(Container parent) {
        	layoutFadingBackgroundComponent(parent);
        	layoutFloatingComponent();
        }

        private void layoutFadingBackgroundComponent(Container parent) {
        	updateFadingBackgroundBounds(parent);
        }

        private void updateFadingBackgroundBounds(Container parent) {
        	fadingBackgroundComponent.setBounds(parent.getBounds());
        }

        public void layoutFloatingComponent() {
        	updateForegroundBounds();
        }

        private void updateForegroundBounds() {
        	if (isFloatLocation()) {
	        	foregroundPanel.setBounds(new Rectangle(
	        			getFloatLocation(),
	        			foregroundPanel.getPreferredSize()));
        	}
        }

        private boolean isFloatLocation() {
        	return getFloatLocation()!=null;
        }

        private Point getFloatLocation() {
        	return floatingPaneModel.getFloatLocation();
        }
	}
}

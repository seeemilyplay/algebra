package seeemilyplay.algebra.components.fading;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
public final class FadingComponent extends JComponent {
	
	private final FadingModel fadingModel;
	private final JComponent component;
	private final InbetweenLayer inbetweenLayer;
	
	private Listener fadingModelListener;
	
	private JLayeredPane layeredPane;
	
	public FadingComponent(
			FadingModel fadingModel,
			JComponent component) {
		this.fadingModel = fadingModel;
		this.component = component;
		this.inbetweenLayer = new InbetweenLayer(
				fadingModel, 
				component);
		
		initLayout();
		initLayeredPane();
		initLayers();
		initFadingModelListener();
	}
	
	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void initLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(new LayeredLayoutManager());
		add(layeredPane);
	}

	private void initLayers() {
		layeredPane.add(component, new Integer(0));

		layeredPane.add(inbetweenLayer, new Integer(1));
	}
	
	private void initFadingModelListener() {
		fadingModelListener = new Listener() {
			public void onChange() {
				update();
			}
		};
		fadingModel.addListener(fadingModelListener);
		update();
	}
	
	private void update() {
		updateComponentVisibility();
		validate();
		repaint();
	}
	
	private void updateComponentVisibility() {
    	component.setVisible(isComponentVisible());
    }

    private boolean isComponentVisible() {
    	return !fadingModel.isFaded();
    }
	
	private class LayeredLayoutManager implements LayoutManager {

		
		public LayeredLayoutManager() {
			super();
		}
		
		public void addLayoutComponent(String name, Component comp) {
			return;
		}
		
		public void removeLayoutComponent(Component comp) {
			return;
		}

		public void layoutContainer(Container parent) {
			Rectangle bounds = parent.getBounds();
			component.setBounds(bounds);
			inbetweenLayer.setBounds(bounds);
		}

		public Dimension minimumLayoutSize(Container parent) {
			return component.getMinimumSize();
		}

		public Dimension preferredLayoutSize(Container parent) {
			return component.getPreferredSize();
		}
	}
}

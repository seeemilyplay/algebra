package seeemilyplay.algebra.components.floating;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import seeemilyplay.algebra.components.fading.FadingComponent;
import seeemilyplay.algebra.components.fading.FadingModel;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class FloatingComponent extends JComponent {

	private static final String BORDER_KEY = "FloatingComponent.border";
	private static final int PADDING = 20;

	private final FloatingPaneModel floatingPaneModel;

	private Listener modelListener;

	private JPanel innerPanel;

	public FloatingComponent(FloatingPaneModel floatingPaneModel) {
		this.floatingPaneModel = floatingPaneModel;

		initLayout();
		initModelListener();
		relayout();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		innerPanel = createInnerPanel();
		
		FadingComponent fadingComponent = new FadingComponent(
				new FloatFadingModel(),
				innerPanel);

		JPanel borderPanel = createBorderPanel();
		borderPanel.add(fadingComponent);

		this.add(borderPanel);
	}

	private JPanel createBorderPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(getPanelBorder());
		panel.setOpaque(true);
		return panel;
	}
	
	private Border getPanelBorder() {
		return BorderFactory.createCompoundBorder(
				UIManager.getBorder(BORDER_KEY),
				new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
	}
	
	private class FloatFadingModel implements FadingModel {
		
		public FloatFadingModel() {
			super();
		}
		
		public float getOpacity() {
			return floatingPaneModel.getFloatOpacity();
		}
		
		public boolean isFaded() {
			return floatingPaneModel.isFloatFaded();
		}

		public void addListener(Listener listener) {
			floatingPaneModel.addListener(listener);
		}
	}

	private JPanel createInnerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setOpaque(true);
		return panel;
	}

	private void initModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				relayout();
			}
		};
		floatingPaneModel.addListener(modelListener);
	}

	private void relayout() {
		innerPanel.removeAll();

		innerPanel.setPreferredSize(getFloatSize());

		if (isNakedFloatingComponent()) {
			innerPanel.add(getNakedFloatingComponent());
		}
		
		setVisible(isNakedFloatingComponent());

		validate();
		repaint();
	}
	
	private boolean isNakedFloatingComponent() {
		return (getNakedFloatingComponent()!=null 
				&& getNakedFloatingComponentWidth()>0 
				&& getNakedFloatingComponentHeight()>0);
	}
	
	private int getNakedFloatingComponentHeight() {
		return getNakedFloatingComponentSize().height;
	}
	
	private int getNakedFloatingComponentWidth() {
		return getNakedFloatingComponentSize().width;
	}
	
	private Dimension getNakedFloatingComponentSize() {
		return getNakedFloatingComponent().getPreferredSize(); 
	}
	
	private JComponent getNakedFloatingComponent() {
		return floatingPaneModel.getNakedFloatingComponent();
	}

	private Dimension getFloatSize() {
		return floatingPaneModel.getFloatSize();
	}
}

package seeemilyplay.algebra.components.fading;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import seeemilyplay.core.listeners.Listener;


@SuppressWarnings("serial")
final class InbetweenLayer extends JComponent {

	private final FadingModel fadingModel;
	private final JComponent component;
	
	private Listener fadingModelListener;

	public InbetweenLayer(
			FadingModel fadingModel,
			JComponent component) {
		this.fadingModel = fadingModel;
		this.component = component;

		initLayout();
		initFadingModelListener();
	}

	private void initLayout() {
		setLayout(new BorderLayout(0,0));
		setOpaque(true);
		JPanel panel = new JPanel();
		add(panel);
	}

	private void initFadingModelListener() {
		fadingModelListener = new Listener() {
			public void onChange() {
				updateVisibility();
			}
		};
		fadingModel.addListener(fadingModelListener);
		updateVisibility();
	}
	
	private void updateVisibility() {
		setVisible(isFaded());
	}

	private boolean isFaded() {
		return fadingModel.isFaded();
	}

	public void paint(Graphics g) {
		super.paint(g);

		if (isVisible()) {
			paintFadedBackground(g);
		}
	}

	private void paintFadedBackground(Graphics g) {
		Graphics fadedGraphics = createFadedGraphics(g);
		setBackgroundDoubleBuffering(false);
		try {
			component.paint(fadedGraphics);
		} finally {
			setBackgroundDoubleBuffering(true);
		}
	}

	private void setBackgroundDoubleBuffering(boolean doubleBuffer) {
		getBackgroundRepaintManager().setDoubleBufferingEnabled(doubleBuffer);
	}

	private RepaintManager getBackgroundRepaintManager() {
		return RepaintManager.currentManager(component);
	}

	private Graphics createFadedGraphics(Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setComposite(getAlphaComposite());
		return g2;
	}

	private AlphaComposite getAlphaComposite() {
		return AlphaComposite.getInstance(
        		AlphaComposite.SRC_OVER,
        		getOpacity());
	}

	private float getOpacity() {
		return fadingModel.getOpacity();
	}
}

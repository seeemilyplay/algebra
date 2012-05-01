package seeemilyplay.algebra.components.floating;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RepaintManager;


@SuppressWarnings("serial")
final class InbetweenLayer extends JComponent {

	private final FloatingPaneModel floatingPaneModel;


	public InbetweenLayer(FloatingPaneModel floatingPaneModel) {
		this.floatingPaneModel = floatingPaneModel;

		initLayout();
	}

	private void initLayout() {
		setLayout(new BorderLayout(0,0));
		setOpaque(true);
		JPanel panel = new JPanel();
		add(panel);
	}

	public boolean isVisible() {
		return isBackgroundFaded();
	}

	private boolean isBackgroundFaded() {
		return floatingPaneModel.isBackgroundFaded();
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
			getBackgroundComponent().paint(fadedGraphics);
		} finally {
			setBackgroundDoubleBuffering(true);
		}
	}

	private void setBackgroundDoubleBuffering(boolean doubleBuffer) {
		getBackgroundRepaintManager().setDoubleBufferingEnabled(doubleBuffer);
	}

	private RepaintManager getBackgroundRepaintManager() {
		return RepaintManager.currentManager(getBackgroundComponent());
	}

	private Graphics createFadedGraphics(Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setComposite(getAlphaComposite());
		return g2;
	}

	private AlphaComposite getAlphaComposite() {
		return AlphaComposite.getInstance(
        		AlphaComposite.SRC_OVER,
        		getBackroundOpacity());
	}

	private float getBackroundOpacity() {
		return floatingPaneModel.getBackgroundOpacity();
	}

	private JComponent getBackgroundComponent() {
		return floatingPaneModel.getBackgroundComponent();
	}
}

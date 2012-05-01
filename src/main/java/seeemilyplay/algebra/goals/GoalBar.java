package seeemilyplay.algebra.goals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class GoalBar extends JComponent {

	private static final int HEIGHT = 200;
	private static final int WIDTH = 50;

	private final GoalBarModel goalBarModel;
	private final BoundsCalculator boundsCalculator;

	private Listener listener;

	public GoalBar(GoalBarModel goalBarModel) {
		this.goalBarModel = goalBarModel;
		this.boundsCalculator = new BoundsCalculator(this);

		initLayout();
		initModelListener();
		repaint();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	private void initModelListener() {
		listener = new Listener() {
			public void onChange() {
				repaint();
			}
		};
		goalBarModel.addListener(listener);
	}
	
	public BoundsCalculator getBoundsCalculator() {
		return boundsCalculator;
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		paintProgress(g);
	}
	
	private void paintProgress(Graphics g) {
		drawRectangle(
				createProgressGraphics(g),
				getProgressBounds());
	}
	
	private void drawRectangle(Graphics g, Rectangle bounds) {
		g.drawRect(
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height);
		g.fillRect(
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height);
	}
	
	private Graphics createProgressGraphics(Graphics g) {
		Graphics g2 = g.create();
		g2.setColor(getProgressColor());
		return g2;
	}

	private Color getProgressColor() {
		AlgebraTheme theme = getTheme();
		return theme.getWarningPaper();
	}
	
	private AlgebraTheme getTheme() {
		return AlgebraTheme.getInstance();
	}
	
	private Rectangle getProgressBounds() {
		return boundsCalculator.getBounds(
				this, 
				getProgress());
	}
	
	private double getProgress() {
		return goalBarModel.getProgress();
	}
}

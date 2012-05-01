package seeemilyplay.algebra.goals;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
public final class GoalComponent extends JComponent {
	
	private static final int RIGHT_HAND_PADDING = 50;

	private final GoalModel goalModel;
	private final GoalBarModel goalBarModel;
	private final GoalBar goalBar;
	
	private Listener listener;

	public GoalComponent(GoalModel goalModel) {
		this.goalModel = goalModel;
		this.goalBarModel = new GoalBarModel(goalModel);
		this.goalBar = new GoalBar(goalBarModel);

		initLayout();
		layoutGoalBar();
		layoutSpacing();
		initModelListener();
		repaint();
	}

	private void initLayout() {
		setLayout(new FlowLayout());
	}
	
	private void layoutGoalBar() {
		JPanel goalPanel = new JPanel();
		goalPanel.setLayout(new BorderLayout());
		goalPanel.setBorder(getProgressBorder());
		goalPanel.add(goalBar);
		add(goalPanel);
	}
	
	private void layoutSpacing() {
		add(Box.createHorizontalStrut(RIGHT_HAND_PADDING));
	}
	
	private Border getProgressBorder() {
		AlgebraTheme theme = getTheme();
		return theme.getDrawnBorder();
	}
	
	private AlgebraTheme getTheme() {
		return AlgebraTheme.getInstance();
	}

	private void initModelListener() {
		listener = new Listener() {
			public void onChange() {
				repaint();
			}
		};
		goalBarModel.addListener(listener);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for (Goal goal : goalModel.getGoals()) {
			drawGoal(g, goal);
		}
	}
	
	private void drawGoal(Graphics g, Goal goal) {
		drawMarker(g, goal);
		drawIcon(g, goal);
	}
	
	private void drawMarker(Graphics g, Goal goal) {
		Rectangle markerBounds = getMarkerBounds(goal);
		g.drawLine(
				markerBounds.x,
				markerBounds.y,
				(int)markerBounds.getMaxX(),
				(int)markerBounds.getMaxY());
	}
	
	private Rectangle getMarkerBounds(Goal goal) {
		return getBoundsCalculator().getMarkerBounds(this, goal);
	}
	
	private void drawIcon(Graphics g, Goal goal) {
		Icon icon = getIcon(goal);
		Point iconLocation = getIconLocation(goal, icon);
		icon.paintIcon(
				this, 
				g, 
				iconLocation.x, 
				iconLocation.y);
	}
	
	private Point getIconLocation(Goal goal, Icon icon) {
		return getBoundsCalculator().getIconLocation(this, goal, icon);
	}
	
	private Icon getIcon(Goal goal) {
		Award award = goal.getAward();
		return award.getSmallIcon();
	}
	
	private BoundsCalculator getBoundsCalculator() {
		return goalBar.getBoundsCalculator();
	}
}

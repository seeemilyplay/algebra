package seeemilyplay.algebra.goals;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.SwingUtilities;

final class BoundsCalculator {

	private static final int TICK_LENGTH = 10;
	private static final int PADDING = 5;
	private static final int MARKER_PADDING = 2;
	
	private final GoalBar goalBar;
	
	public BoundsCalculator(GoalBar goalBar) {
		this.goalBar = goalBar;
	}
	
	public Point getIconLocation(Component target, Goal goal, Icon icon) {
		Rectangle markerBounds = getMarkerBounds(target, goal);
		int x = (int)(markerBounds.getMaxX() + PADDING);
		int y = (int)(markerBounds.getMaxY() - icon.getIconWidth() / 2.0);
		return new Point(x, y);
	}
	
	public Rectangle getMarkerBounds(Component target, Goal goal) {
		double progress = getProgress(goal);
		Rectangle bounds = getBounds(target, progress);
		return new Rectangle(
				bounds.x, 
				bounds.y - MARKER_PADDING,
				bounds.width + TICK_LENGTH,
				0);
	}
	
	private double getProgress(Goal goal) {
		return goal.getProgress();
	}
	
	public Rectangle getBounds(Component target, double progress) {  
		return new Rectangle(
				getLocation(target, progress), 
				getSize(progress));
	}
	
	private Dimension getSize(double progress) {
		return new Dimension(
				getWidth(),
				getHeight(progress));
	}
	
	private Point getLocation(Component target, double progress) {
		Point location = getLocation(progress);
		return SwingUtilities.convertPoint(goalBar, location, target);
	}
	
	private Point getLocation(double progress) {
		return new Point(
				getX(),
				getY(progress));
	}
	
	private int getY(double progress) {
		return getHeight() - getHeight(progress);
	}
	
	private int getHeight(double progress) {
		return (int)(progress * getHeight());
	}
	
	private int getX() {
		return getLocation().x;
	}
	
	private Point getLocation() {
		return goalBar.getLocation();
	}
	
	private int getWidth() {
		return getSize().width;
	}
	
	private int getHeight() {
		return getSize().height;
	}
	
	private Dimension getSize() {
		return goalBar.getPreferredSize();
	}
}

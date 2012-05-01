package seeemilyplay.algebra.components.arrow;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import seeemilyplay.core.swing.SwingModel;

public final class ArrowPaneModel extends SwingModel {

	private Rectangle attentionArea;
	private ArrowDirection arrowDirection;

	public ArrowPaneModel() {
		super();
	}

	public void setAttention(ArrowDirection arrowDirection, JComponent component) {
		setAttention(
				arrowDirection,
				component.getParent(),
				component.getBounds());
	}

	public void setAttention(
			ArrowDirection arrowDirection,
			Component component,
			Rectangle focusArea) {
		this.arrowDirection = arrowDirection;
		setAttentionArea(component, focusArea);
		fireChange();
	}

	private void setAttentionArea(
			Component component,
			Rectangle focusArea) {

		Point screenLocation = new Point(focusArea.getLocation());
		SwingUtilities.convertPointToScreen(
				screenLocation,
				component);
		attentionArea = new Rectangle(
				screenLocation,
				focusArea.getSize());
	}

	public void clearAttention() {
		attentionArea = null;
		fireChange();
	}

	public boolean isAttentionArea() {
		return attentionArea!=null;
	}

	public Rectangle getAttentionArea() {
		return attentionArea;
	}

	public ArrowDirection getArrowDirection() {
		return arrowDirection;
	}
}

package seeemilyplay.algebra.lookandfeel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * A custom button UI for the Algebra project.
 */
public final class AlgebraButtonUI extends BasicButtonUI {

    private Color originalBackground;
    private Color originalForeground;

    private boolean isRollover;
    private boolean isDisabled;

	public AlgebraButtonUI() {
		super();
	}

	public static ComponentUI createUI(JComponent c) {
        return new AlgebraButtonUI();
    }

	public void installUI(JComponent c) {
		super.installUI(c);
		c.setFocusable(false);
		AlgebraTheme theme = AlgebraTheme.getInstance();

		c.setBackground(theme.getHighlightPaper());
    	c.setForeground(theme.getHighlightInk());
	}

	protected void installListeners(AbstractButton b) {
		super.installListeners(b);
		b.addMouseListener(new RolloverListener(b));
		b.addMouseMotionListener(new RolloverListener(b));
	}

	private void recordOriginalColor(JComponent c) {
		originalBackground = c.getBackground();
		originalForeground = c.getForeground();
	}

	public void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        if (!model.isEnabled()) {
        	if (!isDisabled) {
        		changeToDisabledColors(c);
        	}
        } else if (model.isRollover() || isMouseOver(b)) {
        	if (!isRollover) {
        		changeToRolloverColors(c);
        	}
        } else if (isDisabled || isRollover){
        	changeToOriginalColors(c);
        }
		super.paint(g, c);
	}

	private void changeToDisabledColors(JComponent c) {
		recordOriginalColor(c);
		setDisabledColors(c);
		isDisabled = true;
		isRollover = false;
	}

	private void changeToRolloverColors(JComponent c) {
		recordOriginalColor(c);
		setRolloverColors(c);
		isDisabled = false;
		isRollover = true;
	}

	private void changeToOriginalColors(JComponent c) {
		setOriginalColors(c);
		isDisabled = false;
		isRollover = false;
	}

	private void setDisabledColors(JComponent c) {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		c.setBackground(theme.getDisabledPaper());
    	c.setForeground(theme.getDisabledInk());
	}

	private void setOriginalColors(JComponent c) {
		c.setBackground(originalBackground);
    	c.setForeground(originalForeground);
	}

	private void setRolloverColors(JComponent c) {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		c.setBackground(theme.getRolloverPaper());
    	c.setForeground(theme.getRolloverInk());
	}

	private class RolloverListener extends MouseAdapter {

		private final AbstractButton b;

		public RolloverListener(AbstractButton b) {
			this.b = b;
		}

		public void mouseEntered(MouseEvent e) {
			mouseMoved(e);
		}

		public void mouseExited(MouseEvent e) {
			mouseMoved(e);
		}

		public void mouseMoved(MouseEvent e) {
			boolean isRollover = isMouseOver(b);
			if (AlgebraButtonUI.this.isRollover!=isRollover) {
				b.repaint();
			}
		}
	}

	private static boolean isMouseOver(JComponent c) {
		Rectangle componentBounds = c.getBounds();
		Point mousePosition = c.getMousePosition();
		return (componentBounds!=null
    			&& mousePosition!=null
    			&& componentBounds.contains(mousePosition));
	}
}

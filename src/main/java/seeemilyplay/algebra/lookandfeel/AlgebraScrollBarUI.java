package seeemilyplay.algebra.lookandfeel;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;

import net.sourceforge.napkinlaf.NapkinScrollBarUI;
import net.sourceforge.napkinlaf.NapkinTheme;

/**
 * A custom Scroll Bar UI for the Algebra project.
 */
public final class AlgebraScrollBarUI extends NapkinScrollBarUI {

    public AlgebraScrollBarUI(JScrollBar bar) {
		super(bar);
	}

	public static ComponentUI createUI(JComponent c) {
        return new AlgebraScrollBarUI((JScrollBar)c);
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBackground(AlgebraTheme.getInstance().getPaper());
        c.setOpaque(true);
    }

    protected JButton createDecreaseButton(int orientation) {
        JButton decreaseButton = super.createDecreaseButton(orientation);
        decreaseButton.setOpaque(false);
        return decreaseButton;
    }

    protected JButton createIncreaseButton(int orientation) {
        JButton increaseButton = super.createIncreaseButton(orientation);
        increaseButton.setOpaque(false);
        return increaseButton;
    }

	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		super.paintThumb(g, c, thumbBounds);
	}

	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		super.paintTrack(g, c, trackBounds);
	}

	public void superPaint(Graphics g, JComponent c, NapkinTheme theme) {
		super.superPaint(g, c, theme);
	}

	public void update(Graphics g, JComponent c) {
		super.update(g, c);
	}
}
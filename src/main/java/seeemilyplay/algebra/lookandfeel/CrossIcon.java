package seeemilyplay.algebra.lookandfeel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.Icon;

import net.sourceforge.napkinlaf.shapes.DrawnBoxGenerator;
import net.sourceforge.napkinlaf.shapes.DrawnBoxHolder;
import net.sourceforge.napkinlaf.util.NapkinConstants;
import net.sourceforge.napkinlaf.util.NapkinUtil;

/**
 * A cross icon.
 */
final class CrossIcon implements Icon {

	private final int size;
    private final DrawnBoxHolder mark;

    public CrossIcon(int size) {
        DrawnBoxGenerator box = new DrawnBoxGenerator();
        box.setAsX(true);
        mark = new DrawnBoxHolder(box);
        this.size = size;
    }

    public int getIconHeight() {
        return size;
    }

    public int getIconWidth() {
        return size;
    }

    public void paintIcon(Component c, Graphics g1, int x, int y) {
    	g1.setColor(AlgebraTheme.getInstance().getInk());
        Graphics2D g = NapkinUtil
                .lineGraphics(g1, NapkinConstants.CHECK_WIDTH);
        mark.shapeUpToDate(new Rectangle(x, y, size, size));
        g.translate(x, y);
        mark.draw(g);
        g.translate(-x, -y);
    }
}

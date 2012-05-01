package seeemilyplay.algebra.lookandfeel;

import java.awt.Component;
import java.awt.geom.AffineTransform;

import net.sourceforge.napkinlaf.NapkinThemeColor;
import net.sourceforge.napkinlaf.shapes.AbstractDrawnGenerator;
import net.sourceforge.napkinlaf.shapes.DrawnTriangleGenerator;
import net.sourceforge.napkinlaf.util.AbstractNapkinIcon;

/**
 * An arrow icon.
 */
final class ArrowIcon extends AbstractNapkinIcon {

    private final int genNum;
    private final int size;

    public static final int DEFAULT_SIZE = 10;

    private static final DrawnTriangleGenerator[] ARROW_GEN = {
            new DrawnTriangleGenerator(0),
            new DrawnTriangleGenerator(Math.PI / 2),
            new DrawnTriangleGenerator(Math.PI),
            new DrawnTriangleGenerator(-Math.PI / 2),
    };

    /** @param pointTowards One of NORTH, EAST, WEST, or SOUTH. */
    public ArrowIcon(int pointTowards, int size) {
        super(NapkinThemeColor.CHECK_COLOR, scaleMat(size));
        genNum = pointTowards / 2;
        this.size = size;
        init();
    }

    private static AffineTransform scaleMat(double scale) {
        AffineTransform mat = new AffineTransform();
        mat.scale(scale, scale);
        return mat;
    }

    protected AbstractDrawnGenerator createPlaceGenerator() {
        return ARROW_GEN[genNum];
    }

    protected AbstractDrawnGenerator createMarkGenerator() {
        return ARROW_GEN[genNum];
    }

    protected int calcHeight() {
        return size;
    }

    protected int calcWidth() {
        return size;
    }

    protected boolean shouldUseMark(Component c) {
        return false;
    }
}

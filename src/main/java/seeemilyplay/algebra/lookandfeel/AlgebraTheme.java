package seeemilyplay.algebra.lookandfeel;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sourceforge.napkinlaf.NapkinTheme;
import net.sourceforge.napkinlaf.borders.NapkinBoxBorder;
import net.sourceforge.napkinlaf.borders.NapkinLineBorder;
import net.sourceforge.napkinlaf.util.NapkinIconFactory;

/**
 * Stores settings used in the <code>AlgebraLookAndFeel</code>.
 */
public final class AlgebraTheme {

	private static final String POINTER_SE_TEMPLATE = "/seeemilyplay/algebra/lookandfeel/pointer_SE.xml";
	private static final String POINTER_SW_TEMPLATE = "/seeemilyplay/algebra/lookandfeel/pointer_SW.xml";
	private static final String POINTER_N_TEMPLATE = "/seeemilyplay/algebra/lookandfeel/pointer_N.xml";
	private static final String POINTER_NE_TEMPLATE = "/seeemilyplay/algebra/lookandfeel/pointer_NE.xml";
	private static final String POINTER_NW_TEMPLATE = "/seeemilyplay/algebra/lookandfeel/pointer_NW.xml";

	private static final Color LIGHT_ORANGE = new Color(255, 255, 225);
	private static final Color LIGHT_PURPLE = new Color(255, 235, 225);
	private static final Color LIGHT_GREEN = new Color(212, 255, 247);
	private static final Color LIGHT_RED = new Color(255, 195, 162);
	private static final Color LIGHT_BLUE = new Color(94, 7, 255);
	private static final Color BLACK = new Color(0, 0, 0);
	private static final Color LIGHT_GREY = new Color(225, 225, 225);
	private static final Color GREY = new Color(90, 90, 90);
	private static final Color RED = new Color(255, 0, 0);

	private static final AlgebraTheme instance = new AlgebraTheme();

	private static final Border drawnBorder = new CachedBorder(
			BorderFactory.createCompoundBorder(
					new NapkinBoxBorder(),
					BorderFactory.createEmptyBorder(2,2,4,4)));
	private static final Border underlineBorder = new CachedBorder(
			BorderFactory.createCompoundBorder(
		    		new NapkinLineBorder(false),
		    		BorderFactory.createEmptyBorder(3,5,3,5)));

	private final Font boldTextFont;
	private final Font smallTextFont;
	private final Font textFont;

	private final Icon exitIcon;
	private final Icon errorIcon;
	private final Icon backIcon;
	private final Icon forwardIcon;

	private final NapkinTheme defaultTheme;

	private final TemplateIconCache templateIconCache;


	private AlgebraTheme() {
		super();
		defaultTheme =
			NapkinTheme.Manager.getCurrentTheme();
		templateIconCache = new TemplateIconCache(defaultTheme);

		boldTextFont = defaultTheme.getBoldTextFont().deriveFont(30f);
		smallTextFont = defaultTheme.getBoldTextFont().deriveFont(20f);
		textFont = defaultTheme.getTextFont().deriveFont(30f);

		exitIcon = new CrossIcon(20);
		errorIcon = NapkinIconFactory.createSketchedIcon("Error");
		backIcon = new ArrowIcon(SwingConstants.WEST, 20);
		forwardIcon = new ArrowIcon(SwingConstants.EAST, 20);
	}

	public static AlgebraTheme getInstance() {
		return instance;
	}

	public Icon getTemplateIcon(URL url) {
		return templateIconCache.getIcon(url);
	}

	public Color getPaper() {
		return LIGHT_ORANGE;
	}

	public Color getInk() {
		return BLACK;
	}

	public Color getDisabledPaper() {
		return LIGHT_GREY;
	}

	public Color getDisabledInk() {
		return GREY;
	}

	public Color getShadowPaper() {
		return GREY;
	}

	public Color getShadowInk() {
		return GREY;
	}

	public Color getErrorInk() {
		return RED;
	}

	public Color getHighlightPaper() {
		return LIGHT_PURPLE;
	}

	public Color getHighlightInk() {
		return BLACK;
	}

	public Color getWarningPaper() {
		return LIGHT_RED;
	}

	public Color getPopColor() {
		return LIGHT_BLUE;
	}

	public Color getRolloverPaper() {
		return LIGHT_GREEN;
	}

	public Color getRolloverInk() {
		return BLACK;
	}

	public Font getBoldTextFont() {
		return boldTextFont;
	}

	public Font getTextFont() {
		return textFont;
	}

	public Font getSmallTextFont() {
		return smallTextFont;
	}

	public Border getDrawnBorder() {
		return drawnBorder;
	}

	public Border getUnderlineBorder() {
		return underlineBorder;
	}

	public Icon getExitIcon() {
		return exitIcon;
	}

	public Icon getErrorIcon() {
		return errorIcon;
	}

	public Icon getCrossIcon() {
		return new CrossIcon(12);
	}

	public Icon getBackIcon() {
		return backIcon;
	}

	public Icon getForwardIcon() {
		return forwardIcon;
	}

	public Icon getPointerSE() {
		return getTemplateIcon(
				AlgebraTheme.class.getResource(POINTER_SE_TEMPLATE));
	}

	public Icon getPointerSW() {
		return getTemplateIcon(
				AlgebraTheme.class.getResource(POINTER_SW_TEMPLATE));
	}

	public Icon getPointerN() {
		return getTemplateIcon(
				AlgebraTheme.class.getResource(POINTER_N_TEMPLATE));
	}

	public Icon getPointerNE() {
		return getTemplateIcon(
				AlgebraTheme.class.getResource(POINTER_NE_TEMPLATE));
	}

	public Icon getPointerNW() {
		return getTemplateIcon(
				AlgebraTheme.class.getResource(POINTER_NW_TEMPLATE));
	}
}

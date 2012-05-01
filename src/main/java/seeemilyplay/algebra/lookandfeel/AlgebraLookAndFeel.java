package seeemilyplay.algebra.lookandfeel;

import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.border.Border;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * A custom <code>LookAndFeel</code> for the Algebra project.
 */
@SuppressWarnings("serial")
public final class AlgebraLookAndFeel extends WindowsLookAndFeel {

	private static final String ID = "Algebra";
	private static final String NAME = "Algebra";
	private static final String DESCRIPTION =
		"Look and Feel created for the Algebra project";

	private Object drawnBorder;
	private Object emptyBorder;
	private Object underlineBorder;

	public AlgebraLookAndFeel() {
		super();
		setup();
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return NAME;
	}

	public String getDescription() {
		return DESCRIPTION;
	}

	public boolean isNativeLookAndFeel() {
		return false;
	}

	public boolean isSupportedLookAndFeel() {
		return true;
	}

	private void setup() {
		setupDrawnBorder();
		setupEmptyBorder();
		setupUnderlineBorder();
	}

	private void setupDrawnBorder() {
		drawnBorder = createDrawnBorderActiveValue();
	}

	private Object createDrawnBorderActiveValue() {
		return new UIDefaults.ActiveValue() {
            public Object createValue(UIDefaults table) {
            	return createDrawnBorder();
            }
        };
	}

	private Border createDrawnBorder() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
        return theme.getDrawnBorder();
	}

	private void setupEmptyBorder() {
		emptyBorder = createEmptyBorderActiveValue();
	}

	private Object createEmptyBorderActiveValue() {
		return new UIDefaults.ActiveValue() {
            public Object createValue(UIDefaults table) {
            	return createEmptyBorder();
            }
        };
	}

	private Border createEmptyBorder() {
		return BorderFactory.createEmptyBorder(0, 0, 0, 0);
	}

	private void setupUnderlineBorder() {
		underlineBorder = createUnderlineBorderActiveValue();
	}

	private Object createUnderlineBorderActiveValue() {
		return new UIDefaults.ActiveValue() {
            public Object createValue(UIDefaults table) {
            	return createUnderlineBorder();
            }
        };
	}

	private Border createUnderlineBorder() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
        return theme.getUnderlineBorder();
	}

	protected void initComponentDefaults(UIDefaults table) {
		super.initComponentDefaults(table);

		AlgebraTheme theme = AlgebraTheme.getInstance();

		Object[] defaults = new Object[] {
				"ButtonUI", "seeemilyplay.algebra.lookandfeel.AlgebraButtonUI",
				"ScrollBarUI", "seeemilyplay.algebra.lookandfeel.AlgebraScrollBarUI",
				"Panel.background", theme.getPaper(),
				"Panel.border", emptyBorder,
				"ToolBar.background", theme.getPaper(),
				"TextField.background", theme.getPaper(),
				"TextField.foreground", theme.getInk(),
				"TextField.selectionBackground", theme.getHighlightPaper(),
				"TextField.selectionForeground", theme.getHighlightInk(),
				"TextField.border", drawnBorder,
				"TextField.font", theme.getTextFont(),
				"TextArea.background", theme.getPaper(),
				"TextArea.foreground", theme.getInk(),
				"TextArea.font", theme.getTextFont(),
				"Label.foreground", theme.getInk(),
				"Label.font", theme.getBoldTextFont(),
				"Label.errorForeground", theme.getErrorInk(),
				"Button.background", theme.getHighlightPaper(),
				"Button.foreground", theme.getInk(),
				"Button.rollover", true,
				"Button.rolloverEnabled", true,
				"Button.font", theme.getBoldTextFont(),
				"Button.border", drawnBorder,
				"Button.warningBackround", theme.getWarningPaper(),
				"Label.font", theme.getBoldTextFont(),
				"List.background", theme.getPaper(),
				"ScrollPane.border", emptyBorder,
				"Viewport.background", theme.getPaper(),
				"Icon.back", theme.getBackIcon(),
				"Icon.error", theme.getErrorIcon(),
				"Icon.exit", theme.getExitIcon(),
				"FloatingComponent.border", drawnBorder,
				"Hyperlink.mouseOverBorder", underlineBorder
		};

		table.putDefaults(defaults);
	}
}
package seeemilyplay.core.components;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * A hyper link style component.
 */
@SuppressWarnings("serial")
public final class Hyperlink extends JComponent {

	private static final String BORDER_KEY = "Hyperlink.mouseOverBorder";

	private final Action action;

	private JLabel linkLabel;

	private final JPanel innerPanel = new JPanel();

	public Hyperlink(Action action) {
		this.action = action;

		createLinkLabel();
		setupFunctionality();
		layoutComponent();
	}

	private void createLinkLabel() {
		linkLabel = new JLabel(getText());
	}

	private String getText() {
		return (String)action.getValue(Action.NAME);
	}

	private void setupFunctionality() {
		setupClicking();
		setupHoverFormatting();
	}

	private void setupClicking() {
		ClickListener clickListener = new ClickListener();
		linkLabel.addMouseListener(clickListener);
	}

	private void setupHoverFormatting() {
		HoverFormatter hoverFormatter = new HoverFormatter();
		linkLabel.addMouseListener(hoverFormatter);
	}

	private void layoutComponent() {
		this.setLayout(new BorderLayout());
		this.setBorder(getDefaultBorder());
		layoutInnerPanel();
	}

	private Border getDefaultBorder() {
		return BorderFactory.createEmptyBorder(4, 5, 5, 5);
	}

	private void layoutInnerPanel() {
		innerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.add(innerPanel);
		innerPanel.add(linkLabel);
	}

	public void setOpaque(boolean isOpaque) {
		super.setOpaque(isOpaque);
		innerPanel.setOpaque(isOpaque);
	}

	private Cursor getMouseOverCursor() {
		return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}

	private Border getMouseOverBorder() {
		return UIManager.getBorder(BORDER_KEY);
	}

	private class ClickListener extends MouseAdapter {

		public ClickListener() {
			super();
		}

		public void mousePressed(MouseEvent e) {
			performAction();
		}

		private void performAction() {
			action.actionPerformed(null);
		}
	}

	private class HoverFormatter extends MouseAdapter {

		private final FormatSaver formatSaver = new FormatSaver();

		public HoverFormatter() {
			super();
		}

		public void mouseEntered(MouseEvent e) {
			saveCurrentFormat();
			formatMouseOver();
		}

		private void saveCurrentFormat() {
			formatSaver.save();
		}

		private void formatMouseOver() {
			setMouseOverCursor();
			setMouseOverBorder();
		}

		private void setMouseOverCursor() {
			setCursor(getMouseOverCursor());
		}

		private void setMouseOverBorder() {
			setBorder(getMouseOverBorder());
		}

		public void mouseExited(MouseEvent e) {
			formatMouseNotOver();
		}

		private void formatMouseNotOver() {
			restoreSavedFormat();
		}

		private void restoreSavedFormat() {
			formatSaver.restore();
		}
	}

	private class FormatSaver {

		private Cursor cursor;
		private Border border;

		public FormatSaver() {
			save();
		}

		public void save() {
			recordCursor();
			recordBorder();
		}

		private void recordCursor() {
			cursor = getCursor();
		}

		private void recordBorder() {
			border = getBorder();
		}

		public void restore() {
			restoreCursor();
			restoreBorder();
		}

		private void restoreCursor() {
			setCursor(cursor);
		}

		private void restoreBorder() {
			setBorder(border);
		}
	}
}
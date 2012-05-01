package seeemilyplay.core.components;

import java.awt.Dimension;
import java.awt.Font;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;

/**
 * This component displays label text on multiple
 * lines without the use of HTML.
 */
@SuppressWarnings("serial")
public class MultiLineLabel extends JComponent {

	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";

	private static AlgebraTheme theme = AlgebraTheme.getInstance();
	private static final Font FONT = theme.getTextFont();

	private final String text;
	private final int width;
	private final Icon icon;

	private boolean isIconLayedOut;

	public MultiLineLabel(String text, int width) {
		this(text, width, null);
	}

	public MultiLineLabel(String text, int width, Icon icon) {
		this.text = text;
		this.width = width;
		this.icon = icon;

		initLayout();
		layoutTextArea();
	}

	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		return new Dimension(width, preferredSize.height);
	}

	private void initLayout() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	private void layoutTextArea() {

		LineLayer lineLayer = new LineLayer();
		lineLayer.lay();
	}

	private void layoutNewLine() {
		int halfLineHeight = getLineHeight() / 2;
		add(Box.createVerticalStrut(halfLineHeight));
	}

	private int getLineHeight() {
		JLabel lineLabel = createLineLabel(" ");
		return lineLabel.getPreferredSize().height;
	}

	private void layoutLine(String line) {
		JLabel lineLabel = createLineLabel(line);
		add(lineLabel);
		flagSoNoIconsFromNowOn();
	}

	private JLabel createLineLabel(String lineText) {
		Icon icon = (isIconRequired()) ? this.icon : null;
		JLabel label = new JLabel(lineText, icon, JLabel.LEADING);
		label.setFont(FONT);
		return label;
	}

	private boolean isIconRequired() {
		return icon!=null && !isIconLayedOut;
	}

	private void flagSoNoIconsFromNowOn() {
		isIconLayedOut = true;
	}

	private class LineLayer {

		private StringBuilder line = new StringBuilder();

		private String token;

		public LineLayer() {
			super();
		}

		public void lay() {
			StringTokenizer tokenizer = new StringTokenizer(
					text,
					getDelims(),
					true);
			while (tokenizer.hasMoreTokens()) {
				process(tokenizer.nextToken());
			}
			layCurrentLine();
			layNewLine();
		}

		private void process(String token) {
			init(token);
			if (isNewline()) {
				layCurrentLine();
				layNewLine();
			} else if (fitsOnLine()) {
				addOnLine();
			} else {
				layCurrentLine();
				addOnLine();
			}
		}

		private void init(String token) {
			this.token = token;
		}

		private String getDelims() {
			return NEW_LINE + SPACE;
		}

		private boolean isNewline() {
			return NEW_LINE.equals(token);
		}

		private void layCurrentLine() {
			String line = getLineText();
			clearLine();
			if (line.length()>0) {
				layoutLine(line);
			}

		}

		private String getLineText() {
			return getText(line);
		}

		private void clearLine() {
			line = new StringBuilder();
		}

		private void layNewLine() {
			layoutNewLine();
		}

		private boolean fitsOnLine() {
			StringBuilder potentialLine = new StringBuilder(line);
			potentialLine.append(token);
			String potentialLineText = getText(potentialLine);
			return fits(potentialLineText);
		}

		private boolean fits(String text) {
			JLabel lineLabel = createLineLabel(text);
			return lineLabel.getPreferredSize().width<width;
		}

		private void addOnLine() {
			line.append(token);
		}

		private String getText(StringBuilder sb) {
			return sb.toString().trim();
		}
	}
}

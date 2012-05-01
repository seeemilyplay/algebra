package seeemilyplay.algebra.sequences.crossword;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;

@SuppressWarnings("serial")
final class GivenSquareComponent extends JComponent {

	private final SquareModel squareModel;
	private CenteringLayoutManager centeringLayoutManager;

	public GivenSquareComponent(SquareModel squareModel) {
		this.squareModel = squareModel;

		initLayout();
		layoutAll();
	}

	private void initLayout() {
		centeringLayoutManager = new CenteringLayoutManager();
		setLayout(centeringLayoutManager);
	}

	private void layoutAll() {
		JLabel valueLabel = createValueLabel();

		add(valueLabel);
		centeringLayoutManager.setComponent(valueLabel);

		validate();
		repaint();
	}

	private JLabel createValueLabel() {
		return new JLabel(getValueText());
	}

	private String getValueText() {
		return Integer.toString(getValue());
	}

	private int getValue() {
		return getSquare().getValue();
	}

	private Square getSquare() {
		return squareModel.getSquare();
	}

	public Border getBorder() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getDrawnBorder();
	}
}

package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;

@SuppressWarnings("serial")
final class SquareComponent extends JComponent {

	private static final int WIDTH = 80;
	private static final int HEIGHT = 85;

	private final SquareModel squareModel;

	public SquareComponent(SquareModel squareModel) {
		this.squareModel = squareModel;

		initLayout();
		layoutSquare();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void layoutSquare() {
		JComponent squareComponent = createSquareComponent();
		add(squareComponent);
	}

	private JComponent createSquareComponent() {
		SquareComponentFactory factory = createSquareComponentFactory();
		return factory.createSquareComponent();
	}

	private SquareComponentFactory createSquareComponentFactory() {
		return new SquareComponentFactory(squareModel);
	}

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}

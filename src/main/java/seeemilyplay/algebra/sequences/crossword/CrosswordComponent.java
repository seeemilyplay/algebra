package seeemilyplay.algebra.sequences.crossword;

import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JComponent;

//TODO: say something about first error they encounter
@SuppressWarnings("serial")
final class CrosswordComponent extends JComponent {

	private final CrosswordModel crosswordModel;

	public CrosswordComponent(
			CrosswordModel crosswordModel) {
		this.crosswordModel = crosswordModel;

		initLayout();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new GridLayout(
				getRowCount(),
				getColumnCount()));
	}

	private int getRowCount() {
		return getCrossword().getRowCount();
	}

	private int getColumnCount() {
		return getCrossword().getColumnCount();
	}

	private Crossword getCrossword() {
		return crosswordModel.getCrossword();
	}

	private void layoutAll() {
		for (Square square : getCrossword()) {
			layoutSquare(square);
		}
	}

	private void layoutSquare(Square square) {
		SquareModel squareModel = getSquareModel(square);
		layoutSquare(squareModel);
	}

	private SquareModel getSquareModel(Square square) {
		return crosswordModel.getSquareModel(square);
	}

	private void layoutSquare(SquareModel squareModel) {
		SquareComponent squareComponent = createSquareComponent(squareModel);
		layoutSquare(squareComponent);
	}

	private SquareComponent createSquareComponent(SquareModel squareModel) {
		return new SquareComponent(squareModel);
	}

	private void layoutSquare(SquareComponent squareComponent) {
		add(squareComponent);
	}

	public JComponent getSquareComponent(Square square) {
		int componentIndex = getComponentIndex(square);
		return (JComponent)getComponent(componentIndex);
	}

	private int getComponentIndex(Square square) {
		Iterator<Square> iter = getCrossword().iterator();
		for (int i=0; iter.hasNext(); i++) {
			if (square.equals(iter.next())) {
				return i;
			}
		}
		throw new IllegalStateException();
	}
}

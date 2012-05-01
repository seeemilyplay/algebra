package seeemilyplay.algebra.sequences.crossword;

import java.util.Iterator;

final class CrosswordIterator implements Iterator<Square> {

	private final Crossword crossword;

	private int row = 0;
	private int col = -1;

	public CrosswordIterator(Crossword crossword) {
		this.crossword = crossword;
	}

	private boolean isLastRow() {
		return row>=getLastRowIndex();
	}

	private int getLastRowIndex() {
		return getRowCount() - 1;
	}

	public int getRowCount() {
		return crossword.getRowCount();
	}

	private boolean isLastColumn() {
		return col>=getLastColumnIndex();
	}

	private int getLastColumnIndex() {
		return getColumnCount() - 1;
	}

	public int getColumnCount() {
		return crossword.getColumnCount();
	}

	public boolean hasNext() {
		return (!isLastRow() || !isLastColumn());
	}

	public Square next() {
		incrementSquare();
		return getCurrentSquare();
	}

	private void incrementSquare() {
		if (isLastColumn()) {
			row++;
			col = 0;
		} else {
			col++;
		}
	}

	public Square getCurrentSquare() {
		return crossword.getSquare(row, col);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}

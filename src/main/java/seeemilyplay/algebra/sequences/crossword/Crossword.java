package seeemilyplay.algebra.sequences.crossword;

import java.util.Iterator;

import seeemilyplay.quizzer.core.Question;

final class Crossword implements Iterable<Square>, Question {

	private final Square[][] squares;

	public Crossword(Square[][] squares) {
		this.squares = squares;
	}

	public int getRowCount() {
		return squares[0].length;
	}

	public int getColumnCount() {
		return squares.length;
	}

	public Square getSquare(int row, int col) {
		return squares[col][row];
	}

	public Iterator<Square> iterator() {
		return new CrosswordIterator(this);
	}
}

package seeemilyplay.algebra.sequences.crossword;

import javax.swing.SwingConstants;

final class NearestSquareFinder {

	private final Crossword crossword;

	private Location currentLocation = new Location(0, 0);

	private Square square;
	private int direction;
	private Square nearest;
	private boolean isExhausted;


	public NearestSquareFinder(Crossword crossword) {
		this.crossword = crossword;
	}

	public Square getNearest(Square square, int direction) {
		init(square, direction);

		for (int spread=0; !isExhausted(); spread++) {

			searchSides(spread);

			if (isNearestFound()) {
				return getNearest();
			}
		}

		return null;
	}

	private void init(Square square, int direction) {
		this.square = square;
		this.direction = direction;
		this.nearest = null;
		this.isExhausted = false;
	}

	private void searchSides(int spread) {
		boolean isExhausted = true;

		int distanceOnOneSide = 0;
		Square nearestOnOneSide = null;
		
		if (moveToOneSide(spread)) {
			isExhausted = false;
			distanceOnOneSide = searchSide();
			nearestOnOneSide = getNearest();	
		}
		
		clearNearest();

		int distanceOnOtherSide = 0;
		
		if (!isNearestFound() && moveToOtherSide(spread)) {
			isExhausted = false;
			distanceOnOtherSide = searchSide();
		}
		
		boolean isOneOnOneSide = nearestOnOneSide!=null;
		boolean isNoneOnOtherSide = !isNearestFound();
		boolean isOneSideClosest = distanceOnOneSide<distanceOnOtherSide;
		
		if ((isNoneOnOtherSide 
				|| (isOneOnOneSide && isOneSideClosest))) {
			setNearest(nearestOnOneSide);
		}

		if (isExhausted) {
			this.isExhausted = true;
		}
	}

	private boolean isExhausted() {
		return isExhausted;
	}

	private boolean moveToOneSide(int distance) {
		moveToStart();
		int direction = getOneSideDirection();
		return move(direction, distance);
	}

	private boolean moveToOtherSide(int distance) {
		moveToStart();
		int direction = getOtherSideDirection();
		return move(direction, distance);
	}

	private int getOneSideDirection() {
		return (isHorizontal() ?
				SwingConstants.SOUTH
				: SwingConstants.EAST);
	}

	private int getOtherSideDirection(){
		return (isHorizontal() ?
				SwingConstants.NORTH
				: SwingConstants.WEST);
	}

	private boolean isHorizontal() {
		return (direction==SwingConstants.WEST
				|| direction==SwingConstants.EAST);
	}

	private int searchSide() {
		int distance = 0;
		while (!isNearestFound() && move(direction)) {
			distance++;
			if (isQuestion()) {
				setNearest();
			}
		}
		return distance;
	}

	private void setNearest() {
		nearest = getSquare();
	}
	
	private void setNearest(Square nearest) {
		this.nearest = nearest;
	}

	private boolean isNearestFound() {
		return nearest!=null;
	}

	private Square getNearest() {
		return nearest;
	}
	
	private void clearNearest() {
		this.nearest = null;
	}

	private void moveToStart() {
		moveToFirstRow();
		do {
			moveToFirstColumn();
			do {
				if (isSquare()) {
					return;
				}
			} while (moveRight());
		} while (moveDown());
		throw new IllegalStateException();
	}

	private boolean isSquare() {
		return square.equals(getSquare());
	}

	private boolean isQuestion() {
		return getSquare().isQuestion();
	}

	private Square getSquare() {
		return crossword.getSquare(
				getCurrentRow(),
				getCurrentCol());
	}

	private void moveToFirstRow() {
		currentLocation = new Location(
				0,
				getCurrentCol());
	}

	private void moveToFirstColumn() {
		currentLocation = new Location(
				getCurrentRow(),
				0);
	}

	private boolean moveRight() {
		return move(SwingConstants.EAST);
	}

	private boolean moveDown() {
		return move(SwingConstants.SOUTH);
	}

	private boolean move(int direction) {
		return move(direction, 1);
	}

	private boolean move(int direction, int distance) {
		Location location = calculateTargetLocation(
				direction,
				distance);
		if (isLegal(location)) {

			currentLocation = location;
			return true;
		}
		return false;
	}

	private Location calculateTargetLocation(
			int direction,
			int distance) {
		int row = getCurrentRow();
		int col = getCurrentCol();
		switch (direction) {
			case SwingConstants.WEST: {
				col = col - distance;
				break;
			} case SwingConstants.EAST: {
				col = col + distance;
				break;
			} case SwingConstants.NORTH: {
				row = row - distance;
				break;
			} case SwingConstants.SOUTH: {
				row = row + distance;
				break;
			} default: {
				throw new IllegalStateException();
			}
		}
		return new Location(row, col);
	}

	private int getCurrentRow() {
		return currentLocation.getRow();
	}

	private int getCurrentCol() {
		return currentLocation.getCol();
	}

	private boolean isLegal(Location location) {
		int row = location.getRow();
		int col = location.getCol();
		return isLegalRow(row) && isLegalCol(col);
	}

	private boolean isLegalRow(int row) {
		return 0<=row && row<getRowCount();
	}

	private boolean isLegalCol(int col) {
		return 0<=col && col<getColCount();
	}

	private class Location {

		private final int row;
		private final int col;

		public Location(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
	}

	private int getRowCount() {
		return crossword.getRowCount();
	}

	private int getColCount() {
		return crossword.getColumnCount();
	}
}

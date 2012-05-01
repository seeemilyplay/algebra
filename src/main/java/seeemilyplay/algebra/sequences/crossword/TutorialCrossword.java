package seeemilyplay.algebra.sequences.crossword;


final class TutorialCrossword {

	private TutorialCrossword() {
		super();
	}

	public static Crossword getInstance() {
		Square[][] squares = new Square[][] {
				new Square[] {
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(1)
				},
				new Square[] {
						Square.createGivenSquare(1000),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(4)
				},
				new Square[] {
						Square.createQuestionSquare(100),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(1),
						Square.createEmptySquare(),
						Square.createGivenSquare(9)
				},
				new Square[] {
						Square.createGivenSquare(10),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(4),
						Square.createEmptySquare(),
						Square.createGivenSquare(16)
				},
				new Square[] {
						Square.createGivenSquare(1),
						Square.createGivenSquare(3),
						Square.createGivenSquare(5),
						Square.createQuestionSquare(7),
						Square.createEmptySquare(),
						Square.createQuestionSquare(25)
				},
				new Square[] {
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(10),
						Square.createEmptySquare(),
						Square.createGivenSquare(36)
				},
				new Square[] {
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(13),
						Square.createEmptySquare(),
						Square.createGivenSquare(49)
				},
				new Square[] {
						Square.createGivenSquare(2),
						Square.createGivenSquare(4),
						Square.createGivenSquare(8),
						Square.createQuestionSquare(16),
						Square.createGivenSquare(32),
						Square.createGivenSquare(64)
				}
		};
		return new Crossword(squares);
	}
}

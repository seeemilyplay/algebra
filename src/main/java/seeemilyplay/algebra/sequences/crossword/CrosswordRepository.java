package seeemilyplay.algebra.sequences.crossword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seeemilyplay.algebra.progress.Level;

final class CrosswordRepository {

	private final List<Crossword> crosswords =
		new ArrayList<Crossword>();

	private final Map<Level,Integer> countByLevel =
		new HashMap<Level,Integer>();

	public CrosswordRepository() {
		super();
		init();
	}

	private void init() {
		initCountByLevel();
		createCrosswords();
	}

	private void initCountByLevel() {
		for (Level level : Level.values()) {
			countByLevel.put(level, 0);
		}
	}

	private void createCrosswords() {

		saveCrossword(Level.BEGINNER, createFirstCrossword());
		saveCrossword(Level.BEGINNER, createSecondCrossword());
		saveCrossword(Level.BEGINNER, createThirdCrossword());
		saveCrossword(Level.BEGINNER, createFourthCrossword());
		saveCrossword(Level.BEGINNER, createFifthCrossword());

		saveCrossword(Level.NOVICE, createSixthCrossword());
		saveCrossword(Level.NOVICE, createSeventhCrossword());
		saveCrossword(Level.NOVICE, createEighthCrossword());
		saveCrossword(Level.NOVICE, createNinthCrossword());
		saveCrossword(Level.NOVICE, createTenthCrossword());

		saveCrossword(Level.INTERMEDIATE, createEleventhCrossword());
		saveCrossword(Level.INTERMEDIATE, createTwelfthCrossword());
		saveCrossword(Level.INTERMEDIATE, createThirteenthCrossword());
		saveCrossword(Level.INTERMEDIATE, createFourteenthCrossword());
		saveCrossword(Level.INTERMEDIATE, createFifteenthCrossword());
	}

	private void saveCrossword(
			Level level,
			Crossword crossword) {
		incrementLevelCount(level);
		saveCrossword(crossword);
	}

	private void incrementLevelCount(Level level) {
		int count = getCrosswordCount(level);
		count++;
		countByLevel.put(level, count);
	}

	private void saveCrossword(Crossword crossword) {
		crosswords.add(crossword);
	}

	public Crossword getCrossword(int index) {
		return crosswords.get(index);
	}

	public int getCrosswordCount() {
		return crosswords.size();
	}

	public int getCrosswordCount(Level level) {
		return countByLevel.get(level);
	}

	public int getFirstIndex(Level level) {
		int index = 0;
		for (Level lowerLevel : Level.values()) {
			if (lowerLevel.equals(level)) {
				break;
			}
			index += getCrosswordCount(level);
		}
		return index;
	}

	public int getLastIndex(Level level) {
		int lastIndex = getFirstIndex(level) + getCrosswordCount(level) - 1;
		return Math.max(0, lastIndex);
	}

	private Crossword createFirstCrossword() {
		Square[][] squares = new Square[][] {
				new Square[] {
					Square.createGivenSquare(1),
					Square.createEmptySquare(),
					Square.createGivenSquare(2),
					Square.createGivenSquare(5),
					Square.createGivenSquare(8),
					Square.createQuestionSquare(11),
					Square.createQuestionSquare(14)
				},
				new Square[] {
					Square.createGivenSquare(4),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(9),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createQuestionSquare(7),
					Square.createGivenSquare(10),
					Square.createGivenSquare(13),
					Square.createQuestionSquare(16),
					Square.createEmptySquare(),
					Square.createGivenSquare(7),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createGivenSquare(10),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(12),
					Square.createEmptySquare(),
					Square.createGivenSquare(5),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createGivenSquare(9),
					Square.createEmptySquare(),
					Square.createGivenSquare(8),
					Square.createEmptySquare(),
					Square.createQuestionSquare(3),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createGivenSquare(32),
					Square.createQuestionSquare(16),
					Square.createGivenSquare(8),
					Square.createQuestionSquare(4),
					Square.createGivenSquare(2),
					Square.createQuestionSquare(1),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createGivenSquare(25),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(4),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createGivenSquare(36),
					Square.createEmptySquare(),
					Square.createGivenSquare(1),
					Square.createGivenSquare(6),
					Square.createGivenSquare(11),
					Square.createQuestionSquare(16)
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createQuestionSquare(49),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createQuestionSquare(8),
					Square.createEmptySquare(),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(1000),
					Square.createGivenSquare(100),
					Square.createQuestionSquare(10),
					Square.createGivenSquare(1),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(12),
					Square.createEmptySquare(),
					Square.createEmptySquare()
				}
		};
		return new Crossword(squares);
	}

	private Crossword createSecondCrossword() {
		Square[][] squares = new Square[][] {
				new Square[] {
						Square.createGivenSquare(1000),
						Square.createEmptySquare(),
						Square.createQuestionSquare(32),
						Square.createEmptySquare(),
						Square.createGivenSquare(49),
						Square.createEmptySquare(),
						Square.createEmptySquare()
				},
				new Square[] {
						Square.createQuestionSquare(100),
						Square.createEmptySquare(),
						Square.createGivenSquare(16),
						Square.createEmptySquare(),
						Square.createGivenSquare(36),
						Square.createEmptySquare(),
						Square.createEmptySquare()
				},
				new Square[] {
						Square.createGivenSquare(10),
						Square.createEmptySquare(),
						Square.createGivenSquare(8),
						Square.createEmptySquare(),
						Square.createGivenSquare(25),
						Square.createEmptySquare(),
						Square.createGivenSquare(66)
				},
				new Square[] {
						Square.createGivenSquare(1),
						Square.createGivenSquare(2),
						Square.createGivenSquare(4),
						Square.createQuestionSquare(8),
						Square.createQuestionSquare(16),
						Square.createGivenSquare(32),
						Square.createQuestionSquare(64)
				},
				new Square[] {
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(2),
						Square.createEmptySquare(),
						Square.createGivenSquare(9),
						Square.createEmptySquare(),
						Square.createGivenSquare(62)
				},
				new Square[] {
						Square.createGivenSquare(3),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(4),
						Square.createEmptySquare(),
						Square.createQuestionSquare(60)
				},
				new Square[] {
						Square.createGivenSquare(6),
						Square.createEmptySquare(),
						Square.createGivenSquare(1),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createQuestionSquare(58)
				},
				new Square[] {
						Square.createQuestionSquare(9),
						Square.createQuestionSquare(7),
						Square.createGivenSquare(5),
						Square.createGivenSquare(3),
						Square.createGivenSquare(1),
						Square.createEmptySquare(),
						Square.createGivenSquare(56)
				},
				new Square[] {
						Square.createGivenSquare(12),
						Square.createEmptySquare(),
						Square.createGivenSquare(9),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(2),
						Square.createEmptySquare()
				},
				new Square[] {
						Square.createQuestionSquare(15),
						Square.createEmptySquare(),
						Square.createQuestionSquare(13),
						Square.createEmptySquare(),
						Square.createEmptySquare(),
						Square.createGivenSquare(5),
						Square.createEmptySquare()
				},
				new Square[] {
						Square.createQuestionSquare(18),
						Square.createEmptySquare(),
						Square.createQuestionSquare(17),
						Square.createGivenSquare(14),
						Square.createGivenSquare(11),
						Square.createQuestionSquare(8),
						Square.createQuestionSquare(5)
				}
		};
		return new Crossword(squares);
	}

	private Crossword createThirdCrossword() {
		Square[][] squares = new Square[][] {
				new Square[] {
					Square.createQuestionSquare(9),
					Square.createQuestionSquare(7),
					Square.createQuestionSquare(5),
					Square.createGivenSquare(3),
					Square.createGivenSquare(1),
					Square.createEmptySquare(),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(10),
					Square.createEmptySquare(),
					Square.createGivenSquare(4),
					Square.createEmptySquare(),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(15),
					Square.createEmptySquare(),
					Square.createGivenSquare(7),
					Square.createEmptySquare(),
					Square.createEmptySquare()
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createQuestionSquare(25),
					Square.createQuestionSquare(20),
					Square.createGivenSquare(15),
					Square.createGivenSquare(10),
					Square.createGivenSquare(5),
					Square.createQuestionSquare(0)
				},
				new Square[] {
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createQuestionSquare(13),
					Square.createEmptySquare(),
					Square.createGivenSquare(2)
				},
				new Square[] {
					Square.createGivenSquare(64),
					Square.createGivenSquare(49),
					Square.createQuestionSquare(36),
					Square.createGivenSquare(25),
					Square.createQuestionSquare(16),
					Square.createGivenSquare(9),
					Square.createGivenSquare(4)
				},
				new Square[] {
					Square.createQuestionSquare(32),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(21),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createGivenSquare(6)
				},
				new Square[] {
					Square.createGivenSquare(16),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createQuestionSquare(17),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createQuestionSquare(8)
				},
				new Square[] {
					Square.createGivenSquare(8),
					Square.createEmptySquare(),
					Square.createQuestionSquare(14),
					Square.createGivenSquare(13),
					Square.createGivenSquare(12),
					Square.createGivenSquare(11),
					Square.createQuestionSquare(10)
				},
				new Square[] {
					Square.createGivenSquare(4),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createQuestionSquare(12)
				},
				new Square[] {
					Square.createGivenSquare(2),
					Square.createEmptySquare(),
					Square.createEmptySquare(),
					Square.createQuestionSquare(2),
					Square.createGivenSquare(6),
					Square.createGivenSquare(10),
					Square.createQuestionSquare(14)
				}
		};
		return new Crossword(squares);
	}

	private Crossword createFourthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(49),
				Square.createEmptySquare(),
				Square.createGivenSquare(0),
				Square.createGivenSquare(3),
				Square.createGivenSquare(6),
				Square.createQuestionSquare(9)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(36),
				Square.createEmptySquare(),
				Square.createQuestionSquare(2),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(7)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(25),
				Square.createEmptySquare(),
				Square.createQuestionSquare(4),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(5)
			},
			new Square[] {
				Square.createQuestionSquare(21),
				Square.createQuestionSquare(16),
				Square.createGivenSquare(11),
				Square.createGivenSquare(6),
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createQuestionSquare(3)
			},
			new Square[] {
				Square.createQuestionSquare(17),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(8),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(1)
			},
			new Square[] {
				Square.createGivenSquare(13),
				Square.createEmptySquare(),
				Square.createGivenSquare(1),
				Square.createQuestionSquare(10),
				Square.createGivenSquare(100),
				Square.createGivenSquare(1000),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(9),
				Square.createEmptySquare(),
				Square.createGivenSquare(2),
				Square.createEmptySquare(),
				Square.createGivenSquare(98),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(5),
				Square.createEmptySquare(),
				Square.createQuestionSquare(4),
				Square.createEmptySquare(),
				Square.createGivenSquare(96),
				Square.createEmptySquare(),
				Square.createQuestionSquare(7)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(8),
				Square.createEmptySquare(),
				Square.createQuestionSquare(94),
				Square.createEmptySquare(),
				Square.createGivenSquare(10)
			},
			new Square[] {
				Square.createGivenSquare(14),
				Square.createGivenSquare(15),
				Square.createQuestionSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(13)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(32),
				Square.createGivenSquare(28),
				Square.createGivenSquare(24),
				Square.createQuestionSquare(20),
				Square.createQuestionSquare(16)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createFifthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(3),
				Square.createGivenSquare(8),
				Square.createQuestionSquare(13),
				Square.createQuestionSquare(18),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(9)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(10),
				Square.createEmptySquare(),
				Square.createQuestionSquare(2),
				Square.createGivenSquare(6),
				Square.createQuestionSquare(10)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(7),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(11)
			},
			new Square[] {
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createGivenSquare(4),
				Square.createGivenSquare(6),
				Square.createGivenSquare(8),
				Square.createQuestionSquare(10),
				Square.createQuestionSquare(12)
			},
			new Square[] {
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(100),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(9),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1000),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(16),
				Square.createQuestionSquare(12),
				Square.createGivenSquare(8),
				Square.createGivenSquare(4),
				Square.createGivenSquare(0),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(25),
				Square.createEmptySquare(),
				Square.createGivenSquare(10),
				Square.createEmptySquare(),
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createGivenSquare(1000)
			},
			new Square[] {
				Square.createGivenSquare(36),
				Square.createEmptySquare(),
				Square.createGivenSquare(12),
				Square.createEmptySquare(),
				Square.createGivenSquare(2),
				Square.createEmptySquare(),
				Square.createGivenSquare(100)
			},
			new Square[] {
				Square.createGivenSquare(49),
				Square.createEmptySquare(),
				Square.createQuestionSquare(14),
				Square.createEmptySquare(),
				Square.createGivenSquare(3),
				Square.createEmptySquare(),
				Square.createGivenSquare(10)
			},
			new Square[] {
				Square.createQuestionSquare(64),
				Square.createQuestionSquare(32),
				Square.createQuestionSquare(16),
				Square.createGivenSquare(8),
				Square.createQuestionSquare(4),
				Square.createGivenSquare(2),
				Square.createQuestionSquare(1)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createSixthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(7),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1000),
				Square.createQuestionSquare(100),
				Square.createQuestionSquare(10)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(5),
				Square.createEmptySquare(),
				Square.createGivenSquare(2),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(5)
			},
			new Square[] {
				Square.createGivenSquare(-2),
				Square.createQuestionSquare(3),
				Square.createGivenSquare(8),
				Square.createQuestionSquare(13),
				Square.createQuestionSquare(18),
				Square.createEmptySquare(),
				Square.createGivenSquare(0)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createGivenSquare(24),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(-5)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(35),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(-10)
			},
			new Square[] {
				Square.createQuestionSquare(16),
				Square.createGivenSquare(26),
				Square.createGivenSquare(36),
				Square.createQuestionSquare(46),
				Square.createEmptySquare(),
				Square.createQuestionSquare(10),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(25),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(8),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(36),
				Square.createEmptySquare(),
				Square.createGivenSquare(3),
				Square.createGivenSquare(4),
				Square.createGivenSquare(5),
				Square.createQuestionSquare(6),
				Square.createQuestionSquare(7)
			},
			new Square[] {
				Square.createGivenSquare(49),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(6),
				Square.createEmptySquare(),
				Square.createGivenSquare(4),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(64),
				Square.createGivenSquare(32),
				Square.createGivenSquare(16),
				Square.createQuestionSquare(8),
				Square.createGivenSquare(4),
				Square.createQuestionSquare(2),
				Square.createGivenSquare(1)
			},
			new Square[] {
				Square.createQuestionSquare(81),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(10),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			}
		};
		return new Crossword(squares);
	}

	private Crossword createSeventhCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(1),
				Square.createGivenSquare(3),
				Square.createGivenSquare(5),
				Square.createQuestionSquare(7),
				Square.createQuestionSquare(9),
				Square.createEmptySquare(),
				Square.createQuestionSquare(21)
			},
			new Square[] {
				Square.createQuestionSquare(10),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(6),
				Square.createEmptySquare(),
				Square.createGivenSquare(17)
			},
			new Square[] {
				Square.createGivenSquare(100),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(-2),
				Square.createGivenSquare(3),
				Square.createQuestionSquare(8),
				Square.createQuestionSquare(13)
			},
			new Square[] {
				Square.createGivenSquare(1000),
				Square.createEmptySquare(),
				Square.createGivenSquare(22),
				Square.createEmptySquare(),
				Square.createGivenSquare(0),
				Square.createEmptySquare(),
				Square.createGivenSquare(9)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(10),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-3),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(2),
				Square.createQuestionSquare(0),
				Square.createGivenSquare(-2),
				Square.createGivenSquare(-4),
				Square.createQuestionSquare(-6),
				Square.createGivenSquare(-8),
				Square.createQuestionSquare(-10)
			},
			new Square[] {
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-14),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(4)
			},
			new Square[] {
				Square.createGivenSquare(8),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(18)
			},
			new Square[] {
				Square.createGivenSquare(16),
				Square.createGivenSquare(25),
				Square.createGivenSquare(36),
				Square.createGivenSquare(49),
				Square.createQuestionSquare(64),
				Square.createEmptySquare(),
				Square.createGivenSquare(32)
			},
			new Square[] {
				Square.createGivenSquare(32),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(46)
			},
			new Square[] {
				Square.createQuestionSquare(64),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(72),
				Square.createGivenSquare(68),
				Square.createGivenSquare(64),
				Square.createQuestionSquare(60)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createEighthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(89),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(4)
			},
			new Square[] {
				Square.createGivenSquare(1),
				Square.createGivenSquare(10),
				Square.createQuestionSquare(100),
				Square.createGivenSquare(1000),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(9)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(111),
				Square.createEmptySquare(),
				Square.createGivenSquare(24),
				Square.createGivenSquare(20),
				Square.createQuestionSquare(16)
			},
			new Square[] {
				Square.createQuestionSquare(128),
				Square.createGivenSquare(125),
				Square.createQuestionSquare(122),
				Square.createGivenSquare(119),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(25)
			},
			new Square[] {
				Square.createGivenSquare(64),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(36)
			},
			new Square[] {
				Square.createGivenSquare(32),
				Square.createEmptySquare(),
				Square.createGivenSquare(29),
				Square.createGivenSquare(34),
				Square.createGivenSquare(39),
				Square.createQuestionSquare(44),
				Square.createQuestionSquare(49)
			},
			new Square[] {
				Square.createGivenSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(18),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(8),
				Square.createGivenSquare(6),
				Square.createGivenSquare(4),
				Square.createQuestionSquare(2),
				Square.createQuestionSquare(0),
				Square.createGivenSquare(-2),
				Square.createGivenSquare(-4)
			},
			new Square[] {
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(3),
				Square.createEmptySquare(),
				Square.createGivenSquare(0)
			},
			new Square[] {
				Square.createQuestionSquare(2),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(6),
				Square.createEmptySquare(),
				Square.createQuestionSquare(4)
			},
			new Square[] {
				Square.createQuestionSquare(1),
				Square.createGivenSquare(3),
				Square.createGivenSquare(5),
				Square.createQuestionSquare(7),
				Square.createQuestionSquare(9),
				Square.createEmptySquare(),
				Square.createGivenSquare(8)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createNinthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(1),
				Square.createGivenSquare(5),
				Square.createQuestionSquare(9),
				Square.createQuestionSquare(13),
				Square.createGivenSquare(17),
				Square.createEmptySquare(),
				Square.createGivenSquare(27)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(16)
			},
			new Square[] {
				Square.createQuestionSquare(35),
				Square.createQuestionSquare(30),
				Square.createQuestionSquare(25),
				Square.createGivenSquare(20),
				Square.createGivenSquare(15),
				Square.createGivenSquare(10),
				Square.createGivenSquare(5)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(36),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-6)
			},
			new Square[] {
				Square.createQuestionSquare(254),
				Square.createEmptySquare(),
				Square.createGivenSquare(49),
				Square.createEmptySquare(),
				Square.createGivenSquare(19),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(256),
				Square.createGivenSquare(128),
				Square.createGivenSquare(64),
				Square.createGivenSquare(32),
				Square.createQuestionSquare(16),
				Square.createGivenSquare(8),
				Square.createGivenSquare(4)
			},
			new Square[] {
				Square.createGivenSquare(258),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(13),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(260),
				Square.createEmptySquare(),
				Square.createGivenSquare(26),
				Square.createEmptySquare(),
				Square.createQuestionSquare(10),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-12)
			},
			new Square[] {
				Square.createQuestionSquare(262),
				Square.createEmptySquare(),
				Square.createQuestionSquare(21),
				Square.createQuestionSquare(14),
				Square.createQuestionSquare(7),
				Square.createGivenSquare(0),
				Square.createGivenSquare(-7)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(-2)
			},
			new Square[] {
				Square.createQuestionSquare(15),
				Square.createQuestionSquare(13),
				Square.createQuestionSquare(11),
				Square.createGivenSquare(9),
				Square.createGivenSquare(7),
				Square.createGivenSquare(5),
				Square.createGivenSquare(3)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createTenthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(11),
				Square.createGivenSquare(15),
				Square.createGivenSquare(19),
				Square.createQuestionSquare(23),
				Square.createQuestionSquare(27),
				Square.createEmptySquare(),
				Square.createGivenSquare(-14)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(17),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-7)
			},
			new Square[] {
				Square.createQuestionSquare(100),
				Square.createGivenSquare(10),
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createGivenSquare(7),
				Square.createEmptySquare(),
				Square.createGivenSquare(0)
			},
			new Square[] {
				Square.createQuestionSquare(75),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(-8),
				Square.createQuestionSquare(-3),
				Square.createGivenSquare(2),
				Square.createQuestionSquare(7)
			},
			new Square[] {
				Square.createGivenSquare(50),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-1),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-13),
				Square.createEmptySquare(),
				Square.createGivenSquare(14)
			},
			new Square[] {
				Square.createGivenSquare(25),
				Square.createEmptySquare(),
				Square.createQuestionSquare(1),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(21)
			},
			new Square[] {
				Square.createGivenSquare(0),
				Square.createEmptySquare(),
				Square.createQuestionSquare(3),
				Square.createEmptySquare(),
				Square.createGivenSquare(40),
				Square.createEmptySquare(),
				Square.createQuestionSquare(28)
			},
			new Square[] {
				Square.createGivenSquare(-25),
				Square.createGivenSquare(-10),
				Square.createGivenSquare(5),
				Square.createQuestionSquare(20),
				Square.createQuestionSquare(35),
				Square.createEmptySquare(),
				Square.createGivenSquare(35)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(7),
				Square.createEmptySquare(),
				Square.createGivenSquare(30),
				Square.createEmptySquare(),
				Square.createGivenSquare(42)
			},
			new Square[] {
				Square.createQuestionSquare(1),
				Square.createGivenSquare(4),
				Square.createGivenSquare(9),
				Square.createQuestionSquare(16),
				Square.createQuestionSquare(25),
				Square.createGivenSquare(36),
				Square.createQuestionSquare(49)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(11),
				Square.createEmptySquare(),
				Square.createQuestionSquare(20),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			}
		};
		return new Crossword(squares);
	}

	private Crossword createEleventhCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(200),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1)
			},
			new Square[] {
				Square.createQuestionSquare(3),
				Square.createGivenSquare(30),
				Square.createQuestionSquare(300),
				Square.createGivenSquare(3000),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(3)
			},
			new Square[] {
				Square.createQuestionSquare(5),
				Square.createEmptySquare(),
				Square.createGivenSquare(400),
				Square.createEmptySquare(),
				Square.createQuestionSquare(53),
				Square.createEmptySquare(),
				Square.createGivenSquare(9)
			},
			new Square[] {
				Square.createGivenSquare(7),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(51),
				Square.createEmptySquare(),
				Square.createGivenSquare(27)
			},
			new Square[] {
				Square.createGivenSquare(9),
				Square.createGivenSquare(16),
				Square.createGivenSquare(25),
				Square.createGivenSquare(36),
				Square.createQuestionSquare(49),
				Square.createQuestionSquare(64),
				Square.createQuestionSquare(81)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(32),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(47),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(64),
				Square.createEmptySquare(),
				Square.createGivenSquare(50),
				Square.createQuestionSquare(45),
				Square.createGivenSquare(40),
				Square.createGivenSquare(35)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(128),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(38),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(256),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(36),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(512),
				Square.createEmptySquare(),
				Square.createGivenSquare(40),
				Square.createGivenSquare(37),
				Square.createQuestionSquare(34),
				Square.createGivenSquare(31)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(1024),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(32),
				Square.createEmptySquare()
			}
		};
		return new Crossword(squares);
	}

	private Crossword createTwelfthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(3),
				Square.createQuestionSquare(9),
				Square.createGivenSquare(27),
				Square.createGivenSquare(81),
				Square.createGivenSquare(243),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(1),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1)
			},
			new Square[] {
				Square.createGivenSquare(-1),
				Square.createEmptySquare(),
				Square.createGivenSquare(18),
				Square.createQuestionSquare(14),
				Square.createGivenSquare(10),
				Square.createQuestionSquare(6),
				Square.createQuestionSquare(2)
			},
			new Square[] {
				Square.createQuestionSquare(-3),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(12),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(4)
			},
			new Square[] {
				Square.createQuestionSquare(-5),
				Square.createGivenSquare(0),
				Square.createGivenSquare(5),
				Square.createQuestionSquare(10),
				Square.createQuestionSquare(15),
				Square.createEmptySquare(),
				Square.createGivenSquare(8)
			},
			new Square[] {
				Square.createQuestionSquare(-7),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(8),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(16)
			},
			new Square[] {
				Square.createGivenSquare(-9),
				Square.createGivenSquare(21),
				Square.createGivenSquare(51),
				Square.createEmptySquare(),
				Square.createQuestionSquare(10),
				Square.createEmptySquare(),
				Square.createQuestionSquare(32)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(18),
				Square.createEmptySquare(),
				Square.createQuestionSquare(118),
				Square.createGivenSquare(100),
				Square.createGivenSquare(82),
				Square.createQuestionSquare(64)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(15),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1000),
				Square.createEmptySquare(),
				Square.createGivenSquare(128)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(12),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(4),
				Square.createQuestionSquare(9),
				Square.createQuestionSquare(16),
				Square.createGivenSquare(25),
				Square.createQuestionSquare(36),
				Square.createGivenSquare(49),
				Square.createGivenSquare(64)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createThirteenthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(2),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(5),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(4)
			},
			new Square[] {
				Square.createQuestionSquare(-3),
				Square.createGivenSquare(-1),
				Square.createGivenSquare(1),
				Square.createGivenSquare(3),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1)
			},
			new Square[] {
				Square.createGivenSquare(-8),
				Square.createEmptySquare(),
				Square.createGivenSquare(2),
				Square.createGivenSquare(1),
				Square.createGivenSquare(0),
				Square.createQuestionSquare(-1),
				Square.createQuestionSquare(-2)
			},
			new Square[] {
				Square.createQuestionSquare(-13),
				Square.createEmptySquare(),
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-5)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(8),
				Square.createGivenSquare(4),
				Square.createGivenSquare(0),
				Square.createQuestionSquare(-4),
				Square.createQuestionSquare(-8)
			},
			new Square[] {
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createGivenSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-11)
			},
			new Square[] {
				Square.createGivenSquare(12),
				Square.createEmptySquare(),
				Square.createQuestionSquare(32),
				Square.createEmptySquare(),
				Square.createGivenSquare(10),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(36),
				Square.createGivenSquare(49),
				Square.createQuestionSquare(64),
				Square.createGivenSquare(81),
				Square.createQuestionSquare(100),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(108),
				Square.createEmptySquare(),
				Square.createQuestionSquare(128),
				Square.createEmptySquare(),
				Square.createGivenSquare(1000),
				Square.createQuestionSquare(500),
				Square.createGivenSquare(250)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(133)
			},
			new Square[] {
				Square.createGivenSquare(1024),
				Square.createGivenSquare(512),
				Square.createGivenSquare(256),
				Square.createQuestionSquare(128),
				Square.createQuestionSquare(64),
				Square.createGivenSquare(32),
				Square.createGivenSquare(16)
			}
		};
		return new Crossword(squares);
	}

	private Crossword createFourteenthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createGivenSquare(2),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(1),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createGivenSquare(32),
				Square.createGivenSquare(16),
				Square.createGivenSquare(8),
				Square.createQuestionSquare(4),
				Square.createQuestionSquare(2)
			},
			new Square[] {
				Square.createGivenSquare(6),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createGivenSquare(9),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(8),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(-8),
				Square.createEmptySquare(),
				Square.createGivenSquare(16),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(10),
				Square.createQuestionSquare(0),
				Square.createGivenSquare(-10),
				Square.createQuestionSquare(-20),
				Square.createEmptySquare(),
				Square.createQuestionSquare(25),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(36),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(81),
				Square.createEmptySquare(),
				Square.createGivenSquare(31),
				Square.createGivenSquare(40),
				Square.createQuestionSquare(49),
				Square.createQuestionSquare(58)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(27),
				Square.createEmptySquare(),
				Square.createGivenSquare(26),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(9),
				Square.createEmptySquare(),
				Square.createQuestionSquare(21),
				Square.createGivenSquare(14),
				Square.createGivenSquare(7),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(3),
				Square.createEmptySquare(),
				Square.createQuestionSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(1),
				Square.createGivenSquare(6),
				Square.createQuestionSquare(11),
				Square.createQuestionSquare(16),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			}
		};
		return new Crossword(squares);
	}

	private Crossword createFifteenthCrossword() {
		Square[][] squares = new Square[][] {
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(-6),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(16)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createQuestionSquare(-4),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-4),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(25)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(-2),
				Square.createEmptySquare(),
				Square.createQuestionSquare(0),
				Square.createGivenSquare(12),
				Square.createGivenSquare(24),
				Square.createQuestionSquare(36)
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createGivenSquare(0),
				Square.createEmptySquare(),
				Square.createGivenSquare(4),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(49)
			},
			new Square[] {
				Square.createGivenSquare(1),
				Square.createGivenSquare(2),
				Square.createGivenSquare(4),
				Square.createGivenSquare(8),
				Square.createQuestionSquare(16),
				Square.createQuestionSquare(32),
				Square.createQuestionSquare(64)
			},
			new Square[] {
				Square.createGivenSquare(3),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(12),
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createGivenSquare(81)
			},
			new Square[] {
				Square.createGivenSquare(9),
				Square.createEmptySquare(),
				Square.createGivenSquare(-3),
				Square.createEmptySquare(),
				Square.createQuestionSquare(-5),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createQuestionSquare(27),
				Square.createEmptySquare(),
				Square.createGivenSquare(3),
				Square.createGivenSquare(4),
				Square.createQuestionSquare(5),
				Square.createQuestionSquare(6),
				Square.createQuestionSquare(7)
			},
			new Square[] {
				Square.createGivenSquare(81),
				Square.createEmptySquare(),
				Square.createGivenSquare(9),
				Square.createEmptySquare(),
				Square.createGivenSquare(15),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createEmptySquare(),
				Square.createEmptySquare(),
				Square.createQuestionSquare(15),
				Square.createEmptySquare(),
				Square.createGivenSquare(25),
				Square.createEmptySquare(),
				Square.createEmptySquare()
			},
			new Square[] {
				Square.createGivenSquare(7),
				Square.createGivenSquare(14),
				Square.createQuestionSquare(21),
				Square.createQuestionSquare(28),
				Square.createQuestionSquare(35),
				Square.createQuestionSquare(42),
				Square.createEmptySquare()
			}
		};
		return new Crossword(squares);
	}
}

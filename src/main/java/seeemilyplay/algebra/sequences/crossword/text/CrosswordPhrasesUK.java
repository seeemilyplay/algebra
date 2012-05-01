package seeemilyplay.algebra.sequences.crossword.text;

final class CrosswordPhrasesUK implements CrosswordPhrases {

	public CrosswordPhrasesUK() {
		super();
	}

	public String getGameName() {
		return "Crossword";
	}

	public String getInstructions() {
		return "Each word in the crossword is a different " +
				"number sequence.\n" +
				"Complete the crossword by " +
				"filling in the missing numbers.";
	}

	public String getFinish() {
		return "Finished";
	}

	public String getTutorialIntro() {
		return "Fill in the missing numbers " +
				"to complete each sequence.";
	}

	public String getTutorialOutro() {
		return "Well done!\n" +
				"You're ready to play.";
	}

	public String getTutorialQ1Hint() {
		return "The rule for the first one is: multiply each time by 10.\n" +
				"Finish it off by clicking on the empty square and typing 100.";
	}

	public String getTutorialQ1Correction() {
		return "The missing number is actually 100.\n" +
		 	"The rule is: multiply each time by 10.";
	}

	public String getTutorialQ2Hint() {
		return "The rule for the across one is: add 3 each time.\n" +
			"The rule for the down one is: add 2 each time.\n" +
			"What's the missing number?";
	}

	public String getTutorialQ2Correction() {
		return "The missing number is actually 7.\n" +
				"The rule for the across one is: add 3 each time.\n" +
				"The rule for the down one is: add 2 each time.";
	}

	public String getTutorialQ3Hint() {
		return "Can you figure out the down rule here?\n" +
				"What's the missing number?";
	}

	public String getTutorialQ3Correction() {
		return "The down rule is: multiply each time by 2.\n" +
				"So the missing number is 16.";
	}

	public String getTutorialQ4Hint() {
		return "This is the sequence of square numbers.\n" +
				"What's missing?";
	}

	public String getTutorialQ4Correction() {
		return "The missing square number is 25.";
	}
}

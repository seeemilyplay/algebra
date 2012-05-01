package seeemilyplay.algebra.games.text;

final class GamePhrasesUK implements GamePhrases {

	public GamePhrasesUK() {
		super();
	}

	public String getTutorial() {
		return "Tutorial";
	}

	public String getPlay() {
		return "Play";
	}

	public String getYouveEarntAnAward(String awardName) {
		return "Well done!\n" +
				"You've earnt a " + awardName + ".";
	}
	
	public String getYouveEarntAnAwardAndUnlockedANewGame(String awardName) {
		return "Brilliant!\n" +
				"You've earnt a " + awardName + " and unlocked a new game.";
	}
	
	public String getYouveEarntAnAwardAndUnlockedNNewGames(String awardName, int newGameCount) {
		return "Fantastic!\n" +
				"You've earnt a " + awardName + " and unlocked " + newGameCount + " new games.";
	}
	
	public String getWhatDoYouWantToDo() {
		return "What do you want to do?";
	}

	public String getKeepPlayingForAnAward(String awardName) {
		return "Keep playing for a " + awardName;
	}

	public String getKeepPlayingJustForFun() {
		return "Keep playing just for fun";
	}

	public String getPlaySomethingElse() {
		return "Play something else";
	}
}

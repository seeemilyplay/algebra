package seeemilyplay.algebra.games.text;

public interface GamePhrases {

	public String getTutorial();

	public String getPlay();
	
	public String getYouveEarntAnAward(String awardName);
	
	public String getYouveEarntAnAwardAndUnlockedANewGame(String awardName);
	
	public String getYouveEarntAnAwardAndUnlockedNNewGames(String awardName, int newGameCount);
	
	public String getWhatDoYouWantToDo();
	
	public String getKeepPlayingForAnAward(String awardName);
	
	public String getKeepPlayingJustForFun();
	
	public String getPlaySomethingElse();
}

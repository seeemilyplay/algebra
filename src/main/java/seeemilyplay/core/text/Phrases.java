package seeemilyplay.core.text;

/**
 * The phrases found in the <code>PhraseBook</code>.
 *
 * @deprecated
 */
public interface Phrases {

	public String getGameName();

	public String getExitButtonLabel();

	public String getLogOutButtonLabel();

	public String getBackButtonLabel();

	public String getStartGameButtonLabel();

	public String getPlayAgainButtonLabel();

	public String getStopButtonLabel();

	public String getTutorialButtonLabel();

	public String getSomethingElseButtonLabel();

	public String getFinishButtonLabel();

	public String getBeginnerLevelAwardName();

	public String getIntermediateLevelAwardName();

	public String getExpertLevelAwardName();

	public String getYouGotXPoints(int x);

	public String getWellDoneBestSoFar();

	public String getWellDoneAllCorrect();

	public String getYouWonA(String award);

	public String getWhatsYourNameQuestion();

	public String getNoNameWarning();

	public String getNameExistsWarning();

	public String getAreYouQuestion(String name);

	public String getYesIAmButtonText();

	public String getNoImSomeoneElseButtonText();

	public String getWhoAreYouQuestion();

	public String getChooseAGame();

	public String getImSomeoneNewButtonText();

	public String getUseOfSymbolsCategoryName();

	public String getSequencesCategoryName();

	public String getLikeTermsGameName();

	public String getMultiplyingTermsGameName();

	public String getPlusOrMinusGameName();

	public String getSimplifyingExpressionsTaskName();

	public String getSequenceCrosswordName();

	public String getGeneratingSequencesGameName();

	public String getSequenceEquationsGameName();

	public String getSequenceCrosswordInstructions();

	public String getSequenceCrosswordTutorialIntro();

	public String getSequenceCrosswordTutorialOutro();

	public String getSequenceCrosswordTutorialQ1Hint();

	public String getSequenceCrosswordTutorialQ1Correction();

	public String getSequenceCrosswordTutorialQ2Hint();

	public String getSequenceCrosswordTutorialQ2Correction();

	public String getSequenceCrosswordTutorialQ3Hint();

	public String getSequenceCrosswordTutorialQ3Correction();

	public String getSequenceCrosswordTutorialQ4Hint();

	public String getSequenceCrosswordTutorialQ4Correction();

	public String getGeneratingSequencesInstructions();

	public String getGeneratingSequencesTutorialIntro();

	public String getGeneratingSequencesTutorialOutro();

	public String getGeneratingSequencesTutorialQ1Hint();

	public String getGeneratingSequencesTutorialQ1Correction();

	public String getGeneratingSequencesTutorialQ2Hint();

	public String getGeneratingSequencesTutorialQ2Correction();

	public String getGeneratingSequencesTutorialQ3Hint();

	public String getGeneratingSequencesTutorialQ3Correction();

	public String getGeneratingSequencesTutorialQ4Hint();

	public String getGeneratingSequencesTutorialQ4Correction();

	public String getSequenceEquationsTutorialIntro();

	public String getSequenceEquationsTutorialCorrection();

	public String getSequenceEquationsTutorialOutro();

	public String getRuleLabel();

	public String getHintLabel();

	public String getSquareNumbersRule();

	public String getSquareNumberHint();

	public String getSquareNumbersStartsWithNRule(int n);

	public String getEvenNumbersRule();

	public String getEvenNumbersStartWithNRule(int n);

	public String getEvenNumbersHint();

	public String getOddNumbersRule();

	public String getOddNumbersStartWithNRule(int n);

	public String getOddNumbersHint();

	public String getAddXEachTimeRule(int x);

	public String getAddXEachTimeHint(int x);

	public String getAddXEachTimeStartWithNRule(int x, int n);

	public String getMinusXEachTimeRule(int x);

	public String getMinusXEachTimeHint(int x);

	public String getMinusXEachTimeStartWithNRule(int x, int n);

	public String getMultiplyByXEachTimeRule(int x);

	public String getMultiplyByXEachTimeHint(int x);

	public String getMultiplyByXEachTimeStartWithNRule(int x, int n);

	public String getNthTermRule(String expression);

	public String getNVariableName();

	public String getNthTermHint(String firstTermExpression, int firstResult);

	public String getYouGotSomeWrongText();

	public String getYouGotThemAllRightText();

	public String getYouveBeenAwarded(String awardName);

	public String getPlayingFor(String awardName);

	public String getOnX(String x);

	public String getPlayingForFun();

	public String getPlayAgainFor(String awardName);

	public String getThatsTheTopAward();

	public String getPlayAgainForFun();
}

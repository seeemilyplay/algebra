package seeemilyplay.algebra.sequences.crossword;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.base.BaseGameComponentsFactory;
import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.base.BaseInstructionsComponent;
import seeemilyplay.algebra.games.base.BaseTutorialComponent;
import seeemilyplay.algebra.sequences.crossword.text.CrosswordPhrases;
import seeemilyplay.core.international.text.PhraseBook;

final class CrosswordGameComponentsFactory implements BaseGameComponentsFactory {

	public JComponent createInstructionsComponent(BaseGameModel baseGameModel) {
		return new BaseInstructionsComponent(
				baseGameModel,
				getInstructions());
	}

	private String getInstructions() {
		return getPhrases().getInstructions();
	}

	private CrosswordPhrases getPhrases() {
		return PhraseBook.getPhrases(CrosswordPhrases.class);
	}

	public JComponent createTutorialComponent(BaseGameModel baseGameModel) {
		TutorialModel tutorialModel = new TutorialModel(baseGameModel);
		TutorialComponent tutorial = new TutorialComponent(tutorialModel);
		return new BaseTutorialComponent(baseGameModel, tutorial);
	}

	public JComponent createPlayComponent(BaseGameModel baseGameModel) {
		PlayModel playModel = createPlayModel(baseGameModel);
		return new PlayComponent(playModel);
	}

	private PlayModel createPlayModel(BaseGameModel baseGameModel) {
		PlayModelFactory factory = new PlayModelFactory(baseGameModel);
		return factory.createPlayModel();
	}
}

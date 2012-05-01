package seeemilyplay.algebra.sequences.jigsaw;

import javax.swing.JComponent;

import seeemilyplay.algebra.games.base.BaseGameComponentsFactory;
import seeemilyplay.algebra.games.base.BaseGameModel;
import seeemilyplay.algebra.games.base.BaseInstructionsComponent;
import seeemilyplay.algebra.sequences.jigsaw.text.JigsawPhrases;
import seeemilyplay.core.international.text.PhraseBook;

final class JigsawGameComponentsFactory implements BaseGameComponentsFactory {

	public JComponent createInstructionsComponent(BaseGameModel baseGameModel) {
		return new BaseInstructionsComponent(
				baseGameModel,
				getInstructions());
	}

	private String getInstructions() {
		return getPhrases().getInstructions();
	}

	private JigsawPhrases getPhrases() {
		return PhraseBook.getPhrases(JigsawPhrases.class);
	}

	public JComponent createTutorialComponent(BaseGameModel baseGameModel) {
//		TutorialModel tutorialModel = new TutorialModel(baseGameModel);
//		TutorialComponent tutorial = new TutorialComponent(tutorialModel);
//		return new BaseTutorialComponent(baseGameModel, tutorial);
		return null;
	}

	public JComponent createPlayComponent(BaseGameModel baseGameModel) {
//		PlayModel playModel = createPlayModel(baseGameModel);
//		return new PlayComponent(playModel);
		return null;
	}

//	private PlayModel createPlayModel(BaseGameModel baseGameModel) {
//		PlayModelFactory factory = new PlayModelFactory(baseGameModel);
//		return factory.createPlayModel();
//	}
}

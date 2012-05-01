package seeemilyplay.algebra.progress;

import seeemilyplay.algebra.progress.text.ProgressPhrases;
import seeemilyplay.algebra.progress.text.ProgressPhrasesFactory;
import seeemilyplay.core.international.text.PhraseBook;

public final class ProgressContext {

	private final ProgressDAO progressDao = new ProgressDAO();
	private final ProgressModel progressModel =
		new ProgressModelImpl(progressDao);
	private final AwardModel awardModel = new AwardModel();

	public ProgressContext() {
		super();
		initPhraseBook();
	}

	private void initPhraseBook() {
		PhraseBook.registerPhrases(
				ProgressPhrases.class,
				new ProgressPhrasesFactory());
	}

	public ProgressModel getUserProgressModel(String user) {
		return new UserProgressModel(user, progressModel);
	}

	public AwardModel getAwardModel() {
		return awardModel;
	}
}

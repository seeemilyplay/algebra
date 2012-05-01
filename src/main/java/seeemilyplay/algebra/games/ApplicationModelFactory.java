package seeemilyplay.algebra.games;

import seeemilyplay.algebra.games.selection.ApplicationModel;
import seeemilyplay.algebra.games.selection.GameSelectionModel;
import seeemilyplay.algebra.progress.AwardModel;
import seeemilyplay.algebra.progress.ProgressContext;
import seeemilyplay.algebra.progress.ProgressModel;
import seeemilyplay.algebra.users.UserContext;

final class ApplicationModelFactory {

	private final ProgressContext progressContext;
	private final GameSelectionModel gameSelectionModel;
	private final UserProgressModelFactory userProgressModelFactory;

	public ApplicationModelFactory(
			UserContext userContext,
			ProgressContext progressContext,
			GameSelectionModel gameSelectionModel) {
		this.progressContext = progressContext;
		this.gameSelectionModel = gameSelectionModel;
		this.userProgressModelFactory = new UserProgressModelFactory(
				userContext.getLogInModel(),
				progressContext);
	}

	public ApplicationModel createApplicationModel() {
		return new ApplicationModelImpl(
				getAwardModel(),
				getUserProgressModel(),
				gameSelectionModel);
	}

	private AwardModel getAwardModel() {
		return progressContext.getAwardModel();
	}

	private ProgressModel getUserProgressModel() {
		return userProgressModelFactory.createUserProgressModel();
	}
}

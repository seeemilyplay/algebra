package seeemilyplay.algebra.application;

import seeemilyplay.algebra.application.text.ApplicationPhrases;
import seeemilyplay.algebra.application.text.ApplicationPhrasesFactory;
import seeemilyplay.algebra.games.GamesContext;
import seeemilyplay.algebra.games.install.Installer;
import seeemilyplay.algebra.games.install.InstallerFactory;
import seeemilyplay.algebra.progress.ProgressContext;
import seeemilyplay.algebra.users.UserContext;
import seeemilyplay.core.db.Database;
import seeemilyplay.core.db.DatabaseConfig;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.threads.ThreadPool;


final class ApplicationContext {

	static final String DB_FOLDER = "db";
	private static final String DB_NAME = "progressDB";
	private static final String DATABASE_NAME = DB_FOLDER + "/" + DB_NAME;

	private final UserContext userContext;
	private final ProgressContext progressContext;
	private final GamesContext gamesContext;

	public ApplicationContext() {
		super();
		setupDatabaseConfig();
		userContext = new UserContext();
		progressContext = new ProgressContext();
		gamesContext = new GamesContext(
				userContext,
				progressContext);

		initApplicationPhrases();
		installGames();
	}

	private void initApplicationPhrases() {
		PhraseBook.registerPhrases(
				ApplicationPhrases.class,
				new ApplicationPhrasesFactory());
	}

	private void installGames() {
		Installer installer = createInstaller();
		installer.install(gamesContext);
	}

	private Installer createInstaller() {
		InstallerFactory factory = new InstallerFactory();
		return factory.createInstaller();
	}

	public void shutdown() {
		ThreadPool.getInstance().shutdown();
		Database.shutdown();
		System.exit(0);
	}

	private void setupDatabaseConfig() {
		DatabaseConfig config = Database.getConfig();
		config.setName(DATABASE_NAME);
		config.setPermanent(true);
	}

	public UserContext getUserContext() {
		return userContext;
	}

	public GamesContext getGamesContext() {
		return gamesContext;
	}
}

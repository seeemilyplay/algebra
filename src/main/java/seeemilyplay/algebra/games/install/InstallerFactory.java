package seeemilyplay.algebra.games.install;

public final class InstallerFactory {

	public Installer createInstaller() {
		return new InstallerImpl();
	}
}

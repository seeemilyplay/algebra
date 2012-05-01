package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.progress.Award;
import seeemilyplay.algebra.progress.Level;

final class StatusManager<T> {

	private final ApplicationModel applicationModel;
	private final StatusCache<T> statusCache;
	private final UnlockMonitor<T> unlockMonitor;

	public StatusManager(
			ApplicationModel applicationModel,
			StatusCalculator<T> statusCalculator,
			UnlockNoticeFactory<T> unlockNoticeFactory) {
		this.applicationModel = applicationModel;
		this.statusCache = new StatusCache<T>(statusCalculator);
		this.unlockMonitor = new UnlockMonitor<T>(unlockNoticeFactory);
	}

	public void clear() {
		statusCache.clear();
	}

	public Level getLevel(T id) {
		return statusCache.getLevel(id);
	}

	public boolean isUnlocked(T id) {
		boolean isUnlocked = statusCache.isUnlocked(id);
		updateUnlockMonitor(id, isUnlocked);
		return isUnlocked;
	}
	
	private void updateUnlockMonitor(T id, boolean isUnlocked) {
		unlockMonitor.setUnlockedState(id, isUnlocked);
	}

	public boolean isAwarded(T id) {
		Award award = getAward(id);
		return award!=null;
	}

	public Award getAward(T id) {
		Level level = getLevel(id);
		return applicationModel.getAward(level);
	}
}

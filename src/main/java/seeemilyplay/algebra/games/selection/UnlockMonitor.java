package seeemilyplay.algebra.games.selection;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.algebra.bulletin.BulletinBoard;
import seeemilyplay.algebra.bulletin.Notice;

final class UnlockMonitor<T> {

	private final UnlockNoticeFactory<T> unlockNoticeFactory;
	
	private final Map<T,Boolean> unlockedStates = new HashMap<T,Boolean>();
	
	public UnlockMonitor(UnlockNoticeFactory<T> unlockNoticeFactory) {
		this.unlockNoticeFactory = unlockNoticeFactory;
	}
	
	public void setUnlockedState(T thing, boolean isUnlocked) {
		boolean isUnlockNoticeRequired = isUnlockNoticeRequired(thing, isUnlocked);
		saveUnlockedState(thing, isUnlocked);
		if (isUnlockNoticeRequired) {
			fireUnlockNotice(thing);
		}
	}
	
	private boolean isUnlockNoticeRequired(T thing, boolean isUnlocked) {
		return (isUnlockedState(thing) 
				&& isUnlocked
				&& isDifferentUnlockedState(thing, isUnlocked));
	}
	
	private boolean isUnlockedState(T thing) {
		return unlockedStates.containsKey(thing);
	}
	
	private boolean isDifferentUnlockedState(
			T thing, 
			boolean isUnlocked) {
		return (getUnlockedState(thing)!=isUnlocked);
	}

	private boolean getUnlockedState(T thing) {
		return unlockedStates.get(thing);
	}
	
	private void saveUnlockedState(T thing, boolean isUnlocked) {
		unlockedStates.put(thing, isUnlocked);
	}
	
	private void fireUnlockNotice(T thing) {
		Notice notice = createNotice(thing);
		post(notice);
	}
	
	private Notice createNotice(T thing) {
		return unlockNoticeFactory.createUnlockNotice(thing);
	}
	
	private void post(Notice notice) {
		getBulletinBoard().post(notice);
	}
	
	private BulletinBoard getBulletinBoard() {
		return BulletinBoard.getInstance();
	}
}

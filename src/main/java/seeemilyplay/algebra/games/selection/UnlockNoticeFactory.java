package seeemilyplay.algebra.games.selection;

import seeemilyplay.algebra.bulletin.Notice;

interface UnlockNoticeFactory<T> {

	public Notice createUnlockNotice(T thing);
}

package seeemilyplay.algebra.games.bulletin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;

import seeemilyplay.algebra.bulletin.BulletinBoard;
import seeemilyplay.algebra.bulletin.NoticeListener;
import seeemilyplay.algebra.bulletin.notices.AwardNotice;
import seeemilyplay.algebra.bulletin.notices.UnlockedGameNotice;
import seeemilyplay.algebra.components.floating.FloatPosition;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.swing.SwingModel;

public final class BulletinModel extends SwingModel {
	
	private final JComponent backgroundComponent;
	private final FloatPosition floatPosition;
	private final BulletinBoard bulletinBoard;
	
	private AwardNotice awardNotice;
	
	private final List<UnlockedGameNotice> unlockedGameNotices = 
		new ArrayList<UnlockedGameNotice>();
	
	private boolean isContinueRequested;
	private boolean isCancelRequested;
	
	private NoticeListener<AwardNotice> awardNoticeListener;
	private NoticeListener<UnlockedGameNotice> unlockedGameNoticeListener;
	
	public BulletinModel(
			JComponent backgroundComponent) {
		this(backgroundComponent, FloatPosition.CENTRE);
	}
		
	public BulletinModel(
			JComponent backgroundComponent,
			FloatPosition floatPosition) {
		this.backgroundComponent = backgroundComponent;
		this.floatPosition = floatPosition;
		bulletinBoard = BulletinBoard.getInstance();
		
		initAwardNoticeListener();
		initUnlockedGameNoticeListener();
	}
	
	private void initAwardNoticeListener() {
		awardNoticeListener = new NoticeListener<AwardNotice>() {
			public void onPost(AwardNotice awardNotice) {
				setAwardNotice(awardNotice);
			}
		};
		bulletinBoard.addListener(AwardNotice.class, awardNoticeListener);
	}
	
	private void initUnlockedGameNoticeListener() {
		unlockedGameNoticeListener = new NoticeListener<UnlockedGameNotice>() {
			public void onPost(UnlockedGameNotice unlockedGameNotice) {
				addUnlockedGameNotice(unlockedGameNotice);
			}
		};
		bulletinBoard.addListener(UnlockedGameNotice.class, unlockedGameNoticeListener);
	}

	public JComponent getBackgroundComponent() {
		return backgroundComponent;
	}
	
	public FloatPosition getFloatPosition() {
		return floatPosition;
	}
	
	private void setAwardNotice(AwardNotice awardNotice) {
		this.awardNotice = awardNotice;
		fireChange();
	}
	
	private void addUnlockedGameNotice(UnlockedGameNotice unlockedGameNotice) {
		unlockedGameNotices.add(unlockedGameNotice);
		fireChange();
	}
	
	private void acknowledge() {
		acknowledgeAward();
		acknowledgeUnlockedGames();
	}

	public boolean isAward() {
		return awardNotice!=null;
	}
	
	public Award getAward() {
		return awardNotice.getAward();
	}
	
	public boolean isNextAward() {
		return awardNotice.isNextAward();
	}
	
	public Award getNextAward() {
		return awardNotice.getNextAward();
	}
	
	private void acknowledgeAward() {
		bulletinBoard.remove(awardNotice);
		awardNotice = null;
	}
	
	public boolean isUnlockedGame() {
		return !unlockedGameNotices.isEmpty();
	}

	public int getUnlockedGamesCount() {
		return unlockedGameNotices.size();
	}
	
	public Iterable<String> getUnlockedGames() {
		List<String> games = new ArrayList<String>();
		for (UnlockedGameNotice notice : unlockedGameNotices) {
			games.add(notice.getGame());
		}
		return Collections.unmodifiableList(games);
	}
	
	private void acknowledgeUnlockedGames() {
		unlockedGameNotices.clear();
	}
	
	public void requestCancel() {
		acknowledge();
		isCancelRequested = true;
		fireChange();
	}
	
	public boolean isCancelRequested() {
		boolean isCancelRequestedCopy = this.isCancelRequested;
		isCancelRequested = false;
		return isCancelRequestedCopy;
	}
	
	public void requestContinue() {
		acknowledge();
		isContinueRequested = true;
		fireChange();
	}
	
	public boolean isContinueRequested() {
		boolean isContinueRequestedCopy = this.isContinueRequested;
		isContinueRequested = false;
		return isContinueRequestedCopy;
	}
}

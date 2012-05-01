package seeemilyplay.algebra.games.bulletin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import seeemilyplay.algebra.components.floating.FloatPosition;
import seeemilyplay.algebra.components.floating.FloatingPane;
import seeemilyplay.algebra.components.floating.FloatingPaneModel;
import seeemilyplay.algebra.games.text.GamePhrases;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.components.Hyperlink;
import seeemilyplay.core.components.MultiLineLabel;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
public final class BulletinPane extends JComponent {

	private static final String ACTION_KEY = "playMore";
	private static final String NEWLINE = "\n";
	private static final int WIDTH = 400; 
	private static final int PADDING = 15;
	 
	private final BulletinModel bulletinModel;
	
	private FloatingPaneModel floatingPaneModel; 
	
	private Listener bulletinModelListener;
	
	
	public BulletinPane(
			BulletinModel bulletinModel) {
		this.bulletinModel = bulletinModel;
		
		initLayout();
		initBulletinModelListener();
	}
	
	private void initLayout() {
		setLayout(new BorderLayout());
		
		floatingPaneModel = new FloatingPaneModel(getBackgroundComponent());
		floatingPaneModel.animateBackgroundEnabling();
		FloatingPane floatingPane = new FloatingPane(floatingPaneModel);
		floatingPaneModel.animateFloatPositionChange(getFloatPosition());
		
		add(floatingPane);
	}
	
	private JComponent getBackgroundComponent() {
		return bulletinModel.getBackgroundComponent();
	}
	
	private FloatPosition getFloatPosition() {
		return bulletinModel.getFloatPosition();
	}
	
	private void initBulletinModelListener() {
		bulletinModelListener = new Listener() {
			public void onChange() {
				layoutBulletin();
			}
		};
		bulletinModel.addListener(bulletinModelListener);
		layoutBulletin();
	}
	
	private void layoutBulletin() {
		if (isAward()) {
			layoutAward();
		} else {
			layoutNoAward();
		}
	}
	
	private void layoutAward() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(wrap(getAwardLabel()));
		JPanel innerPanel = wrap(getPlayMoreHyperlink());
		innerPanel.add(Box.createHorizontalStrut(PADDING));
		panel.add(innerPanel);
		panel.add(wrap(getStopPlayHyperlink()));
		panel.add(Box.createVerticalStrut(PADDING));
		setAwardComponent(panel);
	}
	
	private JPanel wrap(JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.add(component);
		return panel;
	}
	
	private MultiLineLabel getAwardLabel() {
		return new MultiLineLabel(
				getAwardText(), 
				WIDTH, 
				getAwardIcon());
	}
	
	private String getAwardText() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAwardAndUnlockText()).append(NEWLINE);
		sb.append(getQueryText()).append(NEWLINE);
		return sb.toString();
	}
	
	private Hyperlink getPlayMoreHyperlink() {
		return new Hyperlink(getAndRegisterPlayMoreAction());
	}
	
	private Action getAndRegisterPlayMoreAction() {
		registerPlayMoreAction();
		return getPlayMoreAction();
	}
	
	private void registerPlayMoreAction() {
		getActionMap().put(ACTION_KEY, getPlayMoreAction());
		registerPlayMoreShortcutKey();
	}
	
	private void registerPlayMoreShortcutKey() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
				getPlayMoreShortcutKey(),
				ACTION_KEY);
	}
	
	private KeyStroke getPlayMoreShortcutKey() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	}
	
	private Action getPlayMoreAction() {
		return new AbstractAction(getPlayMoreText()) {
			public void actionPerformed(ActionEvent e) {
				requestContinue();
			}
		};
	}
	
	private void requestContinue() {
		bulletinModel.requestContinue();
	}
	
	private Hyperlink getStopPlayHyperlink() {
		return new Hyperlink(new AbstractAction(getStopPlayText()) {
			public void actionPerformed(ActionEvent e) {
				requestCancel();
			}
		});
	}
	
	private void requestCancel() {
		bulletinModel.requestCancel();
	}
	
	private String getAwardAndUnlockText() {
		if (!isUnlockedGame()) {
			return getYouveEarntAnAwardText();
		} else if (isUnlockedSingleGame()) {
			return getYouveEarntAnAwardAndUnlockedANewGameText();
		} else {
			return getYouveEarntAnAwardAndUnlockedMultipleNewGamesText();
		}
	}
	
	private String getQueryText() {
		return getWhatDoYouWantToDoText();
	}
	
	private String getPlayMoreText() {
		if (isNextAward()) {
			return getKeepPlayingForAnAwardText();
		} else {
			return getKeepPlayingJustForFunText();
		}
	}
	
	private String getStopPlayText() {
		return getPlaySomethingElseText();
	}
	
	private void setAwardComponent(JComponent awardComponent) {
		floatingPaneModel.animateFloatingComponentChange(getAwardName(), awardComponent);
	}
	
	private void layoutNoAward() {
		removeAwardComponent();
	}
	
	private void removeAwardComponent() {
		floatingPaneModel.animateFloatingComponentDisappears();
	}
	
	private boolean isAward() {
		return bulletinModel.isAward();
	}
	
	private String getAwardName() {
		return getAward().getName();
	}
	
	private Icon getAwardIcon() {
		return getAward().getSmallIcon();
	}
	
	private Award getAward() {
		return bulletinModel.getAward();
	}
	
	private boolean isNextAward() {
		return bulletinModel.isNextAward();
	}
	
	private Award getNextAward() {
		return bulletinModel.getNextAward();
	}
	
	private String getNextAwardName() {
		return getNextAward().getName();
	}
	
	private boolean isUnlockedGame() {
		return bulletinModel.isUnlockedGame();
	}
	
	private boolean isUnlockedSingleGame() {
		return bulletinModel.getUnlockedGamesCount()==1;
	}
	
	private int getUnlockedGamesCount() {
		return bulletinModel.getUnlockedGamesCount();
	}
	
	private String getYouveEarntAnAwardText() {
		return getGamePhrases().getYouveEarntAnAward(
				getAwardName());
	}
	
	private String getYouveEarntAnAwardAndUnlockedANewGameText() {
		return getGamePhrases().getYouveEarntAnAwardAndUnlockedANewGame(
				getAwardName());
	}
	
	private String getYouveEarntAnAwardAndUnlockedMultipleNewGamesText() {
		return getGamePhrases().getYouveEarntAnAwardAndUnlockedNNewGames(
				getAwardName(), 
				getUnlockedGamesCount());
	}
	
	private String getWhatDoYouWantToDoText() {
		return getGamePhrases().getWhatDoYouWantToDo();
	}
	
	private String getKeepPlayingForAnAwardText() {
		return getGamePhrases().getKeepPlayingForAnAward(
				getNextAwardName());
	}
	
	private String getKeepPlayingJustForFunText() {
		return getGamePhrases().getKeepPlayingJustForFun();
	}
	
	private String getPlaySomethingElseText() {
		return getGamePhrases().getPlaySomethingElse();
	}
	
	private GamePhrases getGamePhrases() {
		return PhraseBook.getPhrases(GamePhrases.class);
	}
}

package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import seeemilyplay.algebra.games.text.GamePhrases;
import seeemilyplay.algebra.games.text.GamePhrasesFactory;
import seeemilyplay.core.components.ShortcutButton;
import seeemilyplay.core.international.text.PhraseBook;

@SuppressWarnings("serial")
public final class PlayButton extends JComponent {
	
	private static final String SHORT_CUT_KEY = "play";

	private final boolean isShortcutKeyEnabled;
	private final BaseGameModel baseGameModel;

	public PlayButton(
			BaseGameModel baseGameModel) {
		this(baseGameModel, true);
	}
	
	public PlayButton(
			BaseGameModel baseGameModel,
			boolean isShortcutKeyEnabled) {
		this.baseGameModel = baseGameModel;
		this.isShortcutKeyEnabled = isShortcutKeyEnabled;

		installGamePhrases();
		initLayout();
		layoutAll();
	}

	private void installGamePhrases() {
		PhraseBook.registerPhrases(
				GamePhrases.class,
				new GamePhrasesFactory());
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void layoutAll() {
		add(createPlayButton());
	}

	private JComponent createPlayButton() {
		return (isShortcutKeyEnabled) ? createButtonWithShortcut() : createButtonWithoutShortcut();
	}
	
	private ShortcutButton createButtonWithShortcut() {
		return new Button();
	}
	
	private class Button extends ShortcutButton {
		
		public Button() {
			super(new Runnable() {
				public void run() {
					startPlay();
				}
			});
		}

		protected String getActionKey() {
			return SHORT_CUT_KEY;
		}

		protected Icon getIcon() {
			return null;
		}

		protected String getLabel() {
			return getPlayLabel();
		}

		protected KeyStroke getShortcutKey() {
			return KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		}

		protected void registerPhraseBook() {
			return;
		}
	}
	
	private JButton createButtonWithoutShortcut() {
		return new JButton(new PlayAction());
	}

	private class PlayAction extends AbstractAction {

		public PlayAction() {
			super(getPlayLabel());
		}

		public void actionPerformed(ActionEvent e) {
			startPlay();
		}
	}

	private String getPlayLabel() {
		return getGamePhrases().getPlay();
	}

	private void startPlay() {
		baseGameModel.setPlayingGame();
	}

	private GamePhrases getGamePhrases() {
		return PhraseBook.getPhrases(GamePhrases.class);
	}
}

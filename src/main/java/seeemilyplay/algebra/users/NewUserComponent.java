package seeemilyplay.algebra.users;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.core.components.ErrorLabel;
import seeemilyplay.core.components.LimitedSizeDocumentFilter;
import seeemilyplay.core.components.OkButton;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.listeners.Listener;

/**
 * Use to log in as a new user.
 */
@SuppressWarnings("serial")
final class NewUserComponent extends JComponent {

	private static final int COLS = 20;

	private final NewUserModel newUserModel;
	private Listener modelListener;

	private final Runnable saveTask = new SaveTask();
	private final JTextField textField = new JTextField();
	private final WarningPanel warningPanel = new WarningPanel();

	public NewUserComponent(NewUserModel newUserModel) {
		this.newUserModel = newUserModel;

		initLayout();
		layoutQuestion();
		layoutInput();
		layoutWarningPanel();
		initModelListener();
		focusOnTextField();
	}

	private void initLayout() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	private void layoutQuestion() {
		JPanel questionPanel = createPanel();
		add(questionPanel);

		JLabel questionLabel = createQuestionLabel();
		questionPanel.add(questionLabel);
	}

	private JLabel createQuestionLabel() {
		return new JLabel(getQuestion());
	}

	private void layoutInput() {
		JPanel inputPanel = createPanel();
		add(inputPanel);

		layoutTextField();
		inputPanel.add(textField);

		OkButton okButton = createOkButton();
		inputPanel.add(okButton);
	}

	private void layoutTextField() {
		LimitedSizeDocumentFilter.install(textField.getDocument(), COLS);
		textField.setColumns(COLS);
	}

	private OkButton createOkButton() {
		return new OkButton(saveTask);
	}

	private void layoutWarningPanel() {
		JPanel wrapperPanel = createPanel();
		add(wrapperPanel);

		wrapperPanel.add(warningPanel);
	}

	private void initModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				displayWarningIfRequired();
			}
		};
		newUserModel.addListener(modelListener);
	}

	private void displayWarningIfRequired() {
		warningPanel.displayWarningIfRequired();
	}

	private void focusOnTextField() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textField.requestFocusInWindow();
			}
		});
	}

	private class SaveTask implements Runnable {

		public SaveTask() {
			super();
		}

		public void run() {
			newUserModel.setSuggestedName(textField.getText());
			if (newUserModel.isValidSuggestion()) {
				newUserModel.saveNewUserAndLogin();
			}
		}
	}

	private class WarningPanel extends JComponent {

		private ErrorLabel noSuggestionMadeWarning =
			createNoSuggestionMadeWarning();
		private ErrorLabel existingSuggestionWarning =
			createExistingSuggestionWarning();

		public WarningPanel() {
			super();
			initLayout();
		}

		private void initLayout() {
			setLayout(new BorderLayout());
		}

		public Dimension getPreferredSize() {
			return max(
					noSuggestionMadeWarning.getPreferredSize(),
					existingSuggestionWarning.getPreferredSize());
		}

		public void displayWarningIfRequired() {
			removeAll();

			if (isNoSuggestionMade()) {
				layoutNoSuggestionMadeWarning();
			} else if (isExistingSuggestion()) {
				layoutExistingSuggestionWarning();
			} else {
				layoutOK();
			}

			validate();
			repaint();
		}

		private boolean isNoSuggestionMade() {
			return !newUserModel.isSuggestionMade();
		}

		private boolean isExistingSuggestion() {
			return newUserModel.isExistingSuggestion();
		}

		private void layoutNoSuggestionMadeWarning() {
			add(noSuggestionMadeWarning);
		}

		private void layoutExistingSuggestionWarning() {
			add(existingSuggestionWarning);
		}

		private void layoutOK() {
			return;
		}
	}

	private Dimension max(Dimension a, Dimension b) {
		return new Dimension(
				Math.max(a.width, b.width),
				Math.max(a.height, b.height));
	}

	private ErrorLabel createNoSuggestionMadeWarning() {
		return new ErrorLabel(getNoNameWarning());
	}

	private ErrorLabel createExistingSuggestionWarning() {
		return new ErrorLabel(getNameExistsWarning());
	}

	private String getNoNameWarning() {
		return getUserPhrases().getEnterYourNameWarning();
	}

	private String getNameExistsWarning() {
		return getUserPhrases().getNameUsedWarning();
	}

	private String getQuestion() {
		return getUserPhrases().getWhatsYourName();
	}

	private UserPhrases getUserPhrases() {
		return PhraseBook.getPhrases(UserPhrases.class);
	}

	private JPanel createPanel() {
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		return questionPanel;
	}
}

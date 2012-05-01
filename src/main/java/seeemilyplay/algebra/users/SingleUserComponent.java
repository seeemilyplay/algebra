package seeemilyplay.algebra.users;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.core.components.Hyperlink;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * Used to log in when there is just a single existing user.
 */
@SuppressWarnings("serial")
final class SingleUserComponent extends JComponent {

	private static final int INDENT_SIZE = 20;

	private final SingleUserModel singleUserModel;

	public SingleUserComponent(SingleUserModel singleUserModel) {
		this.singleUserModel = singleUserModel;

		initLayout();
		layoutQuestion();
		layoutYesReply();
		layoutNoReply();
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

	private void layoutYesReply() {
		JPanel yesPanel = createPanel();
		add(yesPanel);

		addIndent(yesPanel);

		Hyperlink yesLink = createYesIAmHyperlink();
		yesPanel.add(yesLink);
	}

	private void layoutNoReply() {
		JPanel noPanel = createPanel();
		add(noPanel);

		addIndent(noPanel);

		Hyperlink noLink = createNoIAmSomeoneElseHyperlink();
		noPanel.add(noLink);
	}
	private Hyperlink createYesIAmHyperlink() {
		return new Hyperlink(new YesAction());
	}

	private Hyperlink createNoIAmSomeoneElseHyperlink() {
		return new Hyperlink(new NoAction());
	}

	private class YesAction extends AbstractAction {

		public YesAction() {
			super(getYesIAm());
		}

		public void actionPerformed(ActionEvent e) {
			loginAsSingleUser();
		}
	}

	private void loginAsSingleUser() {
		singleUserModel.loginAsSingleUser();
	}

	private class NoAction extends AbstractAction {

		public NoAction() {
			super(getNoIAmSomeoneElse());
		}

		public void actionPerformed(ActionEvent e) {
			createNewUser();
		}
	}

	private void createNewUser() {
		singleUserModel.createNewUser();
	}

	private String getQuestion() {
		String singleUserName = getSingleUserName();
		return getUserPhrases().getAreYou(singleUserName);
	}

	private String getSingleUserName() {
		return singleUserModel.getSingleUserName();
	}

	private String getYesIAm() {
		return getUserPhrases().getYesIAm();
	}

	private String getNoIAmSomeoneElse() {
		return getUserPhrases().getNoIAmSomeoneElse();
	}

	private UserPhrases getUserPhrases() {
		return PhraseBook.getPhrases(UserPhrases.class);
	}

	private void addIndent(JComponent c) {
		c.add(createIndent());
	}

	private Component createIndent() {
		return Box.createHorizontalStrut(INDENT_SIZE);
	}

	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		return panel;
	}
}

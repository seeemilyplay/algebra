package seeemilyplay.algebra.users;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import seeemilyplay.algebra.users.text.UserPhrases;
import seeemilyplay.core.components.Hyperlink;
import seeemilyplay.core.international.text.PhraseBook;

/**
 * Used to login when there are multiple existing users
 * to choose from.
 */
@SuppressWarnings("serial")
final class MultipleUserComponent extends JComponent {

	private static final int SCROLL_BAR_HEIGHT = 400;
	private static final int SCROLL_UNIT = 20;
	private static final int INDENT_SIZE = 20;

	private final MultipleUserModel multipleUserModel;

	public MultipleUserComponent(MultipleUserModel multipleUserModel) {
		this.multipleUserModel = multipleUserModel;

		initLayout();
		layoutQuestion();
		layoutUserOptions();
		layoutNewUserOption();
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
		return new JLabel(getWhoAreYou());
	}

	private void layoutUserOptions() {
		JPanel userPanel = createUserPanel();
		JScrollPane userScrollPane = wrapInScrollPane(userPanel);
		add(userScrollPane);
	}

	private JPanel createUserPanel() {
		JPanel userPanel = createListPanel();

		for (User user : multipleUserModel.getUsers()) {
			userPanel.add(createUserOption(user));
		}

		return userPanel;
	}

	private JPanel createUserOption(User user) {
		JPanel optionPanel = createPanel();

		addIndent(optionPanel);

		Hyperlink hyperlink = createHyperlink(user);
		optionPanel.add(hyperlink);

		return optionPanel;
	}

	private Hyperlink createHyperlink(User user) {
		return new Hyperlink(new LoginAction(user));
	}

	private class LoginAction extends AbstractAction {

		private final User user;

		public LoginAction(User user) {
			super(getName(user));
			this.user = user;
		}

		public void actionPerformed(ActionEvent e) {
			logInAs(user);
		}
	}

	private String getName(User user) {
		UserId userId = user.getUserId();
		return userId.getName();
	}

	private void logInAs(User user) {
		multipleUserModel.loginAs(user);
	}

	private JScrollPane wrapInScrollPane(JPanel innerPanel) {
		JScrollPane scrollPane = new JScrollPane(innerPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
		scrollPane.setPreferredSize(new Dimension(
				innerPanel.getPreferredSize().width + scrollPane.getVerticalScrollBar().getPreferredSize().width,
				Math.min(innerPanel.getPreferredSize().height,
						SCROLL_BAR_HEIGHT)));
		return scrollPane;
	}

	private void layoutNewUserOption() {
		JPanel newUserPanel = createPanel();

		addIndent(newUserPanel);

		Hyperlink hyperlink = createNewUserHyperlink();
		newUserPanel.add(hyperlink);

		add(newUserPanel);
	}

	private Hyperlink createNewUserHyperlink() {
		return new Hyperlink(new CreateNewUserAction());
	}

	private class CreateNewUserAction extends AbstractAction {

		public CreateNewUserAction() {
			super(getImSomeoneNew());
		}

		public void actionPerformed(ActionEvent e) {
			createNewUser();
		}
	}

	private void createNewUser() {
		multipleUserModel.createNewUser();
	}

	private String getImSomeoneNew() {
		return getUserPhrases().getImSomeoneNew();
	}

	private String getWhoAreYou() {
		return getUserPhrases().getWhoAreYou();
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

	private JPanel createListPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		return panel;
	}
}

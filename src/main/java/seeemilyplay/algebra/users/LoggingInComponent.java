package seeemilyplay.algebra.users;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import seeemilyplay.core.components.BackButton;
import seeemilyplay.core.listeners.Listener;

/**
 * Used for logging in.
 */
@SuppressWarnings("serial")
final class LoggingInComponent extends JComponent {

	private final LoggingInModel loggingInModel;

	private Listener modelListener;

	public LoggingInComponent(
			LoggingInModel loggingInModel) {
		this.loggingInModel = loggingInModel;

		initLayout();
		initModelListener();
		layoutComponent();
	}

	private void initLayout() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	private void initModelListener() {
		this.modelListener = new Listener() {
			public void onChange() {
				layoutComponent();
			}
		};
		loggingInModel.addListener(modelListener);
	}

	private void layoutComponent() {
		removeAll();

		if (isCreatingFirstUser()) {
			layoutCreatingFirstUser();
		} else if (isCreatingAdditionalUser()) {
			layoutCreatingAdditionalUser();
		} else if (isSelectingFromSingleUser()) {
			layoutSelectingFromSingleUser();
		} else {
			layoutSelectingFromMultipleUsers();
		}

		validate();
		repaint();
	}

	private boolean isCreatingFirstUser() {
		return loggingInModel.isCreatingFirstUser();
	}

	private boolean isCreatingAdditionalUser() {
		return loggingInModel.isCreatingAdditionalUser();
	}

	private boolean isSelectingFromSingleUser() {
		return loggingInModel.isSelectingFromSingleUser();
	}

	private void layoutCreatingFirstUser() {
		add(createNewUserComponent());
	}

	private void layoutCreatingAdditionalUser() {
		JPanel innerPanel = createListPanel();
		add(innerPanel);

		innerPanel.add(createNewUserComponent());

		innerPanel.add(createCancelNewUserPanel());
	}

	private void layoutSelectingFromSingleUser() {
		add(createSingleUserComponent());
	}

	private void layoutSelectingFromMultipleUsers() {
		add(createMultipleUserComponent());
	}

	private JPanel createCancelNewUserPanel() {
		JPanel panel = createPanel();
		panel.add(createCancelNewUserButton());

		return panel;
	}

	private BackButton createCancelNewUserButton() {
		return new BackButton(new CancelNewUserTask());
	}

	private class CancelNewUserTask implements Runnable {

		public CancelNewUserTask() {
			super();
		}

		public void run() {
			loggingInModel.cancelUserCreation();
		}
	}

	private MultipleUserComponent createMultipleUserComponent() {
		MultipleUserModel multipleUserModel = createMultipleUserModel();
		return new MultipleUserComponent(multipleUserModel);
	}

	private SingleUserComponent createSingleUserComponent() {
		SingleUserModel singleUserModel = createSingleUserModel();
		return new SingleUserComponent(singleUserModel);
	}

	private NewUserComponent createNewUserComponent() {
		NewUserModel newUserModel = createNewUserModel();
		return new NewUserComponent(newUserModel);
	}

	private MultipleUserModel createMultipleUserModel() {
		return loggingInModel.createMultipleUserModel();
	}

	private SingleUserModel createSingleUserModel() {
		return loggingInModel.createSingleUserModel();
	}

	private NewUserModel createNewUserModel() {
		return loggingInModel.createNewUserModel();
	}

	private JPanel createListPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		return panel;
	}

	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		return panel;
	}
}

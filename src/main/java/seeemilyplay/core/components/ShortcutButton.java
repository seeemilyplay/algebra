package seeemilyplay.core.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.KeyStroke;

/**
 * A base for making shortcut button components.
 */
@SuppressWarnings("serial")
public abstract class ShortcutButton extends PhraseBookAwareComponent {

	private final Runnable task;
	private Action action;
	private JButton button;

	public ShortcutButton(Runnable task) {
		this.task = task;

		registerAction();
		registerShortcutKey();
		layoutComponent();
	}

	private void registerAction() {
		createAction();
		registerInActionMap();
	}

	private void createAction() {
		action = new ButtonAction();
	}

	private class ButtonAction extends AbstractAction {

		public ButtonAction() {
			super(getLabel(), getIcon());
		}

		public void actionPerformed(ActionEvent e) {
			task.run();
		}
	}

	protected abstract String getLabel();

	protected abstract Icon getIcon();

	private void registerInActionMap() {
		getActionMap().put(getActionKey(), action);
	}

	protected abstract String getActionKey();

	private void registerShortcutKey() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
				getShortcutKey(),
				getActionKey());
	}

	protected abstract KeyStroke getShortcutKey();

	private void layoutComponent() {
		setupLayout();
		layoutButton();
	}

	private void setupLayout() {
		setLayout(new FlowLayout(FlowLayout.LEADING));
	}

	private void layoutButton() {
		createButton();
		add(button);
	}

	private void createButton() {
		button = new JButton(action);
	}

	public void setBackground(Color background) {
		button.setBackground(background);
	}
}

package seeemilyplay.core.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * A warning box can be used to display errors
 * to the user in a pretty way.
 */
@SuppressWarnings("serial")
public final class ErrorLabel extends JComponent {

	private static final String ICON_KEY = "Icon.error";
	private static final String FOREGROUND_KEY = "Label.errorForeground";

	private final String text;
	private JLabel errorLabel;

	public ErrorLabel(String text) {
		this.text = text;

		createErrorLabel();
		setupForeground();
		layoutComponent();
	}

	private void createErrorLabel() {
		errorLabel = new JLabel(
				text,
				getIcon(),
				JLabel.TRAILING);
	}

	private Icon getIcon() {
		return UIManager.getIcon(ICON_KEY);
	}

	private void layoutComponent() {
		setLayout(new BorderLayout());
		add(errorLabel);
	}

	private void setupForeground() {
		setForeground(getDefaultForeground());
	}

	public void setForeground(Color fg) {
		super.setForeground(fg);
		errorLabel.setForeground(fg);
	}

	private Color getDefaultForeground() {
		return UIManager.getColor(FOREGROUND_KEY);
	}
}

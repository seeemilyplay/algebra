package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class WarningComponent extends JComponent {

	private final SquareModel squareModel;

	private Listener squareModelListener;

	public WarningComponent(SquareModel squareModel) {
		this.squareModel = squareModel;

		initLayout();
		initSquareModelListener();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		setOpaque(false);
	}

	private void initSquareModelListener() {
		squareModelListener = new Listener() {
			public void onChange() {
				layoutAll();
			}
		};
		squareModel.addListener(squareModelListener);
	}

	private void layoutAll() {
		removeAll();

		if (isWarningDisplayed()) {
			layoutWarning();
		}

		validate();
		repaint();
	}

	private boolean isWarningDisplayed() {
		return squareModel.isWarningDisplayed();
	}

	private void layoutWarning() {
		JPanel innerPanel = createPanel();
		innerPanel.add(getWarningLabel());
		add(innerPanel, BorderLayout.SOUTH);
	}

	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		panel.setOpaque(false);
		return panel;
	}

	private JComponent getWarningLabel() {
		return new JLabel(getWarningIcon());
	}

	private Icon getWarningIcon() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getErrorIcon();
	}
}

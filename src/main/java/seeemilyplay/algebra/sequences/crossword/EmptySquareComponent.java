package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;

@SuppressWarnings("serial")
final class EmptySquareComponent extends JComponent {

	public EmptySquareComponent() {
		super();

		initLayout();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void layoutAll() {
		JPanel panel = new JPanel();
		panel.setBackground(getShadow());
		add(panel);
	}

	private Color getShadow() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getShadowPaper();
	}
}

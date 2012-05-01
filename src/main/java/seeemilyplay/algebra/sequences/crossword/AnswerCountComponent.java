package seeemilyplay.algebra.sequences.crossword;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import seeemilyplay.core.listeners.Listener;


@SuppressWarnings("serial")
final class AnswerCountComponent extends JComponent {

	private static final int WIGGLE_ROOM = 50;

	private final CrosswordModel crosswordModel;
	private Listener modelListener;

	public AnswerCountComponent(CrosswordModel crosswordModel) {
		this.crosswordModel = crosswordModel;

		initLayout();
		initModelListener();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new FlowLayout(FlowLayout.TRAILING));
	}

	private void initModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				layoutAll();
			}
		};
		crosswordModel.addListener(modelListener);
	}

	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		return new Dimension(
				preferredSize.width + WIGGLE_ROOM,
				preferredSize.height);
	}

	private void layoutAll() {
		removeAll();

		add(getLabel());

		validate();
		repaint();
	}

	private JLabel getLabel() {
		return new JLabel(getText());
	}

	private String getText() {
		StringBuilder sb = new StringBuilder();
		sb.append(crosswordModel.getCorrectAnswerCount());
		sb.append("/");
		sb.append(crosswordModel.getQuestionCount());
		return sb.toString();
	}
}

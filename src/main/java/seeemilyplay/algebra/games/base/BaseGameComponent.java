package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
public final class BaseGameComponent extends JComponent {

	private final BaseGameModel baseGameModel;

	private Listener modelListener;

	public BaseGameComponent(
			BaseGameModel baseGameModel) {
		this.baseGameModel = baseGameModel;

		initLayout();
		initModelListener();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}

	private void initModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				layoutAll();
			}
		};
		baseGameModel.addListener(modelListener);
	}

	private void layoutAll() {
		removeAll();

		add(baseGameModel.getComponent());

		validate();
		repaint();
	}
}

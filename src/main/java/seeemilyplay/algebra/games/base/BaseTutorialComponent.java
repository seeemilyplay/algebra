package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public final class BaseTutorialComponent extends JComponent {

	private final BaseGameModel baseGameModel;
	private final JComponent tutorial;

	private JToolBar toolBar = new JToolBar();

	public BaseTutorialComponent(
			BaseGameModel baseGameModel,
			JComponent tutorial) {
		this.baseGameModel = baseGameModel;
		this.tutorial = tutorial;

		initLayout();

		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		initToolBar();
	}

	private void initToolBar() {
		toolBar.setLayout(new BorderLayout());
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
	}

	private void layoutAll() {
		layoutToolBar();

		layoutMainComponent();
	}

	private void layoutToolBar() {
		layoutBackButton();
		layoutPlayButton();
	}

	private void layoutBackButton() {
		JComponent backButton = createBackButton();
		toolBar.add(wrap(backButton), BorderLayout.WEST);
	}

	private JComponent createBackButton() {
		return new BackToInstructionsButton(baseGameModel);
	}

	private void layoutPlayButton() {
		JComponent playButton = createPlayButton();
		toolBar.add(wrap(playButton), BorderLayout.EAST);
	}

	private JComponent createPlayButton() {
		return new PlayButton(baseGameModel, false);
	}

	private void layoutMainComponent() {
		add(tutorial);
	}

	private JPanel wrap(JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(component);
		return panel;
	}
}

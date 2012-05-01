package seeemilyplay.algebra.games.base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.core.components.MultiLineLabel;

@SuppressWarnings("serial")
public final class BaseInstructionsComponent extends JComponent {

	private static final int INSTRUCTION_WIDTH = 400;

	private final BaseGameModel baseGameModel;
	private final String instructions;

	private final JToolBar toolBar = new JToolBar();
	private final JPanel innerPanel = new JPanel();
	private final JPanel instructionPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();

	public BaseInstructionsComponent(
			BaseGameModel baseGameModel,
			String instructions) {
		this.baseGameModel = baseGameModel;
		this.instructions = instructions;

		initLayout();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(mainPanel);

		innerPanel.setLayout(new BorderLayout());
		mainPanel.add(innerPanel);

		initToolBar();
		initInstructionPanel();
		initButtonPanel();
	}

	private void initToolBar() {
		toolBar.setLayout(new FlowLayout(FlowLayout.LEADING));
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
	}

	private void initInstructionPanel() {
		instructionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		instructionPanel.setBorder(getInstructionBorder());
		innerPanel.add(instructionPanel);
	}

	private void initButtonPanel() {
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		innerPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	private Border getInstructionBorder() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getDrawnBorder();
	}

	private void layoutAll() {
		layoutToolBar();
		layoutInstructions();
		layoutButtons();
	}

	private void layoutToolBar() {
		toolBar.add(createBackButton());
	}

	private JComponent createBackButton() {
		return new BackToGameChooserButton(baseGameModel);
	}

	private void layoutInstructions() {

		MultiLineLabel label = new MultiLineLabel(
				instructions,
				getInstructionWidth());
		instructionPanel.add(label);
	}

	private int getInstructionWidth() {
		return INSTRUCTION_WIDTH;
	}

	private void layoutButtons() {
		layoutTutorialButton();
		layoutPlayButton();
	}

	private void layoutTutorialButton() {
		buttonPanel.add(createTutorialButton());
	}

	private JComponent createTutorialButton() {
		return new TutorialButton(baseGameModel);
	}

	private void layoutPlayButton() {
		buttonPanel.add(createPlayButton());
	}

	private JComponent createPlayButton() {
		return new PlayButton(baseGameModel);
	}
}

package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import seeemilyplay.algebra.components.fading.FadingComponent;

@SuppressWarnings("serial")
final class CrosswordWithCounterComponent extends JComponent {

	private final CrosswordModel crosswordModel;
	private final CrosswordComponent crosswordComponent;
	private final AnswerCountComponent answerCountComponent;
	
	
	public CrosswordWithCounterComponent(CrosswordModel crosswordModel) {
		this.crosswordModel = crosswordModel;
		this.crosswordComponent = new CrosswordComponent(crosswordModel);
		this.answerCountComponent = new AnswerCountComponent(crosswordModel);
	
		initLayout();
		layoutAll();
	}
	
	private void initLayout() {
		setLayout(new BorderLayout());
	}
	
	private void layoutAll() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(crosswordComponent);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(answerCountComponent, BorderLayout.EAST);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		
		FadingComponent fadingComponent = 
			new FadingComponent(
					crosswordModel,
					panel);
		add(fadingComponent);
	}
}

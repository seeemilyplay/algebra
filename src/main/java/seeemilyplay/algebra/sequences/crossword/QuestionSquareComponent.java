package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;

@SuppressWarnings("serial")
final class QuestionSquareComponent extends JComponent {

	private final SquareModel squareModel;

	private JComponent answerInputComponent;
	private JComponent warningComponent;

	public QuestionSquareComponent(SquareModel squareModel) {
		this.squareModel = squareModel;

		initLayout();
		initAnswerInputComponent();
		initWarningComponent();
		layoutAll();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		
		setBorder(getDrawnBorder());
	}

	private Border getDrawnBorder() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getDrawnBorder();
	}
	
	private void initAnswerInputComponent() {
		answerInputComponent = new AnswerInputComponent(squareModel);
	}

	private void initWarningComponent() {
		warningComponent = new WarningComponent(squareModel);
	}

	private void layoutAll() {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(new LayeredLayout());
		layeredPane.add(answerInputComponent, new Integer(0));
		layeredPane.add(warningComponent, new Integer(1));
		add(layeredPane);
	}

	private class LayeredLayout implements LayoutManager {

		public LayeredLayout() {
			super();
		}

		public void removeLayoutComponent(Component comp) {
			return;
        }

        public void addLayoutComponent(String name, Component comp) {
        	return;
        }

        public void layoutContainer(Container parent) {
            if (isReadyToLayout(parent)) {
                layoutAll(parent);
            }
        }

        private boolean isReadyToLayout(Container parent) {
        	return parent!=null && hasAllComponents(parent);
        }

        private boolean hasAllComponents(Container parent) {
        	return parent.getComponents().length==2;
        }

        private void layoutAll(Container parent) {
        	Rectangle bounds = parent.getBounds();
        	answerInputComponent.setBounds(bounds);
        	warningComponent.setBounds(bounds);
        }
        
		public Dimension minimumLayoutSize(Container parent) {
			return answerInputComponent.getMinimumSize();
		}

		public Dimension preferredLayoutSize(Container parent) {
			return answerInputComponent.getPreferredSize();
		}
	}
}

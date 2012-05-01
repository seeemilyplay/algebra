package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Document;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.core.components.IntegerDocumentFilter;
import seeemilyplay.core.listeners.Listener;
import seeemilyplay.core.swing.DocumentAdapter;

@SuppressWarnings("serial")
final class AnswerInputComponent extends JComponent {

	private static final String LEFT = "Left";
	private static final String RIGHT = "Right";
	private static final String UP = "Up";
	private static final String DOWN = "Down";

	private static final int COLS = 3;
	private static final String MINUS = "-";

	private final SquareModel squareModel;
	
	private Listener squareModelListener;

	private CenteringLayoutManager centeringLayoutManager;
	private JPanel innerPanel = new JPanel();
	private JTextField answerField = new JTextField();

	private Listener desiredFocusListener;

	public AnswerInputComponent(SquareModel squareModel) {
		this.squareModel = squareModel;

		hookUpFocusManager();
		initLayout();
		layoutAnswerField();
		initBackgroundUpdating();
	}

	private void hookUpFocusManager() {
		initFocusHolderUpdates();
		initDesiredFocusListener();
		initFocusChangeActions();
	}

	private void initFocusHolderUpdates() {
		answerField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				setFocusHolder();
			}
		});
	}

	private void setFocusHolder() {
		getFocusManager().setFocusHolder();
	}

	private void initDesiredFocusListener() {
		desiredFocusListener = new Listener() {
			public void onChange() {
				requestFocusIfDesired();
			}
		};
		getFocusManager().addListener(desiredFocusListener);
	}

	private void requestFocusIfDesired() {
		if (isRequestFocusDesired()) {
			requestTheFocus();
		}
	}

	private boolean isRequestFocusDesired() {
		return getFocusManager().isDesiredFocusHolder();
	}

	private void requestTheFocus() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				answerField.requestFocusInWindow();
			}
		});
	}

	private void initFocusChangeActions() {
		initMoveFocusLeftAction();
		initMoveFocusRightAction();
		initMoveFocusUpAction();
		initMoveFocusDownAction();
	}

	private void initMoveFocusLeftAction() {
		registerFocusMoveAction(LEFT, new Runnable() {
			public void run() {
				moveFocusLeft();
			}
		});
		registerFocusMoveShortcut(LEFT, KeyEvent.VK_LEFT);
	}

	private void initMoveFocusRightAction() {
		registerFocusMoveAction(RIGHT, new Runnable() {
			public void run() {
				moveFocusRight();
			}
		});
		registerFocusMoveShortcut(RIGHT, KeyEvent.VK_RIGHT);
	}

	private void initMoveFocusUpAction() {
		registerFocusMoveAction(UP, new Runnable() {
			public void run() {
				moveFocusUp();
			}
		});
		registerFocusMoveShortcut(UP, KeyEvent.VK_UP);
	}

	private void initMoveFocusDownAction() {
		registerFocusMoveAction(DOWN, new Runnable() {
			public void run() {
				moveFocusDown();
			}
		});
		registerFocusMoveShortcut(DOWN, KeyEvent.VK_DOWN);
	}

	private void moveFocusLeft() {
		getFocusManager().moveFocusLeft();
	}

	private void moveFocusRight() {
		getFocusManager().moveFocusRight();
	}

	private void moveFocusUp() {
		getFocusManager().moveFocusUp();
	}

	private void moveFocusDown() {
		getFocusManager().moveFocusDown();
	}

	private void registerFocusMoveAction(
			String key,
			final Runnable command) {
		ActionMap actionMap = answerField.getActionMap();
		Action action = new AbstractAction(key) {
			public void actionPerformed(ActionEvent e) {
				command.run();
			}
		};
		actionMap.put(key, action);
	}

	private void registerFocusMoveShortcut(String actionKey, int key) {
		InputMap inputMap = answerField.getInputMap(WHEN_FOCUSED);
		inputMap.put(getKeyStroke(key), actionKey);
	}

	private KeyStroke getKeyStroke(int key) {
		return KeyStroke.getKeyStroke(key, 0);
	}

	private SquareFocusManager getFocusManager() {
		return squareModel.getFocusManager();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		
		centeringLayoutManager = new CenteringLayoutManager();
		innerPanel.setLayout(centeringLayoutManager);
		
		add(innerPanel);
		
		initAnswerField();
	}

	private void initAnswerField() {
		IntegerDocumentFilter.install(answerField.getDocument(), COLS);
		answerField.setColumns(COLS);
		answerField.setHorizontalAlignment(JTextField.CENTER);
		initAnswerFieldFocusBehaviour();
		initModelUpdateBehaviour();
	}

	private void initAnswerFieldFocusBehaviour() {
		answerField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				onFocusChange();
			}

			public void focusLost(FocusEvent e) {
				onFocusChange();
			}
		});
		onFocusChange();
	}

	private void onFocusChange() {
		updateAnswerFieldBorder();
		setMustDisplayAnyWarnings();
	}

	private void setMustDisplayAnyWarnings() {
		squareModel.setMustDisplayAnyWarnings();
	}

	private void initModelUpdateBehaviour() {
		Document document = answerField.getDocument();
		document.addDocumentListener(new DocumentAdapter() {
			public void onChange() {
				updateAnswer();
			}
		});
	}

	private void updateAnswer() {
		if (isAnswerFieldEmpty()) {
			clearAnswer();
		} else {
			int answer = getAnswerFieldValue();
			setAnswer(answer);
		}
	}

	private boolean isAnswerFieldEmpty() {
		return (!isAnswerFieldContainsText()
				|| isAnswerFieldIsJustAMinus());
	}

	private boolean isAnswerFieldContainsText() {
		String text = answerField.getText();
		return (text!=null
				&& text.length()>0);
	}

	private boolean isAnswerFieldIsJustAMinus() {
		String text = answerField.getText();
		return MINUS.equals(text);
	}

	private void clearAnswer() {
		squareModel.clearAnswer();
	}

	private int getAnswerFieldValue() {
		String text = answerField.getText();
		return Integer.parseInt(text);
	}

	private void setAnswer(int answer) {
		squareModel.setAnswer(answer);
	}

	private void updateAnswerFieldBorder() {
		if (answerField.isFocusOwner()) {
			answerField.setBorder(getUnderlineBorder());
		} else {
			answerField.setBorder(getEmptyBorder());
		}
	}

	private Border getUnderlineBorder() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getUnderlineBorder();
	}

	private Border getEmptyBorder() {
		return BorderFactory.createEmptyBorder(0, 0, 0, 0);
	}

	private void layoutAnswerField() {
		innerPanel.add(answerField);
		centeringLayoutManager.setComponent(answerField);

		validate();
		repaint();
	}	

	private void initBackgroundUpdating() {
		squareModelListener = new Listener() {
			public void onChange() {
				updateBackground();
			}
		};
		squareModel.addListener(squareModelListener);
		updateBackground();
	}

	private void updateBackground() {
		if (isComplete()) {
			setBackground(getCompleteColor());
		} else {
			setBackground(getUncompleteColor());
		}
	}
	
	public void setBackground(Color background) {
		super.setBackground(background);
		answerField.setBackground(background);
		innerPanel.setBackground(background);
	}
	
	private boolean isComplete() {
		return squareModel.isCorrectlyAnswered();
	}
	
	private Color getCompleteColor() {
		return getTheme().getPaper();
	}
	
	private Color getUncompleteColor() {
		return getTheme().getHighlightPaper();
	}
	
	private AlgebraTheme getTheme() {
		return AlgebraTheme.getInstance();
	}
}

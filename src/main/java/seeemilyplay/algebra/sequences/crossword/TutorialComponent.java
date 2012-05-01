package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import seeemilyplay.algebra.components.arrow.ArrowDirection;
import seeemilyplay.algebra.components.arrow.ArrowPane;
import seeemilyplay.algebra.components.arrow.ArrowPaneModel;
import seeemilyplay.algebra.components.floating.FloatPosition;
import seeemilyplay.algebra.components.floating.FloatingPane;
import seeemilyplay.algebra.components.floating.FloatingPaneModel;
import seeemilyplay.algebra.lookandfeel.AlgebraTheme;
import seeemilyplay.algebra.sequences.crossword.text.CrosswordPhrases;
import seeemilyplay.core.components.MultiLineLabel;
import seeemilyplay.core.components.OkButton;
import seeemilyplay.core.international.text.PhraseBook;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class TutorialComponent extends JComponent {

	private static final String INTRO = "intro";
	private static final String OUTRO = "outro";

	private static final int INFO_WIDTH = 300;

	private final TutorialModel tutorialModel;

	private final TutorialQuestionHandlers questionHandlers =
		new TutorialQuestionHandlers();

	private ArrowPaneModel arrowPaneModel =
		new ArrowPaneModel();
	private ArrowPane arrowPane;
	private FloatingPaneModel floatingPaneModel;

	private JPanel infoPanel;
	private JPanel mainPanel = new JPanel();
	private CrosswordComponent crosswordComponent;

	private Listener modelListener;

	public TutorialComponent(TutorialModel tutorialModel) {
		this.tutorialModel = tutorialModel;

		initLayout();
		layoutMainPanel();
		initArrowPane();
		initFloatingPane();
		layoutInfoPanel();
		initModelListener();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	private void initArrowPane() {
		arrowPane = new ArrowPane(
				arrowPaneModel,
				mainPanel);
	}

	private void initFloatingPane() {
		floatingPaneModel = new FloatingPaneModel(
				arrowPane);
		FloatingPane floatingPane = new FloatingPane(
				floatingPaneModel);
		add(floatingPane);
	}

	private void initModelListener() {
		modelListener = new Listener() {
			public void onChange() {
				layoutInfoPanel();
			}
		};
		tutorialModel.addListener(modelListener);
		layoutInfoPanel();
	}

	private void layoutMainPanel() {
		mainPanel.removeAll();

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());

		createCrosswordComponent();
		innerPanel.add(crosswordComponent);
		innerPanel.add(createButtonPanel(), BorderLayout.SOUTH);

		mainPanel.add(innerPanel);

		mainPanel.validate();
		mainPanel.repaint();
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JComponent answerCountComponent = createAnswerCountComponent();
		buttonPanel.add(answerCountComponent);

		return buttonPanel;
	}

	private void createCrosswordComponent() {
		CrosswordModel crosswordModel = getCrosswordModel();
		crosswordComponent = new CrosswordComponent(crosswordModel);
	}

	private JComponent createAnswerCountComponent() {
		CrosswordModel crosswordModel = getCrosswordModel();
		return new AnswerCountComponent(crosswordModel);
	}

	private CrosswordModel getCrosswordModel() {
		return tutorialModel.getCrosswordModel();
	}

	private void layoutInfoPanel() {

		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());

		if (!tutorialModel.isSeenIntro()) {
			layoutIntro();
		} else if (tutorialModel.isFinished()) {
			layoutOutro();
		} else {
			layoutQuestion();
		}
	}

	private void layoutIntro() {

		MultiLineLabel label = new MultiLineLabel(
				getIntroText(),
				INFO_WIDTH);
		infoPanel.add(label);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.add(createOkButton());

		infoPanel.add(buttonPanel, BorderLayout.SOUTH);


		setUnavoidableInfo(INTRO);
	}

	private String getIntroText() {
		CrosswordPhrases phrases = getPhrases();
		return phrases.getTutorialIntro();
	}

	private JComponent createOkButton() {
		OkButton okButton = new OkButton(new Runnable() {
			public void run() {
				setSeenIntro();
			}
		});
		return okButton;
	}

	private void setSeenIntro() {
		tutorialModel.setSeenIntro();
	}

	private void layoutOutro() {
		MultiLineLabel label = new MultiLineLabel(
				getOutroText(),
				INFO_WIDTH);
		infoPanel.add(label);
		
		JComponent playButton = createPlayButton();
		infoPanel.add(playButton, BorderLayout.SOUTH);

		setUnavoidableInfo(OUTRO);
	}
	
	private JComponent createPlayButton() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panel.add(tutorialModel.createPlayButton());
		return panel;
	}

	private void setUnavoidableInfo(String floatingComponentId) {
		dontPoint();
		disableBackground();
		centreInfo();
		updateFloatingComponent(floatingComponentId);
	}
	
	private void updateFloatingComponent(String floatingComponentId) {
		floatingPaneModel.animateFloatingComponentChange(
				floatingComponentId,
				infoPanel);
	}

	private String getOutroText() {
		CrosswordPhrases phrases = getPhrases();
		return phrases.getTutorialOutro();
	}

	private void layoutQuestion() {
		if (isCorrection()) {
			layoutCorrection();
		} else {
			layoutHint();
		}

		
		enableBackground();
		moveInfo();
		updateFloatingComponent(getQuestionId());
		pointToQuestion();
	}

	private String getQuestionId() {
		StringBuilder sb = new StringBuilder();
		sb.append(getQuestionIndex());
		sb.append(isCorrection());
		return sb.toString();
	}

	private boolean isCorrection() {
		return tutorialModel.isWarningDisplayed();
	}

	private int getQuestionIndex() {
		return tutorialModel.getQuestionIndex();
	}

	private SquareModel getQuestion() {
		return tutorialModel.getQuestion();
	}

	private void layoutCorrection() {
		Icon errorIcon = getErrorIcon();
		String text = getCorrectionText();
		MultiLineLabel label = new MultiLineLabel(
				text,
				INFO_WIDTH,
				errorIcon);
		infoPanel.add(label);
	}

	private Icon getErrorIcon() {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		return theme.getErrorIcon();
	}

	private String getCorrectionText() {
		return getQuestionHandler().getCorrectionText();
	}

	private void layoutHint() {
		String text = getHintText();
		MultiLineLabel label = new MultiLineLabel(
				text,
				INFO_WIDTH);
		infoPanel.add(label);
	}

	private String getHintText() {
		return getQuestionHandler().getHintText();
	}

	private void dontPoint() {
		arrowPaneModel.clearAttention();
	}

	private void pointToQuestion() {
		arrowPaneModel.setAttention(
				getArrowDirection(),
				getQuestionComponent());
	}

	private ArrowDirection getArrowDirection() {
		return getQuestionHandler().getArrowDirection();
	}

	private JComponent getQuestionComponent() {
		Square square = getQuestion().getSquare();
		return crosswordComponent.getSquareComponent(square);
	}

	private void disableBackground() {
		floatingPaneModel.animateBackgroundDisabling();
	}

	private void enableBackground() {
		floatingPaneModel.animateBackgroundEnabling();
	}

	private void moveInfo() {
		moveInfo(getInfoPostion());
	}

	private void centreInfo() {
		moveInfo(FloatPosition.ON_SCREEN_MID_LINE);
	}

	private void moveInfo(FloatPosition position) {
		floatingPaneModel.animateFloatPositionChange(position);
	}

	private FloatPosition getInfoPostion() {
		return getQuestionHandler().getInfoPosition();
	}

	private TutorialQuestionHandler getQuestionHandler() {
		int questionIndex = tutorialModel.getQuestionIndex();
		return questionHandlers.get(questionIndex);
	}

	private CrosswordPhrases getPhrases() {
		return PhraseBook.getPhrases(CrosswordPhrases.class);
	}
}


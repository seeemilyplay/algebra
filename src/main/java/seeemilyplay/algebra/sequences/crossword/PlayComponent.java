package seeemilyplay.algebra.sequences.crossword;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import seeemilyplay.algebra.games.bulletin.BulletinModel;
import seeemilyplay.algebra.games.bulletin.BulletinPane;
import seeemilyplay.algebra.goals.GoalComponent;
import seeemilyplay.algebra.goals.GoalModel;
import seeemilyplay.core.components.QuitButton;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class PlayComponent extends JComponent {

	private final PlayModel playModel;

	private Listener playModelListener;

	private JToolBar toolBar = new JToolBar();
	private JPanel goalPanel = new JPanel();
	private JPanel centralPanel = new JPanel();


	public PlayComponent(PlayModel playModel) {
		this.playModel = playModel;

		initLayout();
		initPlayModelListener();
		layoutCentralPanel();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		layoutToolBar();
		layoutGoalPanel();
		layoutMainPanel();
	}
	
	private void layoutGoalPanel() {
		goalPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		goalPanel.add(createGoalComponent());
	}
	
	private void layoutMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(goalPanel, BorderLayout.WEST);
		mainPanel.add(createSameSizeArea(goalPanel), BorderLayout.EAST);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		centralPanel.setLayout(new BorderLayout());
		
		BulletinPane bulletinPane = new BulletinPane(createBulletinModel());

		innerPanel.add(bulletinPane);
		mainPanel.add(innerPanel);

		JScrollPane scrollPane = new JScrollPane(mainPanel);
		add(scrollPane);
	}
	
	private BulletinModel createBulletinModel() {
		return playModel.createBulletinModel(centralPanel);
	}

	private void layoutToolBar() {
		toolBar.setFloatable(false);
		toolBar.setLayout(new FlowLayout(FlowLayout.TRAILING));

		toolBar.add(createQuitButton());

		add(toolBar, BorderLayout.NORTH);
	}

	private JComponent createQuitButton() {
		return new QuitButton(new CancelPlayTask());
	}

	private class CancelPlayTask implements Runnable {

		public CancelPlayTask() {
			super();
		}

		public void run() {
			cancelPlay();
		}
	}

	private void cancelPlay() {
		playModel.cancelPlay();
	}

	private void initPlayModelListener() {
		playModelListener = new Listener() {
			public void onChange() {
				layoutCentralPanel();
			}
		};
		playModel.addListener(playModelListener);
	}

	private void layoutCentralPanel() {
		centralPanel.removeAll();

		centralPanel.add(createCrosswordComponent());

		centralPanel.validate();
		centralPanel.repaint();
	}

	private JComponent createCrosswordComponent() {
		CrosswordModel crosswordModel = getCrosswordModel();
		return new CrosswordWithCounterComponent(crosswordModel);
	}

	private CrosswordModel getCrosswordModel() {
		return playModel.getCrosswordModel();
	}

	private JComponent createGoalComponent() {
		return new GoalComponent(getGoalModel());
	}

	private GoalModel getGoalModel() {
		return playModel.getGoalModel();
	}

	private Component createSameSizeArea(JComponent component) {
		return Box.createRigidArea(component.getPreferredSize());
	}
}

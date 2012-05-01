package seeemilyplay.algebra.application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import seeemilyplay.core.components.ExitButton;
import seeemilyplay.core.listeners.Listener;


@SuppressWarnings("serial")
final class ApplicationComponent extends JComponent {

	private final ApplicationModel applicationModel;
	private Listener applicationModelListener;

	private JToolBar toolBar = new JToolBar();
	private JPanel applicationPanel = new JPanel();

	public ApplicationComponent(
			ApplicationModel applicationModel) {
		this.applicationModel = applicationModel;

		initLayout();
		initApplicationModelListener();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		initApplicationLayout();
		initToolBarLayout();
	}

	private void initApplicationLayout() {
		applicationPanel.setLayout(new BorderLayout());
		add(applicationPanel);
	}

	private void initToolBarLayout() {
		toolBar.setLayout(new FlowLayout(FlowLayout.TRAILING));
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
	}

	private void initApplicationModelListener() {
		applicationModelListener = new Listener() {
			public void onChange() {
				layoutApp();
			}
		};
		applicationModel.addListener(applicationModelListener);
		layoutApp();
	}

	private void layoutApp() {

		clear();

		if (applicationModel.isLoggingIn()) {
			layoutLoggingIn();
		} else if (applicationModel.isChoosingGame()) {
			layoutGameChooser();
		} else {
			layoutGamePlay();
		}

		validate();
		repaint();
	}

	private void clear() {
		clearToolBar();
		clearApplicationPanel();
	}

	private void clearToolBar() {
		toolBar.removeAll();
		toolBar.setLayout(new FlowLayout(FlowLayout.TRAILING));
	}

	private void clearApplicationPanel() {
		applicationPanel.removeAll();
	}

	private void layoutLoggingIn() {

		addExitButtonToToolBar();

		applicationPanel.add(getLoggingInComponent());
	}

	private JComponent getLoggingInComponent() {
		return applicationModel.createLoggingInComponent();
	}

	private void layoutGameChooser() {

		addExitButtonToToolBar();

		applicationPanel.add(getGameChooserComponent());
	}

	private JComponent getGameChooserComponent() {
		return applicationModel.getGameChooserComponent();
	}

	private void addExitButtonToToolBar() {
		toolBar.add(createExitButton());
	}

	private JComponent createExitButton() {
		ExitButton exitButton = new ExitButton(new Runnable() {
			public void run() {
				shutdown();
			}
		});
		return exitButton;
	}

	private void shutdown() {
		applicationModel.shutdown();
	}

	private void layoutGamePlay() {

		applicationPanel.add(getGamePlayComponent());
	}

	private JComponent getGamePlayComponent() {
		return applicationModel.createGamePlayComponent();
	}
}

package seeemilyplay.algebra.games.selection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.algebra.progress.Award;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
final class CategoryComponent extends JComponent {

	private static final int LABEL_VERTICAL_PADDING = 6;
	private static final int LABEL_HORIZONTAL_PADDING = 6;
	private static final Border PADDING = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private final CategoryModel categoryModel;

	private Listener categoryModelListener;

	public CategoryComponent(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;

		initLayout();
		initCategoryModelListener();
		layoutNode();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		setBorder(PADDING);
	}

	private void initCategoryModelListener() {
		categoryModelListener = new Listener() {
			public void onChange() {
				layoutNode();
			}
		};
		categoryModel.addListener(categoryModelListener);
	}

	private void layoutNode() {
		removeAll();

		if (isSelected()) {
			layoutSelected();
		} else {
			layoutUnselected();
		}

		validate();
		repaint();
	}

	private void layoutSelected() {
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout(
				FlowLayout.CENTER,
				LABEL_HORIZONTAL_PADDING,
				LABEL_VERTICAL_PADDING));
		innerPanel.add(createLabel());
		add(innerPanel);
	}


	private void layoutUnselected() {
		add(createButton());
	}

	private boolean isSelected() {
		return categoryModel.isSelectedCategory();
	}

	public boolean isEnabled() {
		return categoryModel.isUnlocked();
	}

	private JButton createButton() {
		JButton button = new JButton(
				new SelectCategoryAction());
		button.setEnabled(isEnabled());
		return button;
	}

	private JLabel createLabel() {
		JLabel label = new JLabel(
				getText(),
				getIcon(),
				JLabel.LEADING);
		return label;
	}

	private class SelectCategoryAction extends AbstractAction {

		public SelectCategoryAction() {
			super(getText(), getIcon());
		}

		public void actionPerformed(ActionEvent e) {
			selectCategory();
		}
	}

	private String getText() {
		CategoryId category = categoryModel.getCategoryId();
		return category.getName();
	}

	private Icon getIcon() {
		if (categoryModel.isAward()) {
			Award award = categoryModel.getAward();
			return award.getSmallIcon();
		}
		return null;
	}

	private void selectCategory() {
		categoryModel.selectCategory();
	}
}

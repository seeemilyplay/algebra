package seeemilyplay.algebra.games.selection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import seeemilyplay.algebra.games.core.Categories;
import seeemilyplay.algebra.games.core.Category;
import seeemilyplay.algebra.games.core.CategoryId;
import seeemilyplay.core.listeners.Listener;

@SuppressWarnings("serial")
public final class GameChooserComponent extends JComponent {

	private static final Border PADDING_BORDER = BorderFactory.createEmptyBorder(
			10, 20, 50, 60);

	private final GameChooserModel gameChooserModel;

	private final JPanel categoryPanel = new JPanel();
	private final JPanel gamesPanel = new JPanel();

	private Listener gameChooserModelListener;

	private JComponent selectedCategoryComponent;
	private JComponent selectedGamesComponent;

	private final Map<CategoryId,JComponent> gamesComponentCache =
		new HashMap<CategoryId,JComponent>();

	private Graphics g;

	public GameChooserComponent(
			GameChooserModel gameChooserModel) {
		this.gameChooserModel = gameChooserModel;

		initGamesComponents();
		initLayout();
		initCategoryPanel();
		initGamesPanel();
		initGameChooserModelListener();
	}

	private void initGamesComponents() {
		for (Category category : gameChooserModel.getCategories()) {
			createGameComponent(category.getId());
		}
	}

	private GamesComponent createGameComponent(CategoryId category) {
		GamesModel gamesModel = getGamesModel(category);
		GamesComponent gamesComponent = new GamesComponent(gamesModel);
		JScrollPane scrollPane = new JScrollPane(gamesComponent);
		cache(category, scrollPane);
		return gamesComponent;
	}

	private void cache(CategoryId category, JComponent gamesComponent) {
		gamesComponentCache.put(category, gamesComponent);
	}

	private GamesModel getGamesModel(CategoryId category) {
		return gameChooserModel.getGamesModel(category);
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		setBorder(PADDING_BORDER);
	}

	private void initCategoryPanel() {
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		add(innerPanel, BorderLayout.WEST);

		categoryPanel.setLayout(
				new GridLayout(getCategoryCount(), 1));
		innerPanel.add(categoryPanel);
	}

	private int getCategoryCount() {
		Categories categories = gameChooserModel.getCategories();
		return categories.getCount();
	}

	private void initGamesPanel() {
		gamesPanel.setLayout(new BorderLayout());
		add(gamesPanel, BorderLayout.CENTER);
	}

	private void initGameChooserModelListener() {
		gameChooserModelListener = new Listener() {
			public void onChange() {
				layoutAll();
			}
		};
		gameChooserModel.addListener(gameChooserModelListener);
		layoutAll();
	}

	private void layoutAll() {
		layoutCategories();
		layoutGames();
	}

	private void layoutCategories() {
		categoryPanel.removeAll();

		for (Category category : gameChooserModel.getCategories()) {
			layoutCategory(category);
		}

		validate();
		repaint();
	}

	private void layoutCategory(Category category) {
		CategoryComponent categoryComponent = createCategoryComponent(category);
		if (isSelected(category)) {
			selectedCategoryComponent = categoryComponent;
		}
		categoryPanel.add(categoryComponent);
	}

	private boolean isSelected(Category category) {
		return getSelectedCategory().equals(category.getId());
	}

	private CategoryComponent createCategoryComponent(Category category) {
		CategoryModel categoryModel = createCategoryModel(category);
		return new CategoryComponent(categoryModel);
	}

	private CategoryModel createCategoryModel(Category category) {
		return new CategoryModel(category.getId(), gameChooserModel);
	}

	private void layoutGames() {
		gamesPanel.removeAll();

		selectedGamesComponent = getGamesComponentForSelectedCategory();
		gamesPanel.add(selectedGamesComponent);

		validate();
		repaint();
	}

	private JComponent getGamesComponentForSelectedCategory() {
		CategoryId selectedCategory = getSelectedCategory();
		return getGamesComponentFor(selectedCategory);
	}

	private CategoryId getSelectedCategory() {
		return gameChooserModel.getSelectedCategory();
	}

	private JComponent getGamesComponentFor(CategoryId category) {
		return gamesComponentCache.get(category);
	}

	public void paint(Graphics g) {
		super.paint(g);

		initGraphics(g);

		drawOutline();
	}

	private void initGraphics(Graphics g) {
		this.g = g;
	}

	private void drawOutline() {
		List<Point> points = createOutlinePoints();
		joinTheDots(points);
	}

	private List<Point> createOutlinePoints() {
		List<Point> points = new ArrayList<Point>();
		points.add(getCategoryTopLeft());
		if (!isStraightAlongTop()) {
			points.add(getCategoryTopRight());
			points.add(getGameTopLeft());
		}
		points.add(getGameTopRight());
		points.add(getGameBottomRight());
		points.add(getGameBottomLeft());
		points.add(getCategoryBottomRight());
		points.add(getCategoryBottomLeft());
		return Collections.unmodifiableList(points);
	}

	private boolean isStraightAlongTop() {
		return (getCategoryTopLeft().y==getGameTopLeft().y);
	}

	private void joinTheDots(List<Point> points) {
		Iterator<Point> iter = points.iterator();
		Point first = iter.next();
		Point a = first;
		while (iter.hasNext()) {
			Point b = iter.next();
			drawLine(a, b);
			a = b;
		}
		drawLine(a, first);
	}

	private void drawLine(
			Point from,
			Point to) {
		g.drawLine(from.x, from.y, to.x, to.y);
	}

	private Point getCategoryTopLeft() {
		return getTopLeft(getCategoryBounds());
	}

	private Point getCategoryTopRight() {
		Point gameTopLeft = getGameTopLeft();
		Point categoryTopLeft = getCategoryTopLeft();
		return new Point(
				gameTopLeft.x,
				categoryTopLeft.y);
	}

	private Point getCategoryBottomRight() {
		Point gameTopLeft = getGameTopLeft();
		Point categoryBottomLeft = getCategoryBottomLeft();
		return new Point(
				gameTopLeft.x,
				categoryBottomLeft.y);
	}

	private Point getCategoryBottomLeft() {
		return getBottomLeft(getCategoryBounds());
	}

	private Point getGameTopLeft() {
		return getTopLeft(getGameBounds());
	}

	private Point getGameTopRight() {
		return getTopRight(getGameBounds());
	}

	private Point getGameBottomLeft() {
		return getBottomLeft(getGameBounds());
	}

	private Point getGameBottomRight() {
		return getBottomRight(getGameBounds());
	}

	private Rectangle getGameBounds() {
		return getBounds(selectedGamesComponent);
	}

	private Rectangle getCategoryBounds() {
		return getBounds(selectedCategoryComponent);
	}

	private Rectangle getBounds(JComponent component) {
		return new Rectangle(
				getLocation(component),
				component.getSize());
	}

	private Point getLocation(JComponent component) {
		Point screenLocation =
			component.getLocationOnScreen();
		return convertPointFromScreen(screenLocation);
	}

	private Point convertPointFromScreen(Point point) {
		Point convertedPoint = new Point(point);
		SwingUtilities.convertPointFromScreen(convertedPoint, this);
		return convertedPoint;
	}

	private Point getTopLeft(Rectangle bounds) {
		return bounds.getLocation();
	}

	private Point getTopRight(Rectangle bounds) {
		return new Point(
				(int)bounds.getMaxX(),
				bounds.y);
	}

	private Point getBottomLeft(Rectangle bounds) {
		return new Point(
				bounds.x,
				(int)bounds.getMaxY());
	}

	private Point getBottomRight(Rectangle bounds) {
		return new Point(
				(int)bounds.getMaxX(),
				(int)bounds.getMaxY());
	}
}

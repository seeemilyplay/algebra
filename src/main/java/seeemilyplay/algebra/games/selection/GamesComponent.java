package seeemilyplay.algebra.games.selection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import seeemilyplay.algebra.games.core.Dependencies;
import seeemilyplay.algebra.games.core.Dependency;
import seeemilyplay.algebra.games.core.GameId;
import seeemilyplay.algebra.games.core.Games;
import seeemilyplay.algebra.games.core.GamesMap;
import seeemilyplay.algebra.lookandfeel.AlgebraTheme;

@SuppressWarnings("serial")
final class GamesComponent extends JComponent {

	private static final int H_GAP = 30;
	private static final int V_GAP = 50;

	private static final Border PADDING = BorderFactory.createEmptyBorder(
			20, 20, 20, 20);

	private final GamesModel gamesModel;

	private final java.util.Map<GameId,GameComponent> gameComponentCache =
		new HashMap<GameId,GameComponent>();
	private final List<Connector> connectors =
		new ArrayList<Connector>();

	public GamesComponent(GamesModel gamesModel) {
		this.gamesModel = gamesModel;

		initLayout();
		layoutNodes();
		createConnectors();
	}

	public void paint(Graphics g) {
		super.paint(g);

		paintConnectors(g);
	}

	private void paintConnectors(Graphics g) {
		for (Connector connector : connectors) {
			paintConnectorIfVisible(g, connector);
		}
	}

	private void paintConnectorIfVisible(Graphics g, Connector connector) {
		if (!connector.isVisible()) {
			return;
		}
		paintConnector(g, connector);
	}

	private void paintConnector(Graphics g, Connector connector) {
		Graphics2D g2 = (Graphics2D)g;
		Stroke originalStroke = g2.getStroke();
		Color originalColor = g2.getColor();
		updateStroke(g2);
		updateColor(g2, connector);
		drawLine(g2, connector.getFromLocation(), connector.getToLocation());
		g2.setStroke(originalStroke);
		g2.setColor(originalColor);
	}

	private void updateStroke(Graphics2D g2) {
		g2.setStroke(new BasicStroke(
				2.0f,
				BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
	}

	private void updateColor(Graphics2D g2, Connector connector) {
		AlgebraTheme theme = AlgebraTheme.getInstance();
		g2.setColor(connector.isEnabled() ?
			theme.getInk()
			: theme.getDisabledInk());
	}

	private void drawLine(Graphics g, Point from, Point to) {
		g.drawLine(from.x, from.y, to.x, to.y);
	}

	private void initLayout() {
		setLayout(new GridLayout(getLevelCount(), 1, H_GAP, V_GAP));
		setBorder(PADDING);
	}

	private void layoutNodes() {
		removeAll();

		for (int level=0; level<getLevelCount(); level++) {
			layoutLevel(level);
		}

		validate();
		repaint();
	}

	private void layoutLevel(int level) {

		JPanel levelPanel = new JPanel();
		levelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		for (int index=0; index<getGameCount(level); index++) {
			GameId game = getGame(level, index);
			GameComponent gameComponent = createGameComponent(game);
			levelPanel.add(gameComponent);
		}

		add(levelPanel);
	}

	private GameComponent createGameComponent(GameId game) {
		GameModel gameModel = createGameModel(game);
		GameComponent gameComponent = new GameComponent(gameModel);
		cache(gameModel, gameComponent);
		return gameComponent;
	}

	private void cache(GameModel gameModel, GameComponent gameComponent) {
		gameComponentCache.put(gameModel.getGameId(), gameComponent);
	}

	private GameModel createGameModel(GameId game) {
		return new GameModel(game, gamesModel);
	}

	private void createConnectors() {
		for (Dependency dependency : getDependencies()) {
			createArrow(dependency);
		}
	}

	private void createArrow(Dependency dependency) {
		GameComponent fromComponent = getGameComponent(dependency.getFrom());
		GameComponent toComponent = getGameComponent(dependency.getTo());
		Connector connector = new Connector(fromComponent, toComponent);
		connectors.add(connector);
	}

	private GameComponent getGameComponent(GameId game) {
		return gameComponentCache.get(game);
	}

	private int getLevelCount() {
		return getGames().getLevelCount();
	}

	private GameId getGame(int level, int index) {
		return getGames().getGame(level, index);
	}

	private int getGameCount(int level) {
		return getGames().getGameCount(level);
	}

	private Dependencies getDependencies() {
		return getGamesMap().getDependencies();
	}

	private Games getGames() {
		return getGamesMap().getGames();
	}

	private GamesMap getGamesMap() {
		return gamesModel.getGamesMap();
	}
}

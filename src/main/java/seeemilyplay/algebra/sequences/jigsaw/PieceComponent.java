package seeemilyplay.algebra.sequences.jigsaw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.JComponent;

import seeemilyplay.algebra.lookandfeel.AlgebraTheme;


@SuppressWarnings("serial")
class PieceComponent extends JComponent {

	private static final int Y_ICON_BORDER = 5;
	private static final int X_ICON_BORDER = 11;
	private static final int OUTTEY_WIDTH = 66;

	private final PieceModel pieceModel;

	private Rectangle centralBoundsForFitting;
	private Graphics g;

	public PieceComponent(
			PieceModel pieceModel) {
		this.pieceModel = pieceModel;

		initLayout();
	}
	
	private void initLayout() {
		setLayout(new BorderLayout());
		setFont(getTheme().getBoldTextFont());
		setPreferredSize(new Dimension(
				getIconWidth(), 
				getIconHeight()));
	}
	
	private AlgebraTheme getTheme() {
		return AlgebraTheme.getInstance();
	}
	
	private int getIconWidth() {
		return getIcon().getIconWidth();
	}
	
	private int getIconHeight() {
		return getIcon().getIconHeight();
	}
	
	private Icon getIcon() {
		return pieceModel.getIcon();
	}

	public boolean contains(int x, int y) {
		if (!isVisible()) {
			return false;
		}

		if (!super.contains(x, y)) {
			return false;
		}
		
		return isWithinIconEdge(x, y);
	}
	
	private boolean isWithinIconEdge(int x, int y) {
		return pieceModel.isWithinIconEdge(x, y);
	}

	public Rectangle getCentralBounds() {
		return new Rectangle(
				getCentralLocation(),
				getCentralDimension());
	}
	
	private Point getCentralLocation() {
		return new Point(
				getCentralX(), 
				getCentralY());
	}
	
	private int getCentralX() {
		return (getBounds().x 
				+ X_ICON_BORDER
				+ (isOutteyOnLeft() ? OUTTEY_WIDTH : 0));
	}
	
	private int getCentralY() {
		return (getBounds().y + Y_ICON_BORDER);
	}
	
	private Dimension getCentralDimension() {
		return new Dimension(
				getCentralWidth(),
				getCentralHeight());
	}
	
	private int getCentralWidth() {
		return (getPreferredSize().width 
				- (2 * X_ICON_BORDER)
				- (isOutteyOnLeft() ? OUTTEY_WIDTH : 0)
				- (isOutteyOnRight() ? OUTTEY_WIDTH : 0));
	}
	
	private int getCentralHeight() {
		return (getPreferredSize().height
					- (2 * Y_ICON_BORDER));
	}

	public Rectangle getFittingBounds(Rectangle centralBounds) {
		init(centralBounds);
		
		return new Rectangle(
				getFittingX(),
				getFittingY(),
				getFittingWidth(),
				getFittingHeight());
	}
	
	private void init(Rectangle centralBounds) {
		this.centralBoundsForFitting = centralBounds;
	}
	
	private int getFittingX() {
		return (centralBoundsForFitting.x
				- X_ICON_BORDER
				- (isOutteyOnLeft() ? OUTTEY_WIDTH : 0));
	}
	
	private int getFittingY() {
		return (centralBoundsForFitting.y 
				- Y_ICON_BORDER);
	}
	
	private int getFittingWidth() {
		return (centralBoundsForFitting.x
				+ (X_ICON_BORDER * 2)
				+ (isOutteyOnLeft() ? OUTTEY_WIDTH : 0));
	}
	
	private int getFittingHeight() {
		return (centralBoundsForFitting.y 
				+ (Y_ICON_BORDER * 2));
	}

	private boolean isOutteyOnLeft() {
		return pieceModel.isOutteyOnLeft();
	}

	private boolean isOutteyOnRight() {
		return pieceModel.isOutteyOnRight();
	}

	public void paint(Graphics g) {
		super.paint(g);

		init(g);
		paintIcon();
		paintText();
	}
	
	private void init(Graphics g) {
		this.g = g;
	}
	
	private void paintIcon() {
		getIcon().paintIcon(
				this, 
				g, 
				0, 
				0);
	}
	
	private void paintText() {
		g.drawString(
				getText(), 
				getTextX(), 
				getTextY());
	}
	
	private int getTextX() {
		return (int)(getCentralWidth() / 2.0 
				- getTextWidth() / 2.0);
	}
	
	private int getTextY() {
		return (int)(getCentralHeight() / 2.0
				- getTextHeight() / 2.0);
	}
	
	private int getTextWidth() {
		return getTextSize().width;
	}
	
	private int getTextHeight() {
		return getTextSize().height;
	}
	
	private Dimension getTextSize() {
		Font font = g.getFont();
		Rectangle2D textBounds = font.getStringBounds(
				getText(), 
				getFontRenderContext());
		return textBounds.getBounds().getSize();
	}
	
	private FontRenderContext getFontRenderContext() {
		return g.getFontMetrics().getFontRenderContext();
	}
	
	private String getText() {
		return Integer.toString(getValue());
	}
	
	private int getValue() {
		return pieceModel.getValue();
	}
}

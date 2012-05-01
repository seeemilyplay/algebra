package seeemilyplay.algebra.lookandfeel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.border.Border;

/**
 * Caches images used for drawing borders.
 * This comes in handy when repeatedly drawing borders
 * that would naturally be drawn in a way that
 * differs each time.
 */
@SuppressWarnings("serial")
final class CachedBorder implements Border {

	private static final int IMAGE_CACHE_SIZE_LIMIT = 10;

	private final Border delegate;

	private final Map<Rectangle, Image> imageCache =
		new LinkedHashMap<Rectangle, Image>() {
			protected boolean removeEldestEntry(Entry<Rectangle, Image> eldest) {
				return (imageCache.size()>IMAGE_CACHE_SIZE_LIMIT);
			}
	};

	private Component c;
	private Graphics g;
	private Rectangle bounds;

	public CachedBorder(Border delegate) {
		super();
		this.delegate = delegate;
	}

	public Insets getBorderInsets(Component c) {
		return delegate.getBorderInsets(c);
	}

	public boolean isBorderOpaque() {
		return delegate.isBorderOpaque();
	}

	public void paintBorder(
			Component c,
			Graphics g,
			int x,
			int y,
			int width,
			int height) {
		setComponent(c);
		setGraphics(g);
		setBounds(x, y, width, height);
		drawBorder();
	}

	private void setComponent(Component c) {
		this.c = c;
	}

	private void setGraphics(Graphics g) {
		this.g = g;
	}

	private void setBounds(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
	}

	private void drawBorder() {
		if (!isImageCached()) {
			createAndCacheImage();
		}
		drawCachedImage();
	}

	private boolean isImageCached() {
		return imageCache.containsKey(bounds);
	}

	private void createAndCacheImage() {
		Image image = createImage();
		cache(image);
	}

	private Image createImage() {
		BufferedImage bufferedImage = createBufferedImage();
		paintBorder(bufferedImage);
		return bufferedImage;
	}

	private BufferedImage createBufferedImage() {
		return new BufferedImage(
				bounds.width,
				bounds.height,
				BufferedImage.TYPE_INT_ARGB);
	}

	private void paintBorder(BufferedImage bufferedImage) {
		Graphics bufferedImageGraphics = getBufferedImageGraphics(bufferedImage);
		bufferedImageGraphics.setColor(g.getColor());
		delegate.paintBorder(
				c,
				bufferedImageGraphics,
				0,
				0,
				bounds.width,
				bounds.height);
	}

	private Graphics getBufferedImageGraphics(BufferedImage bufferedImage) {
		return bufferedImage.createGraphics();
	}

	private void cache(Image image) {
		imageCache.put(bounds, image);
	}

	private void drawCachedImage() {
		Image image = getCachedImage();
		draw(image);
	}

	private Image getCachedImage() {
		return imageCache.get(bounds);
	}

	private void draw(Image image) {
		g.drawImage(
				image,
				bounds.x,
				bounds.y,
				c);
	}
}

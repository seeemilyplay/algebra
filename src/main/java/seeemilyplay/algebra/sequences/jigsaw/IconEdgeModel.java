package seeemilyplay.algebra.sequences.jigsaw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import seeemilyplay.core.swing.BlockingTask;
import seeemilyplay.core.swing.BlockingTaskUtils;
import seeemilyplay.core.swing.MultiThreadedSwingModel;


final class IconEdgeModel extends MultiThreadedSwingModel {
	
	private final CountDownLatch calculateLatch = new CountDownLatch(1);
	private final Object lock = new Object();
	
	private final Icon icon;
	
	private boolean[][] isWithinEdgeValues;
	
	
	public IconEdgeModel(Icon icon) {
		this.icon = icon;
		
		scheduleEdgeCalculation();
	}
	
	private void scheduleEdgeCalculation() {
		executeOffSwingThread(new Runnable() {
			public void run() {
				calculateAndSetIsWithinEdgeValues();
			}
		});
	}
	
	private void calculateAndSetIsWithinEdgeValues() {
		boolean[][] isWithinEdgeValues = calculateIsWithinEdgeValues();
		
		synchronized (lock) {
			IconEdgeModel.this.isWithinEdgeValues =
				isWithinEdgeValues;
		}
		
		calculateLatch.countDown();
	}
	
	private void waitForCalculation() {
		BlockingTaskUtils.execute(new BlockingTask() {
			public void run() throws InterruptedException {
				calculateLatch.await(5, TimeUnit.SECONDS);
			}
		});
	}
	
	public boolean isWithinEdge(int x, int y) {
		waitForCalculation();
		return getIsWithinEdgeValue(x, y);
	}
	
	private boolean getIsWithinEdgeValue(int x, int y) {
		synchronized (lock) {
			return isWithinEdgeValues[x][y];
		}
	}
	
	private boolean[][] calculateIsWithinEdgeValues() {

		BufferedImage image = createIconImage();
		
		boolean[][] horizontalValues = createHorizontalValues(image);
		boolean[][] verticalValues = createVerticalValues(image);
		
		return combine(
				horizontalValues,
				verticalValues);
	}
	
	private BufferedImage createIconImage() {
		BufferedImage image = createWhiteImage();
		icon.paintIcon(
				createDummyComponent(), 
				image.createGraphics(), 
				0, 
				0);
		return image;
	}
	
	private JComponent createDummyComponent() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(
				new Dimension(
						getIconWidth(), 
						getIconHeight()));
		return panel;
	}
	
	private BufferedImage createWhiteImage() {
		BufferedImage image = createEmptyImage();
		for (int x=0; x<image.getWidth(); x++) {
			for (int y=0; y<image.getHeight(); y++) {
				image.setRGB(x, y, Color.white.getRGB());
			}
		}
		return image;
	}
	
	private BufferedImage createEmptyImage() {
		return new BufferedImage(
				getIconWidth(), 
				getIconHeight(), 
				BufferedImage.TYPE_INT_RGB);
	}
	
	private boolean[][] createHorizontalValues(BufferedImage image) {
		
		boolean[][] values = createEmptyValues();
		
		for (int y=0; y<getIconHeight(); y++) {
			boolean previousIsInk = false;
			int lineCount = 0;
			for (int x=0; x<getIconWidth(); x++) {
				boolean isInk = isInk(image, x, y);
				
				if (!isInk && previousIsInk) {
					lineCount++;
				}
				
				previousIsInk = isInk;
				
				boolean isPixelWithinEdge = (isInk || ((lineCount%2) == 1));
				
				values[x][y] = isPixelWithinEdge;
			}
		}
		
		return values;
	}
	
	private boolean[][] createVerticalValues(BufferedImage image) {
		
		boolean[][] values = createEmptyValues();
		
		for (int x=0; x<getIconWidth(); x++) {
			boolean previousIsInk = false;
			int lineCount = 0;
			for (int y=0; y<getIconHeight(); y++) {
				boolean isInk = isInk(image, x, y);
				
				if (!isInk && previousIsInk) {
					lineCount++;
				}
				
				previousIsInk = isInk;
				
				boolean isPixelWithinEdge = (isInk || ((lineCount%2) == 1));
				
				values[x][y] = isPixelWithinEdge;
			}
		}
		
		return values;
	}
	
	private boolean isInk(BufferedImage image, int x, int y) {
		return (Color.white.getRGB()!=image.getRGB(x, y));
	}
	
	private boolean[][] combine(
			boolean[][] a, 
			boolean[][] b) {
		
		boolean[][] values = createEmptyValues();
		
		for (int y=0; y<getIconHeight(); y+=1) {
			for (int x=0; x<getIconWidth(); x+=1) {
				values[x][y] = 
					(a[x][y] 
					 && b[x][y]);
			}
		}
		
		return values;
	}
	
	private boolean[][] createEmptyValues() {
		return new boolean[getIconWidth()][getIconHeight()];
	}
	
	private int getIconWidth() {
		return icon.getIconWidth();
	}
	
	private int getIconHeight() {
		return icon.getIconHeight();
	}
}

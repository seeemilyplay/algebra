package seeemilyplay.algebra.lookandfeel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import net.sourceforge.napkinlaf.NapkinTheme;
import net.sourceforge.napkinlaf.sketch.SketchedIcon;
import net.sourceforge.napkinlaf.sketch.Template;
import net.sourceforge.napkinlaf.sketch.TemplateReadException;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;

final class TemplateIconCache {

	private final Map<URI, Icon> cache = new HashMap<URI, Icon>();

	private final NapkinTheme defaultTheme;

	public TemplateIconCache(NapkinTheme defaultTheme) {
		super();
		this.defaultTheme = defaultTheme;
	}

	public Icon getIcon(URL url) {
		IconTask iconTask = new IconTask(url);
		ErrorHandler.getInstance().run(iconTask);
		return iconTask.getIcon();
	}

	private class IconTask implements ExceptionThrowingTask {

		private final URL url;

		private URI uri;
		private Icon icon;

		public IconTask(URL url) {
			this.url = url;
		}

		public void run() throws URISyntaxException, IOException, TemplateReadException {
			icon = getIconIgnoringExceptions();
		}

		private Icon getIconIgnoringExceptions() throws URISyntaxException, IOException, TemplateReadException {
			initURI();
			createAndCacheIconIfRequired();
			return getCachedIcon();
		}

		private void initURI() throws URISyntaxException {
			uri = url.toURI();
		}

		private boolean isIconCached() {
			return cache.containsKey(uri);
		}

		private void createAndCacheIconIfRequired() throws IOException, TemplateReadException {
			if (!isIconCached()) {
				createAndCacheIcon();
			}
		}

		private void createAndCacheIcon() throws IOException, TemplateReadException {
			createIcon();
			cacheIcon();
		}

		private void createIcon() throws IOException, TemplateReadException {
			InputStream in = url.openStream();
			try {
				createIcon(in);
			} finally {
				in.close();
			}
		}

		private void createIcon(InputStream in) throws TemplateReadException {
			icon = new SketchedIcon(
					Template.createFromXML(in),
					defaultTheme.getSketcher());
		}

		private void cacheIcon() {
			cache.put(uri, icon);
		}

		private Icon getCachedIcon() {
			return cache.get(uri);
		}

		public Icon getIcon() {
			return icon;
		}
	}
}

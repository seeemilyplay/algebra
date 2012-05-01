package seeemilyplay.core.components;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * A <code>DocumentFilter</code> that only accepts
 * up to a certain number of characters.
 */
public class LimitedSizeDocumentFilter extends DocumentFilter {

	private final int sizeLimit;
	private Document document;

	public LimitedSizeDocumentFilter(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public static void install(Document document, int sizeLimit) {
		AbstractDocument abstractDocument = (AbstractDocument)document;
		DocumentFilter filter = createDocumentFilter(sizeLimit);
		abstractDocument.setDocumentFilter(filter);
	}

	private static DocumentFilter createDocumentFilter(int sizeLimit) {
		return new LimitedSizeDocumentFilter(sizeLimit);
	}

	public void insertString(
			FilterBypass fb,
			int offset,
			String string,
			AttributeSet attr) throws BadLocationException {

		setup(fb);

		String cutString = cutToFreeSpace(string);

		super.insertString(fb, offset, cutString, attr);

	}

	public void replace(
			FilterBypass fb,
			int offset,
			int length,
			String text,
			AttributeSet attrs) throws BadLocationException {

		setup(fb);

		String cutText = cutToFreeSpaceWhenReplacing(length, text);

		super.replace(fb, offset, length, cutText, attrs);
	}

	private void setup(FilterBypass fb) {
		setupDocument(fb.getDocument());
	}

	private void setupDocument(Document document) {
		this.document = document;
	}

	private String cutToFreeSpace(String string) {
		int freeSpace = getFreeSpace();
		return cutToSize(freeSpace, string);
	}

	private int getFreeSpace() {
		int currentLength = getCurrentLength();
		int freeSpace = (sizeLimit - currentLength);
		return freeSpace;
	}

	private int getCurrentLength() {
		return document.getLength();
	}

	private String cutToSize(int size, String string) {
		if (fitsToSize(size, string)) {
			return string;
		}
		return string.substring(0, size);
	}

	private boolean fitsToSize(int size, String string) {
		return string.length()<=size;
	}

	private String cutToFreeSpaceWhenReplacing(int replacementLength, String string) {
		int freeSpace = getFreeSpaceWhenReplacing(replacementLength);
		return cutToSize(freeSpace, string);
	}

	private int getFreeSpaceWhenReplacing(int replacementLength) {
		return replacementLength + getFreeSpace();
	}
}

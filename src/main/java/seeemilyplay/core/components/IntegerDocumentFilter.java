package seeemilyplay.core.components;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * A <code>DocumentFilter</code> that only accepts integers as inputs.
 */
public final class IntegerDocumentFilter extends LimitedSizeDocumentFilter {

	private static final char MINUS = '-';

	private Document document;
	private int offset;


	public IntegerDocumentFilter(int sizeLimit) {
		super(sizeLimit);
	}

	public static void install(Document document, int sizeLimit) {
		AbstractDocument abstractDocument = (AbstractDocument)document;
		DocumentFilter filter = createDocumentFilter(sizeLimit);
		abstractDocument.setDocumentFilter(filter);
	}

	private static DocumentFilter createDocumentFilter(int sizeLimit) {
		return new IntegerDocumentFilter(sizeLimit);
	}

	public void insertString(
			FilterBypass fb,
			int offset,
			String string,
			AttributeSet attr) throws BadLocationException {

		setup(fb, offset);

		String filteredString = filter(string);

		super.insertString(fb, offset, filteredString, attr);
	}

	public void replace(
			FilterBypass fb,
			int offset,
			int length,
			String text,
			AttributeSet attrs) throws BadLocationException {

		setup(fb, offset);

		String filteredText = filter(text);

		super.replace(fb, offset, length, filteredText, attrs);
	}

	private void setup(FilterBypass fb, int offset) {
		setupDocument(fb.getDocument());
		setupOffset(offset);
	}

	private void setupDocument(Document document) {
		this.document = document;
	}

	private void setupOffset(int offset) {
		this.offset = offset;
	}

	private String filter(String string) throws BadLocationException {

		CharCollector charCollector = new CharCollector();

		for (char c : string.toCharArray()) {
			if (allow(c)) {
				charCollector.add(c);
			}
		}

		return charCollector.toString();
	}

	private boolean allow(char c) throws BadLocationException {
		boolean allow = (allowBecauseMinus(c) || allowBecauseDigit(c));
		if (allow) {
			offset++;
		}
		return allow;
	}

	private boolean allowBecauseDigit(char c) {
		return Character.isDigit(c);
	}

	private boolean allowBecauseMinus(char c) throws BadLocationException {
		return (isMinusPossible() && isMinus(c));
	}

	private boolean isMinus(char c) {
		return MINUS==c;
	}

	private boolean isMinusPossible() throws BadLocationException {
		return (isInsertingAtStart() &&
				(isEmpty() || isNotStartedByMinus()));
	}

	private boolean isEmpty() {
		return (getCurrentLength()==0);
	}

	private int getCurrentLength() {
		return document.getLength();
	}

	private boolean isInsertingAtStart() {
		return (offset==0);
	}

	private boolean isNotStartedByMinus() throws BadLocationException {
		return getFirstChar()!=MINUS;
	}

	private char getFirstChar() throws BadLocationException {
		return document.getText(0, 1).charAt(0);
	}

	private static class CharCollector {

		private char[] chars = new char[0];

		public CharCollector() {
			super();
		}

		public void add(char c) {
			char[] orig = chars;
			chars = new char[orig.length+1];
			System.arraycopy(orig, 0, chars, 0, orig.length);
			chars[orig.length] = c;
		}

		public String toString() {
			return new String(chars);
		}
	}
}

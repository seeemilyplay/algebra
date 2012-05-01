package seeemilyplay.core.components;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import junit.framework.TestCase;

/**
 * A unit test for the <code>LimitedSizeDocument</code>.
 *
 * @see LimitedSizeDocument
 */
public class LimitedSizeDocumentTest extends TestCase {

	private static final int MAX_CHARS = 5;

	private PlainDocument document = new PlainDocument();
	private LimitedSizeDocumentFilter filter = new LimitedSizeDocumentFilter(5);


	protected void setUp() throws Exception {
		setUpDocument();
	}

	private void setUpDocument() {
		document = new PlainDocument();
		LimitedSizeDocumentFilter.install(document, MAX_CHARS);

		document.setDocumentFilter(filter);
	}

	public void testInsertA() {

		givenEmptyDocument();

		whenInsert(0, "123");

		thenTextIs("123");
	}

	public void testInsertB() {

		givenEmptyDocument();

		whenInsert(0, "12345");

		thenTextIs("12345");
	}

	public void testInsertC() {

		givenEmptyDocument();

		whenInsert(0, "123456");

		thenTextIs("12345");
	}

	public void testInsertD() {

		givenDocumentWith("123");

		whenInsert(3, "4");

		thenTextIs("1234");
	}

	public void testInsertE() {

		givenDocumentWith("123");

		whenInsert(3, "45");

		thenTextIs("12345");
	}

	public void testInsertF() {

		givenDocumentWith("123");

		whenInsert(3, "456");

		thenTextIs("12345");
	}

	public void testReplaceA() {

		givenDocumentWith("12345");

		whenReplace(0, 5, "1");

		thenTextIs("1");
	}

	public void testReplaceB() {

		givenDocumentWith("12345");

		whenReplace(0, 1, "2");

		thenTextIs("22345");
	}

	public void testReplaceC() {

		givenDocumentWith("12345");

		whenReplace(0, 1, "23");

		thenTextIs("22345");
	}

	private void givenEmptyDocument() {
		return;
	}

	private void givenDocumentWith(String text) {
		try {
			document.insertString(0, text, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	private void whenInsert(int offs, String str) {
		try {
			document.insertString(offs, str, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	private void whenReplace(int offset, int length, String text) {
		try {
			document.replace(offset, length, text, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	private void thenTextIs(String text) {
		assertEquals(text, getText());
	}

	private String getText() {
		try {
			return document.getText(0, document.getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}
}

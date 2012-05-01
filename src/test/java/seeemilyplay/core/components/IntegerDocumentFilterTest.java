package seeemilyplay.core.components;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import junit.framework.TestCase;

public class IntegerDocumentFilterTest extends TestCase {

	private static final int MAX_CHARS = 5;

	private PlainDocument document = new PlainDocument();
	private IntegerDocumentFilter filter = new IntegerDocumentFilter(5);


	protected void setUp() throws Exception {
		setUpDocument();
	}

	private void setUpDocument() {
		document = new PlainDocument();
		IntegerDocumentFilter.install(document, MAX_CHARS);

		document.setDocumentFilter(filter);
	}

	public void testInsertA() {

		givenEmptyDocument();

		whenInsert(0, "123");

		thenTextIs("123");
	}

	public void testInsertB() {

		givenEmptyDocument();

		whenInsert(0, "-123");

		thenTextIs("-123");
	}

	public void testInsertC() {

		givenEmptyDocument();

		whenInsert(0, "--123");

		thenTextIs("-123");
	}

	public void testInsertD() {

		givenEmptyDocument();

		whenInsert(0, "123-");

		thenTextIs("123");
	}

	public void testInsertE() {

		givenEmptyDocument();

		whenInsert(0, "ab123");

		thenTextIs("123");
	}

	public void testInsertF() {

		givenEmptyDocument();

		whenInsert(0, "a-b-1-d-2-3");

		thenTextIs("-123");
	}

	public void testInsertG() {

		givenDocumentWith("123");

		whenInsert(0, "-");

		thenTextIs("-123");
	}

	public void testInsertH() {

		givenDocumentWith("123");

		whenInsert(0, "a-4");

		thenTextIs("-4123");
	}

	public void testInsertI() {

		givenDocumentWith("-123");

		whenInsert(0, "-");

		thenTextIs("-123");
	}

	public void testInsertJ() {

		givenDocumentWith("-123");

		whenInsert(4, "a-b-c-d-e-4-f-g");

		thenTextIs("-1234");
	}

	public void testInsertK() {

		givenEmptyDocument();

		whenInsert(0, "a-b1c2d-e3d");

		thenTextIs("-123");
	}

	public void testReplaceA() {

		givenDocumentWith("1");

		whenReplace(0, 1, "2");

		thenTextIs("2");
	}

	public void testReplaceB() {

		givenDocumentWith("1");

		whenReplace(0, 1, "a-b2");

		thenTextIs("-2");
	}

	public void testReplaceC() {

		givenDocumentWith("123");

		whenReplace(1, 2, "a4");

		thenTextIs("14");
	}

	public void testReplaceD() {

		givenDocumentWith("12345");

		whenReplace(2, 3, "a6b7");

		thenTextIs("1267");
	}

	public void testReplaceE() {

		givenDocumentWith("12345");

		whenReplace(4, 1, "-");

		thenTextIs("1234");
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
